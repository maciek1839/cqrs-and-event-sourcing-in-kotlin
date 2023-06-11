package com.showmeyourcode.cqrseventsourcing.demo.query.getproducts

import com.showmeyourcode.cqrseventsourcing.demo.domain.QueryHandler
import com.showmeyourcode.cqrseventsourcing.demo.repository.query.ProductQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductsHandler(private val repository: ProductQueryRepository) :
    QueryHandler<GetProductsQueryResult, GetProductsQuery> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductsQuery?): GetProductsQueryResult {
        log.info("Handling 'GetProductsQuery' command...")
        return GetProductsQueryResult(repository.findAll())
    }
}
