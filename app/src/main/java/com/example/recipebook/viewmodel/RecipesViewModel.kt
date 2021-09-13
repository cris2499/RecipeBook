package com.example.recipebook.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipebook.api.RecipeApi
import com.example.recipebook.model.Ricetta
import com.example.recipebook.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesViewModel : ViewModel() {

    private lateinit var db: FirebaseFirestore

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val ricette: MutableLiveData<MutableList<Ricetta>> by lazy {
        MutableLiveData<MutableList<Ricetta>>()
    }

    fun getName(email: String){
        db = FirebaseFirestore.getInstance()

        val docRef = db.collection("users").document(email.uppercase())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    name.value = document["first"].toString() + " " + document["last"].toString()
                }
            }
    }

    fun getRecipes(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api: RecipeApi = retrofit.create(RecipeApi::class.java)

        val call: Call<MutableList<Ricetta>> = api.getArticoli()

        call.enqueue(object : Callback<MutableList<Ricetta>> {
            override fun onResponse(call: Call<MutableList<Ricetta>>, response: Response<MutableList<Ricetta>>) {
                if (!response.isSuccessful()) {
                    Log.d("Codice", response.code().toString())
                    return
                }
                    ricette.value = response.body()!!
            }

            override fun onFailure(call: Call<MutableList<Ricetta>>, t: Throwable) {
                Log.d("Messaggio", t.message.toString())
            }
        })
    }
}