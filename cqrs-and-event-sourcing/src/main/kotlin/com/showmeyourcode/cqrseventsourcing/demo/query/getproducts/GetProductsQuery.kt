package com.showmeyourcode.cqrseventsourcing.demo.query.getproducts

import com.showmeyourcode.cqrseventsourcing.demo.domain.Query
import com.showmeyourcode.cqrseventsourcing.demo.domain.query.ProductQuery

class GetProductsQuery(val listSize: Int = 10) : Query<GetProductsQueryResult>

class GetProductsQueryResult(val products: Collection<ProductQuery>)
