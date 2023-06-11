package com.showmeyourcode.cqrseventsourcing.demo.query.getproducts

import com.showmeyourcode.cqrseventsourcing.demo.domain.Query

class GetProductsQuery(val listSize: Int = 10) : Query<GetProductsQueryResult>
