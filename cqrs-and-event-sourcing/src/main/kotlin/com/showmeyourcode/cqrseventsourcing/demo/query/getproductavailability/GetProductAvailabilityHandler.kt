package com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability

import com.showmeyourcode.cqrseventsourcing.demo.domain.QueryHandler
import com.showmeyourcode.cqrseventsourcing.demo.repository.query.ProductQueryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductAvailabilityHandler(private val repository: ProductQueryRepository) :
    QueryHandler<GetProductAvailabilityQueryResult, GetProductAvailabilityQuery> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductAvailabilityQuery): GetProductAvailabilityQueryResult {
        log.info("Handling 'GetProductAvailability' command...")
        val product = repository.findById(query.productId)
        val availability = if (product.isEmpty) 0 else product.get().availability
        return GetProductAvailabilityQueryResult(availability)
    }
}
