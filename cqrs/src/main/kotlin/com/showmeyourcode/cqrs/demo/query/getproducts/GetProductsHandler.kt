package com.showmeyourcode.cqrs.demo.query.getproducts

import com.showmeyourcode.cqrs.demo.infra.QueryHandler
import com.showmeyourcode.cqrs.demo.repository.query.ProductQueryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductsHandler(private val repository: ProductQueryRepository) : QueryHandler<GetProductsQueryResult, GetProductsQuery> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductsQuery?): GetProductsQueryResult {
        log.info("Handling 'GetProductsQuery' command...")
        return GetProductsQueryResult(repository.findAll())
    }
}
