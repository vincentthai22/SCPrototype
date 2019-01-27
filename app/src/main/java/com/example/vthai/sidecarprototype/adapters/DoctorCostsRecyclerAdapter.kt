package com.example.vthai.sidecarprototype.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.databinding.RecyclerDoctorCostBinding
import com.example.vthai.sidecarprototype.model.DoctorCost
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
        return CostsViewHolder(RecyclerDoctorCostBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return costList.size
    }

    override fun onBindViewHolder(holder: CostsViewHolder, position: Int) {
        with(holder) {
            binding.doctorCost = costList[position]
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

    inner class CostsViewHolder(var binding: RecyclerDoctorCostBinding) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView = itemView.costItemRecyclerView
        val groupImageView = itemView.groupImageView
        var adapter: DoctorCostItemRecyclerAdapter? = null

        init {
            groupImageView.setOnClickListener {
                with(groupImageView) {
                    val pos = tag as Int
                    if (isSelected(pos) == false) { //initial click event or was closed
                        selectedMap[pos] = true
                        val doctorCost = costList[pos]
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