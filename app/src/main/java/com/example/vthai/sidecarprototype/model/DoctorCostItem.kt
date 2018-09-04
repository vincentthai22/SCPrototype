package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable

class DoctorCostItem: Parcelable {

    var id = ""
    var amount = 0f

    constructor() {

    }

    constructor(parcel: Parcel) {
        with(parcel, {
            id = readString() ?: ""
            amount = readFloat() ?: 0f
        })
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        with(p0, {
            writeString(id)
            writeFloat(amount)
        })
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR: Parcelable.Creator<DoctorCostItem> = object: Parcelable.Creator<DoctorCostItem> {
            override fun createFromParcel(p0: Parcel): DoctorCostItem {
                return DoctorCostItem(p0)
            }

            override fun newArray(p0: Int): Array<DoctorCostItem?> {
                return arrayOfNulls(p0)
            }
        }
    }
}