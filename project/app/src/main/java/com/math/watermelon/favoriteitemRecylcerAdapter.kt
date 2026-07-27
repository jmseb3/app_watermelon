package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast


import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.CoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.math.watermelon.databinding.ItemHeaderBinding
import com.math.watermelon.databinding.ItemViewBinding
import com.math.watermelon.room.AppDatabase
import com.math.watermelon.room.mathdata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class favoriteitemRecylcerAdapter(
        var itemlist: List<mathdata>,
        val fragmentManager: FragmentManager,
        val context: Context,
        val name: String,
        private val scope: CoroutineScope
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1


    inner class ItemViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.listitem
        val itemlistarea = binding.listItemAll

        val db = AppDatabase.getInstance(context)

        init {
            scope.launch {
                val data = withContext(Dispatchers.IO) { db.DataDao().getfavoritdatanotlive() }
                itemlistarea.setOnLongClickListener {
                    Toast.makeText(context, "즐겨찾기가 제거되었습니다.", Toast.LENGTH_SHORT).show()
                    scope.launch(Dispatchers.IO) {
                        db.DataDao().changefavorite(data[layoutPosition-1].id!!, null)
                    }
                    return@setOnLongClickListener true
                }
            }
            itemlistarea.setOnClickListener {
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.fragcontainer, DetailFragment()
                                .apply {
                                    arguments = Bundle().apply {
                                        putInt("id", itemlist[layoutPosition-1].id!!)
                                    }
                                }).commit()

            }

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
            holder.item.text = itemlist[position-1].favorite
        }else if( holder is HeaderViewHolder){
            holder.name.text = this.name

        }

    }

    override fun getItemCount(): Int {
        return itemlist.size + 1
    }


}
