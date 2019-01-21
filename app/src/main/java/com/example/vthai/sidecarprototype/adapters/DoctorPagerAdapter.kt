package com.example.vthai.sidecarprototype.adapters

import android.arch.lifecycle.Observer
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.databinding.ContentDoctorCostsBinding
import com.example.vthai.sidecarprototype.databinding.ContentDoctorOverviewBinding
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.viewmodels.DoctorViewModel
import kotlinx.android.synthetic.main.content_doctor_costs.view.*

class DoctorPagerAdapter(var context: AppCompatActivity, var viewModel: DoctorViewModel) : PagerAdapter() {

    lateinit var overviewView: View
    lateinit var costsView: View
    var costsAdapter: DoctorCostsRecyclerAdapter? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = if (position == 0) {
            ContentDoctorOverviewBinding.inflate(LayoutInflater.from(context), container, false).apply {
                doctor = this@DoctorPagerAdapter.viewModel.doctor
                viewModel = this@DoctorPagerAdapter.viewModel
            }.root
        } else {
            ContentDoctorCostsBinding.inflate(LayoutInflater.from(context), container, false).apply {
                setupCostsView(root)
                viewModel = this@DoctorPagerAdapter.viewModel
            }.root
        }
        container.addView(view)
        return view
    }

    private fun setupCostsView(view: View) {
        costsAdapter = DoctorCostsRecyclerAdapter(context)
        view.costsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.costsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        view.costsRecyclerView.adapter = costsAdapter
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return MAX_DOCTOR_PAGES
    }

    companion object {
        const val MAX_DOCTOR_PAGES = 2
    }
}