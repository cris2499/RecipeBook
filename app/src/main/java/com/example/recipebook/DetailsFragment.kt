package com.example.recipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recipebook.databinding.FragmentDetailsBinding
import com.example.recipebook.databinding.FragmentRecipesBinding
import com.example.recipebook.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getString("id")
        val img = arguments?.getString("img")

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        viewModel.ricetta.observe(viewLifecycleOwner, Observer {
            binding.detailTitle.text = it.title
            binding.detailDescription.text = it.body
            Picasso.get().load(img).into(binding.detailImg)
        })

        viewModel.getRicetta(id!!)
    }
}