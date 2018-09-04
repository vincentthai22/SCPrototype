package com.example.vthai.sidecarprototype.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.adapters.DoctorPagerAdapter
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.viewmodels.DoctorViewModel
import kotlinx.android.synthetic.main.activity_doctor.*

class DoctorActivity : AppCompatActivity() {

    lateinit var adapter: DoctorPagerAdapter
    lateinit var viewModel: DoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        setupToolbar()
        setupViewModel()
        setupViewPager()
        viewModel.notifyViewModelOnCreate(intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(doctorToolbar)
        doctorToolbarTitle.text = title
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(DoctorViewModel::class.java)
        viewModel.doctor.observe(this, doctorObserver)
    }

    private fun setupViewPager() {
        adapter = DoctorPagerAdapter(this, viewModel)
        doctorViewPager.adapter = adapter
        doctorTabLayout.setupWithViewPager(doctorViewPager, false)
        doctorTabLayout.getTabAt(0)?.setText(getString(R.string.doctor_tab_overview))
        doctorTabLayout.getTabAt(1)?.setText(getString(R.string.doctor_tab_costs))
    }

    private val doctorObserver = Observer<Doctor>() {
        doctorTitleTextView.text = it?.name
        if((it?.specialties ?: ArrayList()).isNotEmpty()) {
            doctorSubTitleTextView.text = it?.specialties?.get(0)
        }
    }

    private fun setupTabLayout() {

    }

    companion object {
        val DOCTOR_INTENT_KEY = "DOCTOR_INTENT_KEY"
    }
}