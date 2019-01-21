package com.example.vthai.sidecarprototype.datasource

import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.model.DoctorCostItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * DataSourceManager
 *
 * Function: Handles all server calls and serves as a mediator for all other data sources.
 */
object DataSourceManager {

    val retrofit: Retrofit
    const val BASE_URL = "https://c8e4ece5-082f-4c43-aac1-b0215c1f36a4.mock.pstmn.io"
    val doctorService: DoctorDataSource.Service

    init {
        retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        doctorService = retrofit.create(DoctorDataSource.Service::class.java)
    }

    fun getRetroInstance(): Retrofit {
        return retrofit
    }

    fun retrieveDoctorOverviewData(docId: String, listener: DoctorOverviewAsyncTask.Listener) {
        doctorService.retrieveDoctors(docId)
                .enqueue(object: Callback<Doctor> {
                    override fun onFailure(call: Call<Doctor>, t: Throwable) {
                        listener.onOverviewTaskFailed()
                    }

                    override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                        if(response.isSuccessful) {
                            listener.onOverviewTaskCompleted(response.body())
                        }
                    }
                })
//        DoctorDataSource.retrieveDoctorOverviewData(docId, createDoctor(), listener)
    }

    fun retrieveDoctorCostData(docId: String, listener: DoctorCostsAsyncTask.Listener) {
        doctorService.retrieveDoctorCosts(docId)
                .enqueue(object: Callback<List<DoctorCost>> {
                    override fun onFailure(call: Call<List<DoctorCost>>, t: Throwable) {
                        listener.onDoctorCostTaskFailed()
                    }

                    override fun onResponse(call: Call<List<DoctorCost>>, response: Response<List<DoctorCost>>) {
                        response.body()?.let { nonNullList ->
                            listener.onDoctorCostTaskCompleted(ArrayList(nonNullList))
                        }
                    }
                })
//        DoctorDataSource.retrieveDoctorCostData(docId, listener)
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