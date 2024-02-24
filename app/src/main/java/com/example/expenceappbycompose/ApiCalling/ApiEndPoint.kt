package com.example.expenceappbycompose.ApiCalling

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndPoint {
    @GET("get_expenses.php")
    fun getAllExpenses():Call<GetExpensesResponse>

    @FormUrlEncoded
    @POST("insert.php")
    fun UploadExpense(@Field("itemName") itemName: String, @Field("price") price: Int):Call<AddResponse>

    @FormUrlEncoded
    @POST("delete.php")
    fun DeleteExpense(@Field("id") id: Int):Call<AddResponse>
}