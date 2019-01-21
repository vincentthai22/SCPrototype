package com.example.vthai.sidecarprototype.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.utils.Eligibility
import kotlinx.android.synthetic.main.recycler_doctor_cost.view.*

class DoctorCostsRecyclerAdapter(var context: Context) : RecyclerView.Adapter<DoctorCostsRecyclerAdapter.CostsViewHolder>() {

    var costList = ArrayList<DoctorCost>()
        set(value) {
            field = value
            for (i in 0 until costList.size) {
                selectedMap[i] = false
            }
            notifyDataSetChanged()
        }

    var selectedMap = HashMap<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostsViewHolder {
        return CostsViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_doctor_cost, parent, false))
    }

    override fun getItemCount(): Int {
        return costList.size
    }

    override fun onBindViewHolder(holder: CostsViewHolder, position: Int) {
        val item = costList[position]
        with(holder) {
            costIDTextView.text = "#${item.code}"
            providerRateTextView.text = String.format("$%.2f", item.providerRate)
            coverRateTextView.text = String.format("$%.2f", item.sideCarRate)
            if (item.eligibility.equals(Eligibility.Denied.name, true)) {
                deniedImageView.visibility = View.VISIBLE
                deniedTextView.visibility = View.VISIBLE
                coverRateTextView.text = "$—"
                providerRateTextView.text = "$—"
            } else {
                deniedImageView.visibility = View.GONE
                deniedTextView.visibility = View.GONE
            }
            if (isSelected(position) == true) {
                groupImageView.rotation = 45f
                recyclerView.visibility = View.VISIBLE
            } else {
                groupImageView.rotation = 0f
                recyclerView.visibility = View.GONE
            }
            groupImageView.tag = position
        }
    }

    private fun isSelected(pos: Int): Boolean? {
        return selectedMap[pos]
    }

    inner class CostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.costItemRecyclerView
        val groupImageView = itemView.groupImageView
        val costIDTextView = itemView.costIDTextView
        val providerRateTextView = itemView.providerRateTextView
        val coverRateTextView = itemView.coversRateTextView
        val deniedImageView = itemView.deniedImageView
        val deniedTextView = itemView.deniedTextView
        var adapter: DoctorCostItemRecyclerAdapter? = null

        init {
            groupImageView.setOnClickListener {
                with(groupImageView) {
                    val pos = tag as Int
                    if (isSelected(pos) == false) { //initial click event or was closed
                        selectedMap[pos] = true
                        val doctorCost = costList[pos]
                        adapter?.costItemList = doctorCost.costItems
                        if (doctorCost.costItems.isNotEmpty()) {
                            recyclerView.visibility = View.VISIBLE
                            animate().rotation(45f).start()
                            recyclerView.animate()
                                    .setDuration(300)
                                    .setInterpolator(DecelerateInterpolator())
                                    .setUpdateListener {
                                        val newHeight = (recyclerView.minimumHeight * it.animatedFraction).toInt()
                                        recyclerView.layoutParams.height = if (newHeight < 1) 1 else newHeight
                                        recyclerView.requestLayout()
                                    }
                                    .start()

                        }
                        return@with
                    } else {
                        selectedMap[pos] = false
                        animate().rotation(0f).start()
                        recyclerView.animate().withEndAction { recyclerView.visibility = View.GONE }
                                .setDuration(300)
                                .setInterpolator(DecelerateInterpolator())
                                .setUpdateListener {
                                    val newHeight = (recyclerView.minimumHeight * (1f - it.animatedFraction)).toInt()
                                    recyclerView.layoutParams.height = if (newHeight < 1) 1 else newHeight
                                    recyclerView.requestLayout()
                                }
                                .start()
                    }
                }
            }
            adapter = DoctorCostItemRecyclerAdapter(context)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
            recyclerView.pivotY = 0f
        }
    }
}