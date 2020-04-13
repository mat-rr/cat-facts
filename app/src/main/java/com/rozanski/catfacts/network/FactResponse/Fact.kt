package com.rozanski.catfacts.network.FactResponse

data class Fact(
    val _id: String,
    val text: String,
    val type: String,
    val upvotes: Int,
    val user: User,
    val userUpvoted: Any?
)