package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast


import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.math.watermelon.databinding.ItemViewBinding
import com.math.watermelon.room.AppDatabase
import com.math.watermelon.room.mathdata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class itemRecylcerAdapter(
    var itemlist: List<mathdata>,
    val fragmentManager: FragmentManager,
    val context:Context
) : RecyclerView.Adapter<itemRecylcerAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.listitem
        val itemlistarea = binding.listItemAll

        val db = AppDatabase.getInstance(context)

        init {
            itemlistarea.setOnClickListener {
                fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragcontainer, DetailFragment()
                        .apply {
                            arguments = Bundle().apply {
                                putInt("id", itemlist[layoutPosition].id!!)
                            }
                        }).commit()

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = itemlist[position].topic
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }


}