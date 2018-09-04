package com.example.vthai.sidecarprototype.datasource

import com.example.vthai.sidecarprototype.model.Doctor

object DoctorDataSource {

    private const val DOCTOR_OVERVIEW_URL_FORMAT_REQUIRES_ID = "https://c8e4ece5-082f-4c43-aac1-b0215c1f36a4.mock.pstmn.io/doctors/%s"
    private const val DOCTOR_COSTS_URL_FORMAT_REQUIRES_ID = "https://c8e4ece5-082f-4c43-aac1-b0215c1f36a4.mock.pstmn.io/doctors/%s/costs"

    fun retrieveDoctorOverviewData(docId: String, plainDoctor: Doctor, listener: DoctorOverviewAsyncTask.Listener) {
        DoctorOverviewAsyncTask.retrieveDoctorDataFrom(String.format(DOCTOR_OVERVIEW_URL_FORMAT_REQUIRES_ID, docId), listener, plainDoctor)
    }

    fun retrieveDoctorCostData(docId: String, listener: DoctorCostsAsyncTask.Listener) {
        DoctorCostsAsyncTask.retrieveDoctorDataFrom(String.format(DOCTOR_COSTS_URL_FORMAT_REQUIRES_ID, docId), listener)
    }
}