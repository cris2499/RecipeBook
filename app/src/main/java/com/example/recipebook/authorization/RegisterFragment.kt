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
import com.example.recipebook.databinding.FragmentRegisterBinding
import com.example.recipebook.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var editRegisterName: EditText
    lateinit var editRegisterSurname: EditText
    lateinit var editRegisterEmail: EditText
    lateinit var editRegisterPassword: EditText
    lateinit var loginTextview: TextView
    lateinit var viewModel: RegisterViewModel
    lateinit var registerButton: Button
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editRegisterName = binding.editRegisterName
        editRegisterSurname = binding.editRegisterSurname
        editRegisterEmail = binding.editRegisterEmail
        editRegisterPassword = binding.editRegisterPassword
        loginTextview = binding.loginTextview
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        registerButton = binding.registerButton

        viewModel.registered.observe(viewLifecycleOwner, Observer {
            if (viewModel.registered.value == true)
                startActivity(Intent(context, RecipeActivity::class.java))
            else
                Toast.makeText(context, "Riprova di nuovo!", Toast.LENGTH_LONG).show()
        })

        registerButton.setOnClickListener {
            viewModel.registerUser(editRegisterName.text.toString(), editRegisterSurname.text.toString()
                    ,editRegisterEmail.text.toString(), editRegisterPassword.text.toString(), auth)
        }

        loginTextview.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(context, RecipeActivity::class.java))
        }
    }
}