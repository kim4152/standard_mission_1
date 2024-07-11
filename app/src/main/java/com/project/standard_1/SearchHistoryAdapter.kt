package com.project.standard_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.standard_1.databinding.SearchHistoryItemBinding
import com.project.standard_1.databinding.SearchPopularItemBinding
import com.project.standard_1.databinding.TitleItemBinding


class SearchHistoryAdapter(val onClick: (String) -> Unit) :
    ListAdapter<Root, RecyclerView.ViewHolder>(diffUtil) {

    inner class TitleHolder(val binding:TitleItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(root: Root){
            binding.textView.setText(root.text)
        }
    }
    inner class SearchHistoryHolder(val binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(root: Root) {
            binding.textView.setText(root.text)
            binding.close.setImageResource(R.drawable.ic_close_24px)
            binding.icon.setImageResource(R.drawable.ic_schedule_24px)
            binding.close.setOnClickListener { removeHistory(adapterPosition) }
        }
    }
    inner class SearchPopulatHolder(val binding: SearchPopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(root: Root) {
            binding.textView.setText(root.text)
            binding.icon.setImageResource(R.drawable.trending_up_24px)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> TitleHolder(
                TitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            2 -> SearchHistoryHolder(
                SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            3->SearchPopulatHolder(
                SearchPopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val root = currentList[position]
        when (holder) {
            is TitleHolder -> holder.bind(root)
            is SearchHistoryHolder -> holder.bind(root)
            is SearchPopulatHolder -> holder.bind(root)
        }
    }

    override fun getItemViewType(position: Int): Int {
       return currentList[position].type
    }


    override fun getItemCount() = currentList.size

    fun addHistory(text: String) {
        val list = currentList.toMutableList()
        list.add(SearchHistory(2,text))
        submitList(list)
    }

    fun removeHistory(position: Int) {
        val list = currentList.toMutableList()
        list.removeAt(position)
        submitList(list)
    }



    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Root>() {
            override fun areItemsTheSame(oldItem: Root, newItem: Root): Boolean {
                return oldItem.text == newItem.text && oldItem.type == newItem.type
            }
            override fun areContentsTheSame(oldItem: Root, newItem: Root): Boolean {
                return oldItem == newItem
            }


        }
    }

}