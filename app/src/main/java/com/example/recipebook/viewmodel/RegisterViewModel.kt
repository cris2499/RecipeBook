package com.example.recipebook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {

    lateinit var db: FirebaseFirestore

    val registered: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun registerUser(name: String, surname: String, email: String, password: String, auth: FirebaseAuth){

        db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                insertDB(name, surname, email.uppercase())
                registered.value = it.isSuccessful
            }
    }

    private fun insertDB(name:String, surname:String, email: String){
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = name
        user["last"] = surname

        db.collection("users")
            .document(email).set(user)
    }
}