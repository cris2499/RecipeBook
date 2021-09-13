package com.example.recipebook.api

import com.example.recipebook.model.Ricetta
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeApi {
    @GET("api/recipes")
    fun getArticoli(): Call<MutableList<Ricetta>>

    @GET("api/recipes/{id}")
    fun getDettaglio(@Path("id") id: String): Call<Ricetta>

    @POST("api/recipes")
    fun addRecipe(@Body ricetta: Ricetta): Call<Ricetta>
}