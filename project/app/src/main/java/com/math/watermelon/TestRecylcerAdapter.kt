package com.math.watermelon

import android.content.Context
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
        val context: Context,
        val name: String,
        layoutManager: GridLayoutManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    init {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) return 3
                return 1
            }
        }
    }


    inner class ItemViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.listitem
        val itemlistarea = binding.listItemAll

        init {

        }
    }

    inner class HeaderViewHolder(binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.headerTitle

        init {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeaderViewHolder(binding)
        } else {
            val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.item.text = itemlist[position-1]
        }else if( holder is HeaderViewHolder){
            holder.name.text = this.name

        }

    }

    override fun getItemCount(): Int {
        return itemlist.size + 1
    }


}