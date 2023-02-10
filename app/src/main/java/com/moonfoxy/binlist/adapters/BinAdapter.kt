package com.moonfoxy.binlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonfoxy.binlist.R
import com.moonfoxy.binlist.databinding.ListItemBinBinding
import com.moonfoxy.binlist.models.BinInfo

class BinAdapter(private val binList: List<BinInfo>) :
    RecyclerView.Adapter<BinAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(bin: BinInfo)
    }

    private var listener: OnItemClickListener? = null

    class ViewHolder(val binding: ListItemBinBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_bin,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bin = binList[position]
        holder.binding.bin = bin
        holder.itemView.setOnClickListener {
            listener?.onItemClick(bin)
        }
    }

    override fun getItemCount() = binList.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
