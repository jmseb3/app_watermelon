package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.math.watermelon.databinding.ItemHeaderBinding
import com.math.watermelon.databinding.ItemViewBinding

class TestRecylcerAdapter(
    var itemlist: List<String>,
    val fragmentManager: FragmentManager,
    val context: Context
) : RecyclerView.Adapter<TestRecylcerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.listitem
        val itemlistarea = binding.listItemAll

        init {
            itemlistarea.setOnClickListener {
                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragcontainer,HasulFragment().apply {
                        arguments = Bundle().apply {
                            putInt("hasul_id",layoutPosition+1)
                        }
                    }).commit()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.item.text = itemlist[position]
        }

    }

    override fun getItemCount(): Int {
        return itemlist.size
    }


}