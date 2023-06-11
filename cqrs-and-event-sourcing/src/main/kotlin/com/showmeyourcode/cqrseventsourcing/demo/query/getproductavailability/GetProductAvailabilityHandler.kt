package com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability

import com.showmeyourcode.cqrseventsourcing.demo.domain.QueryHandler
import com.showmeyourcode.cqrseventsourcing.demo.repository.query.ProductQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductAvailabilityHandler(private val repository: ProductQueryRepository) :
    QueryHandler<GetProductAvailabilityQueryResult, GetProductAvailabilityQuery> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductAvailabilityQuery): GetProductAvailabilityQueryResult {
        log.info("Handling 'GetProductAvailability' command...")
        val product = repository.findById(query.productId)
        if (product.isEmpty) {
            throw Exception("The product ${query.productId} was not found!")
        }
        return GetProductAvailabilityQueryResult(product.get().availability)
    }
}
