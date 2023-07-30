# CONTRIBUTING

- Follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).
    - References:
      - https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716
      - https://ec.europa.eu/component-library/v1.15.0/eu/docs/conventions/git/

## Coding conventions

Ref: https://kotlinlang.org/docs/reference/coding-conventions.html#naming-rules

### Naming rules - packages

Names of packages are always lower case and do not use underscores (org.example.myproject). Using multi-word names is generally discouraged, but if you do need to use multiple words, you can either simply concatenate them together or use camel humps (org.example.myProject). Ref: https://kotlinlang.org/docs/coding-conventions.html#naming-rules

## Postman

Import existing collection from `CQRS demo.postman_collection.json`.

## Maven commands

- mvn clean install -DskipTests

### Format files using ktlint

Use a command: ``mvn ktlint:format``

*Add a JVM option `--add-opens=java.base/java.lang=ALL-UNNAMED` if you encounter any errors running the plain command.*

Ref: https://gantsign.com/ktlint-maven-plugin/usage.html

### Release a new version

Run a Maven command using Maven Release plugin:
```text
mvn release:prepare
```

If you want to only update versions (not recommended), use below command:
```text
mvn release:update-versions -DautoVersionSubmodules=true
```
