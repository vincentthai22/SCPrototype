package com.example.vthai.sidecarprototype.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.vthai.sidecarprototype.activities.SplashActivity
import com.example.vthai.sidecarprototype.datasource.DataSourceManager
import com.example.vthai.sidecarprototype.datasource.DoctorCostsAsyncTask
import com.example.vthai.sidecarprototype.datasource.DoctorOverviewAsyncTask
import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashViewModel(app: Application): AndroidViewModel(app), DoctorOverviewAsyncTask.Listener, DoctorCostsAsyncTask.Listener {
    var doctor: MutableLiveData<Doctor>
    var doctorCosts: ArrayList<DoctorCost> = ArrayList()
    var failFlag: MutableLiveData<Boolean>

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        Log.d("SplashVM", "inside init")
//        DataSourceManager.retrieveDoctorCostData(SplashActivity.TEST_DOC_ID, this)
        doctor = MutableLiveData()
        doctor.value = null
        failFlag = MutableLiveData()
        performNetworkCalls()
    }

    private fun performNetworkCalls() {
        try {
            uiScope.launch {
                val doctor = DataSourceManager.retrieveDoctorOverviewByCoroutine(SplashActivity.TEST_DOC_ID)
                val doctorCosts = DataSourceManager.retrieveDoctorCostDataByCoroutine(SplashActivity.TEST_DOC_ID)
                doctor.doctorCosts.addAll(doctorCosts)
                this@SplashViewModel.doctor.value = doctor
            }
        } catch (e: Exception) {
            //network call failed
            failFlag.value=true
        }
    }

    fun notifyViewModelOnDestroy() {
        viewModelJob.cancel()
    }

    override fun onDoctorCostTaskCompleted(doctorCosts: ArrayList<DoctorCost>) {
        this.doctorCosts = doctorCosts
        DataSourceManager.retrieveDoctorOverviewData(SplashActivity.TEST_DOC_ID, this)
    }

    override fun onOverviewTaskCompleted(doctor: Doctor?) {
        doctor?.doctorCosts?.addAll(doctorCosts)
        this.doctor.value = doctor
    }

    override fun onOverviewTaskFailed() {
        failFlag.value = true
    }

    override fun onDoctorCostTaskFailed() {
        failFlag.value = true
    }
}