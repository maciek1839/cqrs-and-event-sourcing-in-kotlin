package com.showmeyourcode.cqrseventsourcing.demo.query.getproducts

import com.showmeyourcode.cqrseventsourcing.demo.domain.query.ProductQ

class GetProductsQueryResult(val products: Collection<ProductQ>)
