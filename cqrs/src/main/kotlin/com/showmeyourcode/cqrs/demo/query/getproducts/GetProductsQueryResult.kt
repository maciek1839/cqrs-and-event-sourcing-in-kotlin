package com.showmeyourcode.cqrs.demo.query.getproducts

import com.showmeyourcode.cqrs.demo.domain.query.ProductQuery

class GetProductsQueryResult(val products: Collection<ProductQuery>)
