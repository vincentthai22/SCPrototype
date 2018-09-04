package com.example.vthai.sidecarprototype.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.model.DoctorCostItem
import kotlinx.android.synthetic.main.recycler_cost_item.view.*

class DoctorCostItemRecyclerAdapter(var context: Context): RecyclerView.Adapter<DoctorCostItemRecyclerAdapter.CostItemViewHolder>() {

    var costItemList = ArrayList<DoctorCostItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostItemViewHolder {
        return CostItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_cost_item, parent, false))
    }

    override fun getItemCount(): Int {
        return costItemList.size
    }

    override fun onBindViewHolder(holder: CostItemViewHolder, position: Int) {
        val item = costItemList[position]
        with(holder, {
            itemLabelTextView.text = String.format("#%s", item.id)
            itemCostTextView.text = String.format("$%.2f", item.amount)
        })
    }

    inner class CostItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemLabelTextView = itemView.innerCostItemLabelTextView
        val itemCostTextView = itemView.innerCostItemValueTextView
    }
}