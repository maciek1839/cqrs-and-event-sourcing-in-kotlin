package com.showmeyourcode.cqrs.cqs.demo.query.getproducts

import com.showmeyourcode.cqrs.cqs.demo.infra.QueryHandler
import com.showmeyourcode.cqrs.cqs.demo.repository.InMemoryProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductsHandler(
    private val repository: InMemoryProductRepository,
) : QueryHandler<GetProductsQueryResult, GetProductsQuery> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductsQuery?): GetProductsQueryResult {
        log.info("Handling 'GetProductsQuery' command...")
        return GetProductsQueryResult(repository.getAll())
    }
}
