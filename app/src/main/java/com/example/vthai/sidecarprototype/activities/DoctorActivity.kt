package com.example.vthai.sidecarprototype.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.adapters.DoctorPagerAdapter
import com.example.vthai.sidecarprototype.databinding.ActivityDoctorBinding
import com.example.vthai.sidecarprototype.viewmodels.DoctorViewModel
import kotlinx.android.synthetic.main.activity_doctor.*

class DoctorActivity : AppCompatActivity() {

    lateinit var adapter: DoctorPagerAdapter
    lateinit var viewModel: DoctorViewModel
    lateinit var binding: ActivityDoctorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor)
        binding.setLifecycleOwner(this)
        setupToolbar()
        setupViewModel()
        setupViewPager()
        binding.viewModel = viewModel
        viewModel.notifyViewModelOnCreate(intent)
    }

    private fun setupToolbar() {
        setSupportActionBar(doctorToolbar)
        doctorToolbarTitle.text = title
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(DoctorViewModel::class.java)
        viewModel.doctorTitleTextLiveData.observe(this, titleObserver)
        viewModel.doctorSubTitleTextLiveData.observe(this, subTitleObserver)
        viewModel.directionsFlag.observe(this, directionsObserver)
        viewModel.callFlag.observe(this, callObserver)
    }

    private fun setupViewPager() {
        adapter = DoctorPagerAdapter(this, viewModel)
        doctorViewPager.adapter = adapter
        doctorTabLayout.setupWithViewPager(doctorViewPager, false)
        doctorTabLayout.getTabAt(0)?.setText(getString(R.string.doctor_tab_overview))
        doctorTabLayout.getTabAt(1)?.setText(getString(R.string.doctor_tab_costs))
    }

    private val subTitleObserver = Observer<String> {
        doctorSubTitleTextView.text = it ?: ""
    }

    private val titleObserver = Observer<String> {
        doctorTitleTextView.text = it ?: ""
    }


    private val directionsObserver = Observer<Intent>() {
        try {
            if (it != null) startActivity(it)
        } catch(e: ActivityNotFoundException) {
            Toast.makeText(this, "Please install Maps to use this feature", Toast.LENGTH_LONG).show()
        }
    }

    private val callObserver = Observer<Intent> {
        if (it != null) startActivity(it)
    }

    companion object {
        val DOCTOR_INTENT_KEY = "DOCTOR_INTENT_KEY"
    }
}