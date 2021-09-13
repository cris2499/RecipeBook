package com.example.recipebook.viewmodel

import android.util.Log
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

class DetailsViewModel: ViewModel() {
    val ricetta: MutableLiveData<Ricetta> by lazy {
        MutableLiveData<Ricetta>()
    }

    fun getRicetta(id: String){

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: RecipeApi = retrofit.create(RecipeApi::class.java)

        val call: Call<Ricetta> = api.getDettaglio(id)

        call.enqueue(object : Callback<Ricetta> {
            override fun onResponse(call: Call<Ricetta>, response: Response<Ricetta>) {
                if (!response.isSuccessful()) {
                    Log.d("Codice", response.code().toString())
                    return
                }
                ricetta.value = response.body()!!
            }

            override fun onFailure(call: Call<Ricetta>, t: Throwable) {
                Log.d("Messaggio", t.message.toString())
            }
        })
    }
}