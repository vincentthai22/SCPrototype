package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Doctor: Parcelable {

    var name = "Alice Chang"

    @SerializedName("address")
    var address = Address()
    var doctorCosts = ArrayList<DoctorCost>()
    @SerializedName("phone")
    var phone = ""
    @SerializedName("price")
    var prices = ""
    constructor() {

    }

    @SerializedName("specialties")
    var specialties = ArrayList<String>()

    constructor(parcel: Parcel) {
        with(parcel) {
            name = readString() ?: ""
            readStringList(specialties)
            address = readTypedObject(Address.CREATOR)
            phone = readString() ?: ""
            prices = readString() ?: ""
            readTypedList(doctorCosts, DoctorCost.CREATOR)
        }
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        with(p0) {
            writeString(name)
            writeStringList(specialties)
            writeTypedObject(address, 0)
            writeString(phone)
            writeString(prices)
            writeTypedList(doctorCosts)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR: Parcelable.Creator<Doctor> = object: Parcelable.Creator<Doctor> {
            override fun createFromParcel(p0: Parcel): Doctor {
                return Doctor(p0)
            }

            override fun newArray(p0: Int): Array<Doctor?> {
                return arrayOfNulls(p0)
            }
        }

    }

}