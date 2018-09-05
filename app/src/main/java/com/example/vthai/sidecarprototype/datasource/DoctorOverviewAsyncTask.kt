package com.example.vthai.sidecarprototype.datasource

import android.os.AsyncTask
import android.util.Log
import com.example.vthai.sidecarprototype.model.Doctor
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * DoctorOverviewAsyncTask
 *
 * Function: Responsible for retrieving general doctor information via GET request.
 */
class DoctorOverviewAsyncTask: AsyncTask<String, String, String>() {

    /**
     * DoctorOverviewAsyncTask.Listener
     *
     * function: Responsible for callbacks to the activity.
     */
    interface Listener {
        fun onOverviewTaskCompleted(doctor: Doctor?)
        fun onOverviewTaskFailed()
    }

    companion object {
        const val JSON_ADDRESS_KEY = "address"
        const val JSON_STREET_KEY = "street"
        const val JSON_STREET2_KEY = "street2"
        const val JSON_CITY_KEY = "city"
        const val JSON_STATE_KEY = "state"
        const val JSON_ZIP_KEY = "zipCode"
        const val JSON_PHONE_KEY = "phone"
        const val JSON_SPECIALTIES_KEY = "specialties"
        const val JSON_PRICE_KEY = "price"
        const val ADDRESS_FORMAT_ST_ST2_CITY_ST_ZIP = "%s%s %s, %s %s"

        fun retrieveDoctorDataFrom(url: String, listener: Listener, doctor: Doctor) {
            val task = DoctorOverviewAsyncTask()
            with(task, {
                this.listener = listener
                this.doctor = doctor
                execute(url)
            })
        }
    }

    var listener: Listener? = null
    var doctor: Doctor? = null

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
            listener?.onOverviewTaskFailed()
        } finally {
            if (connection != null) {
                connection.disconnect()
            }

            try {
                if (reader != null)
                    reader.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
                listener?.onOverviewTaskFailed()
            }
        }
        return "";
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
            Log.d("DOCTOROVERVIEW: ", result)
            val jObject = JSONObject(result)
            val addressJObject = jObject.getJSONObject(JSON_ADDRESS_KEY)
            val phone = jObject.getString(JSON_PHONE_KEY)
            val specialtiesJArray = jObject.getJSONArray(JSON_SPECIALTIES_KEY)
            val priceString = jObject.getString(JSON_PRICE_KEY)
            doctor?.address = String.format(ADDRESS_FORMAT_ST_ST2_CITY_ST_ZIP,
                    addressJObject.getString(JSON_STREET_KEY),
                    addressJObject.getString(JSON_STREET2_KEY),
                    addressJObject.getString(JSON_CITY_KEY),
                    addressJObject.getString(JSON_STATE_KEY),
                    addressJObject.getString(JSON_ZIP_KEY))
            doctor?.phone = phone
            doctor?.prices = priceString
            var iter = 0
            while (!specialtiesJArray.isNull(iter)) {
                val specialty: String? = specialtiesJArray[iter++] as? String
                if (specialty != null) doctor?.specialties?.add(specialty)
            }
            listener?.onOverviewTaskCompleted(doctor)
        } catch(e: Exception) {
            listener?.onOverviewTaskFailed()
        }
    }
}
