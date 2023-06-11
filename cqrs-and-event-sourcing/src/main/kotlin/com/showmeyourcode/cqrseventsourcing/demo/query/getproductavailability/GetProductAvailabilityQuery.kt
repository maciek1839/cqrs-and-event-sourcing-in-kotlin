package com.showmeyourcode.cqrseventsourcing.demo.query.getproductavailability

import com.showmeyourcode.cqrseventsourcing.demo.domain.Query
import java.util.*

class GetProductAvailabilityQuery(val productId: UUID) :
    Query<GetProductAvailabilityQueryResult>
