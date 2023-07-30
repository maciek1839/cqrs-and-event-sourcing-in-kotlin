package com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability

import com.showmeyourcode.cqrs.cqs.demo.infra.QueryHandler
import com.showmeyourcode.cqrs.cqs.demo.repository.InMemoryProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductAvailabilityHandler(
    private val repository: InMemoryProductRepository,
) : QueryHandler<GetProductAvailabilityQueryResult, GetProductAvailabilityQuery> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductAvailabilityQuery): GetProductAvailabilityQueryResult {
        log.info("Handling 'GetProductAvailability' command...")
        val p = repository.getProduct(query.productId)
        return GetProductAvailabilityQueryResult(p?.availability ?: 0)
    }
}
