package com.showmeyourcode.cqrseventsourcing.demo.domain

import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityHandler
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability.GetProductAvailabilityQueryResult
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsHandler
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQuery
import com.showmeyourcode.cqrseventsourcing.demo.query.getproducts.GetProductsQueryResult
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class QueryHandlerProvider constructor(private val applicationContext: ApplicationContext) {

    fun getProductAvailability(query: GetProductAvailabilityQuery): GetProductAvailabilityQueryResult {
        return applicationContext.getBean(GetProductAvailabilityHandler::class.java).handle(query)
    }

    fun getProducts(query: GetProductsQuery): GetProductsQueryResult {
        return applicationContext.getBean(GetProductsHandler::class.java).handle(query)
    }
}
