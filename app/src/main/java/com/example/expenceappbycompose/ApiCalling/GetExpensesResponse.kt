package com.example.expenceappbycompose.ApiCalling

data class GetExpensesResponse(
    val message: List<Message>,
    val status: Int
)