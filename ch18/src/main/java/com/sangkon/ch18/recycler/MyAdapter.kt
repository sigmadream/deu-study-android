package com.sangkon.ch18.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangkon.ch18.databinding.ItemMainBinding
import com.sangkon.ch18.model.ItemModel


class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val datas: MutableList<ItemModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding

        //add......................................
        val model=datas!![position]
        binding.itemTitle.text=model.title
        binding.itemDesc.text=model.description
        binding.itemTime.text="${model.author} At ${model.publishedAt}"
        model.urlToImage?.let {
            if(!it.equals("null")) {
                Glide.with(context)
                    .load(model.urlToImage)
                    .into(binding.itemImage)
            }
        }


    }
}