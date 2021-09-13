package com.example.recipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebook.adapter.CardViewAdapter
import com.example.recipebook.databinding.FragmentRecipesBinding
import com.example.recipebook.model.Ricetta
import com.example.recipebook.viewmodel.RecipesViewModel
import com.google.firebase.auth.FirebaseAuth

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RecipesViewModel
    private lateinit var imgsList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val newRecipeTitle = arguments?.getString("newRecipeTitle")
        val newRecipeDescription = arguments?.getString("newRecipeDescription")

        imgsList = mutableListOf("https://th.bing.com/th/id/R.8769580612d10f2e7782a81db801a177?rik=1OIKSMFMJjiZsg&riu=http%3a%2f%2f4.bp.blogspot.com%2f-ygxnBTQPJ4I%2fUNC4mHFa7mI%2fAAAAAAAAJao%2fpGCMOzG3X3M%2fs1600%2fIMG_4558.JPG&ehk=po3%2bCe1oIXzGpx%2bQmYLhSNZmbGVcgnvLkV0Qv0HKUiE%3d&risl=&pid=ImgRaw&r=0",
        "https://th.bing.com/th/id/OIP.oAGIW9uo5tHyuK2X1fqjDAHaE8?pid=ImgDet&rs=1",
        "https://th.bing.com/th/id/OIP.w321wHHRLiMVlv-JgGFs2AHaHa?pid=ImgDet&rs=1",
        "https://th.bing.com/th/id/OIP.taxu_gkK12MtZtqXdNaC2QHaLH?pid=ImgDet&rs=1",
        "https://th.bing.com/th/id/OIP.bC5bajLzVn-V3-w4me32mAHaE8?pid=ImgDet&rs=1",
        "https://th.bing.com/th/id/R.78f68f7de49053b4ee38a59a15092486?rik=PvRhGf9FbiVfKw&pid=ImgRaw&r=0",
        "https://th.bing.com/th/id/OIP.rKoNyzKXzTnhhX232DIJ6QHaHa?pid=ImgDet&rs=1",
        "https://th.bing.com/th/id/OIP.bClpHgvfyXeuCuBFm_20zgHaFj?pid=ImgDet&rs=1",
        "https://images.media-allrecipes.com/userphotos/7102338.jpg",
        "https://th.bing.com/th/id/R.cd8bc23d98e6a739001f526ffa021c79?rik=BnHSK0eSmsR6TQ&pid=ImgRaw&r=0")

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)

        viewModel.name.observe(viewLifecycleOwner, Observer {
            binding.nameText.text = viewModel.name.value
        })

        viewModel.ricette.observe(viewLifecycleOwner, Observer {
            val ad = CardViewAdapter(it, imgsList)
            binding.recipesList.adapter = ad
            binding.recipesList.layoutManager = LinearLayoutManager(context)
        })

        auth.currentUser?.email?.let { viewModel.getName(it) }

        viewModel.getRecipes()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_formFragment)
        }

        if (arguments != null){
            
        }
    }
}