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
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.viewmodels.DoctorViewModel
import kotlinx.android.synthetic.main.content_doctor_costs.view.*
import kotlinx.android.synthetic.main.content_doctor_overview.view.*

class DoctorPagerAdapter(var context: AppCompatActivity, var viewModel: DoctorViewModel): PagerAdapter() {

    lateinit var overviewView: View
    lateinit var costsView: View
    var costsAdapter: DoctorCostsRecyclerAdapter? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = if(position == 0) {
            val temp = LayoutInflater.from(context).inflate(R.layout.content_doctor_overview, container, false)
            overviewView = temp
            setupOverviewView(temp)
            temp
        } else {
            val temp = LayoutInflater.from(context).inflate(R.layout.content_doctor_costs, container, false)
            costsView = temp
            setupCostsView(temp)
            temp
        }
        container.addView(view)
        return view
    }

    private fun setupOverviewView(view: View) {
        view.overviewCallImageView.setOnClickListener { viewModel.notifyViewModelOnCallClicked() }
        view.overviewDirectionsImageView.setOnClickListener{ viewModel.notifyViewModelOnDirectionsClicked()}
        viewModel.doctorAddressLiveData.observe(context, addressObserver)
        viewModel.doctorPhoneLiveData.observe(context, phoneObserver)
        viewModel.doctorSpecialtiesLiveData.observe(context, specialtyObserver)
        viewModel.doctorPricesLiveData.observe(context, pricesObserver)
    }

    private fun setupCostsView(view: View) {
        costsAdapter = DoctorCostsRecyclerAdapter(context)
        view.costsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.costsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        view.costsRecyclerView.adapter = costsAdapter

        viewModel.doctorCostListLiveData.observe(context, costsObserver)
    }

    private val addressObserver = Observer<String> {
        overviewView.addressSubHeaderValueTextView.text = it
    }

    private val phoneObserver = Observer<String> {
        overviewView.phoneSubHeaderValueTextView.text = it
    }

    private val specialtyObserver = Observer<String> {
        overviewView.specialtiesSubHeaderValueTextView.text = it
    }

    private val pricesObserver = Observer<String> {
        overviewView.pricesSubHeaderValueTextView.text = it
    }


    private val costsObserver = Observer<List<DoctorCost>>() {
        costsAdapter?.costList = it as? ArrayList<DoctorCost> ?: ArrayList()
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