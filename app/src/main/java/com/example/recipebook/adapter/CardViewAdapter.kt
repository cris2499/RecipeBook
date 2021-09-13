package com.example.recipebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.databinding.CardviewItemBinding
import com.example.recipebook.model.Ricetta
import com.squareup.picasso.Picasso

class CardViewAdapter(private val dataSet: MutableList<Ricetta>, private val imgs: MutableList<String>) :
    RecyclerView.Adapter<CardViewAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: CardviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(dataSet[position]){
                     binding.description.text = this.body
                     binding.title.text = this.title
                     Picasso.get().load(imgs[position]).into(binding.img);
                     binding.dettaglio.setOnClickListener {
                     val bundle = bundleOf("id" to this._id, "img" to imgs[position])
                     Navigation.findNavController(it)
                         .navigate(R.id.action_recipesFragment_to_detailsFragment, bundle)
                 }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}