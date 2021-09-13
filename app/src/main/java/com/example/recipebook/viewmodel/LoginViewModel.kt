package com.example.recipebook.viewmodel

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    val logged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun loginUser(email: String, password: String, auth: FirebaseAuth){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                logged.value = it.isSuccessful
            }
    }
}