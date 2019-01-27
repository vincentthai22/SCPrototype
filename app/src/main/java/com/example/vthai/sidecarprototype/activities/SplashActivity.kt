package com.example.vthai.sidecarprototype.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.vthai.sidecarprototype.R
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.viewmodels.SplashViewModel

class SplashActivity: AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSplashViewModel()
    }

    private fun setupSplashViewModel() {
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SplashViewModel::class.java)
        viewModel.doctor.observe(this, doctorObserver)
        viewModel.failFlag.observe(this, failFlagObserver)
    }

    private val doctorObserver = Observer<Doctor>() {
        if(it != null) startDoctorActivity(it)
    }

    private val failFlagObserver = Observer<Boolean> {
        showFailDialog()
    }

    private fun showFailDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.data_load_failed_title))
                .setMessage(getString(R.string.data_load_failed_msg))
                .setPositiveButton(getString(R.string.ok), { _, _ -> finish() })
                .setCancelable(false)
                .show()
    }

    private fun startDoctorActivity(doctor: Doctor) {
        val intent = Intent(this, DoctorActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DoctorActivity.DOCTOR_INTENT_KEY, doctor)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.notifyViewModelOnDestroy()
    }

    companion object {
        const val TEST_DOC_ID = "12345"
    }
}