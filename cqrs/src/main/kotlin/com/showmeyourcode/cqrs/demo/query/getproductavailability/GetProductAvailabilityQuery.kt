package com.showmeyourcode.cqrs.demo.query.getproductavailability

import com.showmeyourcode.cqrs.demo.infra.Query
import java.util.*

class GetProductAvailabilityQuery(val productId: UUID) : Query<GetProductAvailabilityQueryResult>
