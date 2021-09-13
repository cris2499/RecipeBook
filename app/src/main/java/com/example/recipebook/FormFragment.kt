package com.example.recipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipebook.databinding.FragmentDetailsBinding
import com.example.recipebook.databinding.FragmentFormBinding
import com.example.recipebook.model.Ricetta
import com.example.recipebook.viewmodel.FormViewModel
import com.squareup.picasso.Picasso


class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(FormViewModel::class.java)

        viewModel.isGood.observe(viewLifecycleOwner, Observer {
            if (viewModel.isGood.value == true){
                val bundle = bundleOf("newRecipeTitle" to binding.formRecipeTitle.text.toString(),
                    "newRecipeDescription" to binding.formRecipeDescription.text.toString())

                findNavController().navigate(R.id.action_formFragment_to_recipesFragment, bundle)
            } else {
                Toast.makeText(context, "Qualcosa è andato storto!", Toast.LENGTH_LONG).show()
            }
        })

        binding.formSubmitButton.setOnClickListener {
            viewModel.addRecipe(binding.formRecipeTitle.text.toString(), binding.formRecipeDescription.text.toString())
        }
    }

}