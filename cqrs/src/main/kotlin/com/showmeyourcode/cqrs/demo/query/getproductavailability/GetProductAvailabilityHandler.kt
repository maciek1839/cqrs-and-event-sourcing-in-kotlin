package com.showmeyourcode.cqrs.demo.query.getproductavailability

import com.showmeyourcode.cqrs.demo.infra.QueryHandler
import com.showmeyourcode.cqrs.demo.repository.query.ProductQueryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GetProductAvailabilityHandler(private val repository: ProductQueryRepository) :
    QueryHandler<GetProductAvailabilityQueryResult, GetProductAvailabilityQuery> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun handle(query: GetProductAvailabilityQuery): GetProductAvailabilityQueryResult {
        log.info("Handling 'GetProductAvailability' command...")
        val p = repository.findById(query.productId)
        return GetProductAvailabilityQueryResult(if (p.isPresent) p.get().availability else 0)
    }
}
