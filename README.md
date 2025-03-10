# CQRS and Event Sourcing

| Branch |                                                                                                         Pipeline                                                                                                         |                                                                                                      Code coverage                                                                                                       |                                               Test report                                                |                                               Dependency report                                                |                                 SonarCloud                                 |
|:------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|
| master | [![pipeline status](https://gitlab.com/ShowMeYourCodeYouTube/cqrs-and-event-sourcing-in-kotlin/badges/master/pipeline.svg)](https://gitlab.com/ShowMeYourCodeYouTube/cqrs-and-event-sourcing-in-kotlin/-/commits/master) | [![coverage report](https://gitlab.com/ShowMeYourCodeYouTube/cqrs-and-event-sourcing-in-kotlin/badges/master/coverage.svg)](https://gitlab.com/ShowMeYourCodeYouTube/cqrs-and-event-sourcing-in-kotlin/-/commits/master) | [link](https://showmeyourcodeyoutube.gitlab.io/cqrs-and-event-sourcing-in-kotlin/test-report/index.html) | [link](https://showmeyourcodeyoutube.gitlab.io/cqrs-and-event-sourcing-in-kotlin/dependency-check-report.html) | [link](https://sonarcloud.io/organizations/showmeyourcodeyoutube/projects) |

---

The example is based on stores and products.
- A product can be added to a store.
- Number of available products can be changed.
- A list of available products can be retrieved.

## Technology

- Kotlin
- Spring Boot Reactive stack
- Kotest

## How to start?

1. Run one of the Maven modules, for example: `cqrs`
2. Run Spring Boot app.
3. Check H2 console - `localhost:8082`
4. Set up a connection to MongoDB  
  ![img](./docs/setup-db/mongo-gui.png)
5. Load the Postman collection to send requests.

## CQS & CQRS & Event sourcing introduction

### Standard architecture (layered)

![No CQRS](./docs/altkom/1_no_cqrs.png)

### CQS (Separate commands and queries)

Command-Query Separation (CQS) is a principle or guideline, used in a software architecture, that states that every method should either be a command that performs an action, or a query that returns data to the caller, but not both. Methods should return a value only if they create no side effects.

**Command - how about returning the ID of a created entity?**     
Put simply, if a CQRS command is capable of returning a success/failure indicating completion status, then a return value makes sense. This includes returning a new DB row identity, or any result that does not read or return domain model (business) content.  
Ref: https://stackoverflow.com/questions/43433318/cqrs-command-return-values

CQS is about Command and Queries. It doesn't care about the model. You have somehow separated services for reading data, and others for writing data.

CQS architecture reads and writes from the same data store/tables.  
![CQS](./docs/altkom/2_separe_commands_queries.png)

![CQS](./docs/thecodereaper/theCodeReaper_cqs.png)

**Commands**  
A command is created when data is to be mutated. The following classes are needed for a command:
- A command class containing the properties required to mutate data, for example properties to create a new object.
- A command handler class to handle the business logic for the command and to handle mutation of the data store, for example CreateObjectCommandHandler is a class that handles the creation of a new object.
- A command response class to return to the caller. Note that a command response class is optional since not all commands will return a response. For example, CreateObjectCommandResponse is a class that contains the details of the newly created object. 
- A command validator class to handle validation for a command. The validator is to be called before the command handler is executed since the command handler always assumes that the command is valid.

**Queries**  
A query is only created when data is to be retrieved from the data store. Similar to commands, the following classes are needed for a query:
- A query class containing the properties needed to retrieve data from a data store. For example, GetObjectByIdQuery is a query class that contains the Id property of the object to get from the data store. Note that not all queries will have properties.
- A query handler class to retrieve data from the data store. For example, GetObjectByIdQueryHandler is a class that handles the logic for getting an object by Id from the data store.
- A query response class to return a response to the caller. For example, GetObjectByIdQueryResponse is a class that contains the details of the object.
- A query validator class to handle validation for a query. The validator is to be called before the query handler is executed since the query handler always assumes that the query is valid. GetObjectByIdQueryValidator is an example of a validator for a GetObjectByIdQuery. Note that the query validator class is optional since not all queries will contain properties.

#### When does validation happen?

You could read about one approach mentioned above where a validator is called before the handler. It might be useful if you use REST API and you can call OpenAPI validator which will check you query/command object accordingly to OpenAPI schema.

**Validate format and required fields in controllers before passing data to a handler/service. Enforce business logic and domain invariants in the service when constructing objects and executing business operations.**

---

> If I have a Web API request to withdraw some money from a bank account, I might perform the following validation:
> Does the bank account exist?
> Does the bank account have enough funds to withdraw?
> 
> When the request comes in, do I save the event before performing the above validation (and risk storing invalid events) or after the validation (and risk something going wrong part-way through the process, like the service going down, and not storing the event at all)? In the case of CQRS, is the event stored before the Command is executed or as part of the Command (in the Command handler)?

Shortly speaking, the Command handles the validation, loading the aggregate, etc ... and if all that is successful, only then the event is recorded in the Event Store. Otherwise, the command fails (e.g. account doesn't exist) and returns the response to the caller (edge).


In general, the command handling flow can be described like this:
- The edge receives a command via its API (HTTP, gRPC, SignalR, messaging, etc).
- It passes the command over to the application service. As the edge is responsible for authentication and some authorisation, it can enrich commands with user credentials.
- The command service, which is agnostic to the API itself, handles the command and gives a response to the edge (positive or negative).
- The API layer then returns the response to the calling party.

The command service itself performs the following operations when handling one command:
- Extract the aggregate id from the command, if necessary.
- Instantiate all the necessary value objects. This could effectively reject the command if value objects cannot be constructed. The command service could also load some other aggregates, or any other information, which is needed to execute the command but won’t change state.
- If the command expects to operate on an existing aggregate instance, this instance gets loaded from the Aggregate Store.
- Execute an operation on the loaded (or new) aggregate, using values from the command, and the constructed value objects.
- The aggregate either performs the operation and changes its state by producing new events, or rejects the operation.
- If the operation was successful, the service persists new events to the store. Otherwise, it returns a failure to the edge.

I would also separate the domain model invariants and validations. I use the word "validation" when I check if a provided string value is indeed a bank account number, a valid phone number, mandatory fields, etc. Domain invariants, including aggregate invariants (can the withdrawal be processed) and cross-aggregate invariants (one customer cannot have more than ten bank accounts, can't imagine anything else really).

[Reference](https://stackoverflow.com/questions/67292439/event-sourcing-and-when-to-perform-validation)

### CQRS

Command Query Responsibility Segregation (CQRS) is a principle/popular architecture pattern that applies CQS however it is a much more complex principle since CQRS allows for separate data stores and separate models for commands and queries. The data between data stores is usually synchronized using a service bus. With CQS, the same data store is used for both commands and queries and models may be shared between commands and queries.

In a nutshell it says that your write model is not the same as your read model because of different representation logic behind it: for your web pages you could have views specifically adapted for representation logic of the UI elements on it. And write model contains all data in format which best fits type of the data. If you familiar with SQL, read model is something similar to SQL Views (which is just projection of data in convenient form). This gives you not only flexibility in separation of representational logic, but also in choosing underlying storage technologies. Write model storage could be in good old SQL and read model could be in MongoDB or any other NoSQL DBMS. In essence it is SRP (single responsibility principle) at application level.

*However, using the same database for your read/write models, you can choose a materialized view to present you read model, direction synchronization by updating the view table in the same transaction after updating your write model, or do it asynchronously via messaging.*

**The same store**  
![single storage](./docs/altkom/3_separate_models_commands_queries.png)

**Different stores**  
![separate storages](./docs/altkom/4_separate_storage_engines.png)

### Event sourcing

In order to adjust CQRS the command side should use Event Sourcing. 
Architecture of this version is very similar to above (when we use separate storage engines).

The key difference is the  command model. Instead of RDBMS and ORM, we use Event Store as a persistent storage. Instead of saving only actual state of object, we save stream of events. This pattern for managing state is named Event Sourcing.

Instead of keeping only current state of the system by altering previous state, we append events (changes) to sequential list of past events (changes). Thanks to this we not only know the current state of the system, but we can easily track how did we reach this state.

> Event Sourcing is a data storage pattern where all changes to an application's state are stored as a sequence of immutable events rather than just storing the current state. Instead of updating a database record, each change is captured as an event in an append-only log.

![event sourcing](./docs/altkom/5_event_sourcing.png)

![event_sourcing](./docs/kotlin-event-sourcing-example/diagram.png)  


References:
- https://www.baeldung.com/cqrs-event-sourcing-java
- https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs
- https://github.com/bringmeister/event-sourcing-with-kotlin (implementation based on DDD)
- https://altkomsoftware.pl/en/blog/cqrs-event-sourcing/
  - https://github.com/asc-lab/java-cqrs-intro
- https://thecodereaper.com/2020/05/23/command-query-separation-cqs
- https://stackoverflow.com/questions/34255490/difference-between-cqrs-and-cqs/55719178#55719178

## Reactive stack

![Reactive stack](./docs/reactive/reactive-stack2.png)

Spring WebFlux is supported on Tomcat, Jetty, Servlet 3.1+ containers, as well as on non-Servlet runtimes such as Netty and Undertow.

**Servlet stack**  
![Servlet stack](./docs/reactive/Thread-per-Request-Model.png)

This model of concurrency is known as the thread-per-request model:

**Reactive stack**  
![Reactive stack](./docs/reactive/Reactive-Model.png)

Is reactive programming a complete departure from thread-based concurrency? Reactive programming certainly has a very different approach to the usage of threads to achieve concurrency. The program flow transforms from a sequence of synchronous operations, into an asynchronous stream of events.

For instance, under the reactive model, a read call to the database does not block the calling thread while data is fetched. The call immediately returns a publisher that others can subscribe to. The subscriber can process the event after it occurs and may even further generate events itself:

### Event loop

There are several programming models that describe a reactive approach to concurrency. One of such reactive asynchronous programming model for servers is the event loop model.

![Event loop](./docs/reactive/Event-Loop.jpg)

Above, is an abstract design of an event loop that presents the ideas of reactive asynchronous programming:
- The event loop runs continuously in a single thread, although we can have as many event loops as the number of available cores
- The event loop process the events from an event queue sequentially and returns immediately after registering the callback with the platform
- The platform can trigger the completion of an operation like a database call or an external service invocation
- The event loop can trigger the callback on the operation completion notification and send back the result to the original caller

References:
- https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html
- https://www.infoq.com/news/2017/12/servlet-reactive-stack/
- https://www.baeldung.com/spring-webflux-concurrency

## Example implementations

- https://github.com/andreschaffer/event-sourcing-cqrs-examples
- https://github.com/asc-lab/java-cqrs-intro
- https://github.com/nicusX/kotlin-event-sourcing-example

### Alternatives

- Axon Framework
  - https://axoniq.io/
