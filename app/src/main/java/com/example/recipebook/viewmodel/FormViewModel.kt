package com.example.recipebook.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipebook.api.RecipeApi
import com.example.recipebook.model.Ricetta
import com.example.recipebook.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class FormViewModel: ViewModel() {
    val isGood: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun addRecipe(title: String, description: String){

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: RecipeApi = retrofit.create(RecipeApi::class.java)

        var recipe = Ricetta("1111", title, description)

        var call: Call<Ricetta> = api.addRecipe(recipe)

        call.enqueue(object : Callback<Ricetta> {
            override fun onResponse(call: Call<Ricetta>, response: Response<Ricetta>) {
                if (!response.isSuccessful()) {
                    isGood.value = false
                }
                isGood.value = true
            }

            override fun onFailure(call: Call<Ricetta>, t: Throwable) {
                isGood.value = false
            }
        })

    }
}