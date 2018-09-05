package com.example.vthai.sidecarprototype.datasource

import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.model.DoctorCostItem

/**
 * DataSourceManager
 *
 * Function: Handles all server calls and serves as a mediator for all other data sources.
 */
object DataSourceManager {

    fun retrieveDoctorOverviewData(docId: String, listener: DoctorOverviewAsyncTask.Listener) {
        DoctorDataSource.retrieveDoctorOverviewData(docId, createDoctor(), listener)
    }

    fun retrieveDoctorCostData(docId: String, listener: DoctorCostsAsyncTask.Listener) {
        DoctorDataSource.retrieveDoctorCostData(docId, listener)
    }

    fun createDoctor(): Doctor {
        return Doctor()
    }

    fun createDoctorCost(): DoctorCost {
        return DoctorCost()
    }

    fun createCostItem(): DoctorCostItem {
        return DoctorCostItem()
    }
}