package com.example.vthai.sidecarprototype.datasource

import com.example.vthai.sidecarprototype.model.Doctor

object DataSourceManager {

    fun retrieveDoctorOverviewData(docId: String, listener: DoctorOverviewAsyncTask.Listener) {
        DoctorDataSource.retrieveDoctorOverviewData(docId, createPlainDoctor(), listener)
    }

    private fun createPlainDoctor(): Doctor {
        return Doctor()
    }

}