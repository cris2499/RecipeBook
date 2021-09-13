package com.example.recipebook.authorization

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipebook.R
import com.example.recipebook.RecipeActivity
import com.example.recipebook.databinding.FragmentLoginBinding
import com.example.recipebook.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var registerTextview: TextView
    lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editEmail = binding.editEmail
        editPassword = binding.editPassword
        val submitButton: Button = binding.submitButton
        registerTextview = binding.registerTextview

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.logged.observe(viewLifecycleOwner, Observer {
            if (viewModel.logged.value == true)
                startActivity(Intent(context, RecipeActivity::class.java))
            else
                Toast.makeText(context, "Riprova di nuovo!", Toast.LENGTH_LONG).show()
        })

        submitButton.setOnClickListener {
            viewModel.loginUser(editEmail.text.toString(), editPassword.text.toString(), auth)
        }

        registerTextview.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(context, RecipeActivity::class.java))
        }
    }
}