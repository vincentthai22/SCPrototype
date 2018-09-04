package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable

class Doctor: Parcelable {

    var name = "Alice Chang"
    var specialties = ArrayList<String>()
    var address = ""
    var phone = ""
    var prices = ""

    constructor() {

    }

    constructor(parcel: Parcel) {
        with(parcel, {
            name = readString()
            readStringList(specialties)
            address = readString()
            phone = readString()
            prices = readString()
        })
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        with(p0, {
            writeString(name)
            writeStringList(specialties)
            writeString(address)
            writeString(phone)
            writeString(prices)
        })
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