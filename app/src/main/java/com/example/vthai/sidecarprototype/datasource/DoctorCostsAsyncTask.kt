package com.example.vthai.sidecarprototype.datasource

import android.os.AsyncTask
import com.example.vthai.sidecarprototype.model.DoctorCost
import com.example.vthai.sidecarprototype.utils.Eligibility
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DoctorCostsAsyncTask: AsyncTask<String, String, String>() {

    interface Listener {
        fun onDoctorCostTaskCompleted(doctorCosts: ArrayList<DoctorCost>)
        fun onDoctorCostTaskFailed()
    }

    companion object {
        const val JSON_CODE_KEY = "code"
        const val JSON_PROVIDER_RATE_KEY = "providerRate"
        const val JSON_SIDECAR_RATE_KEY = "sidecarRate"
        const val JSON_STATUS_KEY = "status"
        const val JSON_ITEMS_KEY = "items"
        const val JSON_ID_KEY = "id"
        const val JSON_AMOUNT_KEY = "amount"

        fun retrieveDoctorDataFrom(url: String, listener: Listener) {
            val task = DoctorCostsAsyncTask()
            with(task, {
                this.listener = listener
                execute(url)
            })
        }
    }

    var listener: Listener? = null

    override fun doInBackground(vararg params: String?): String {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        try {
            var url = URL(params[0])
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doOutput = true
            connection.connect()
            val stream = url.openStream()
            reader = BufferedReader(InputStreamReader(stream))
            val buffer = StringBuffer()
            for (line in reader.readLines()) {
                buffer.append(line)
            }
            return buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            listener?.onDoctorCostTaskFailed()
        } finally {
            if (connection != null) {
                connection.disconnect()
            }

            try {
                if (reader != null)
                    reader.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
                listener?.onDoctorCostTaskFailed()
            }
        }
        return "";
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
            val jArray = JSONArray(result)
            var iter = 0
            var docCosts = ArrayList<DoctorCost>()
            while (!jArray.isNull(iter)) {
                val docObject = jArray.getJSONObject(iter++)
                val doctorCost = DataSourceManager.createDoctorCost()
                doctorCost.code = docObject.getString(JSON_CODE_KEY)
                doctorCost.eligibility = docObject.getString(JSON_STATUS_KEY)
                if (doctorCost.eligibility.equals(Eligibility.Denied.name, true)) {
                    docCosts.add(doctorCost)
                    continue
                }
                doctorCost.providerRate = docObject.getDouble(JSON_PROVIDER_RATE_KEY).toFloat()
                doctorCost.sidecarRate = docObject.getDouble(JSON_SIDECAR_RATE_KEY).toFloat()
                val itemsJArray = docObject.getJSONArray(JSON_ITEMS_KEY)
                var itemIter = 0
                while (!jArray.isNull(itemIter)) {
                    val itemJObject = itemsJArray.getJSONObject(itemIter++)
                    val costItem = DataSourceManager.createCostItem()
                    costItem.id = itemJObject.getString(JSON_ID_KEY)
                    costItem.amount = itemJObject.getDouble(JSON_AMOUNT_KEY).toFloat()
                    doctorCost.costItems.add(costItem)
                }
                docCosts.add(doctorCost)
            }
            listener?.onDoctorCostTaskCompleted(docCosts)
        } catch (e: Exception) {
            listener?.onDoctorCostTaskFailed()
        }
    }

}