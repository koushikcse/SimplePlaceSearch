package com.kusu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kusu.databinding.ItemSearchLayoutBinding

class SearchAdapter(private val context: Context, private val addressList: List<SearchResult>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var itemClickListner: ItemClickListner

    fun setItemClickListner(itemClickListner: ItemClickListner) {
        this.itemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_search_layout,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchAddress = addressList[position]
        holder.binding.tvName.text = searchAddress.label
        holder.binding.tvAddress.text = searchAddress.address
        holder.itemView.setOnClickListener { itemClickListner.onItemClick(position, searchAddress) }
    }

    interface ItemClickListner {
        fun onItemClick(pos: Int, data: SearchResult)
    }

    inner class ViewHolder(val binding: ItemSearchLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: SearchResult) {
//            binding.item = item
//            binding.executePendingBindings()
//        }
    }

}