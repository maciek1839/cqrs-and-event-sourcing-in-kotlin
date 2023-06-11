package com.showmeyourcode.cqrs.cqs.demo.query.getproductavailability

import com.showmeyourcode.cqrs.cqs.demo.infra.Query
import java.util.*

class GetProductAvailabilityQuery(val productId: UUID) : Query<GetProductAvailabilityQueryResult>

class GetProductAvailabilityQueryResult(val availability: Int)
