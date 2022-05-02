package com.example.foodapp.ui.db.service

import com.example.foodapp.ui.db.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String): Call<PopularList>

    @GET("categories.php")
    fun getCategoryItem(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealByCategoryList>

}