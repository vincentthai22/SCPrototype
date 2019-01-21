package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable
import com.example.vthai.sidecarprototype.utils.Eligibility
import com.google.gson.annotations.SerializedName

class DoctorCost : Parcelable {

    var code = ""
    var providerRate = 0.0f
    var sideCarRate = 0.0f
    @SerializedName("status")
    var eligibility = ""

    @SerializedName("items")
    var costItems = ArrayList<DoctorCostItem>()

    constructor() {

    }

    constructor(parcel: Parcel?) {
        if (parcel != null) {
            with(parcel, {
                code = readString() ?: ""
                eligibility = readString() ?: ""
                providerRate = readFloat() ?: 0f
                sideCarRate = readFloat() ?: 0f
                readTypedList(costItems, DoctorCostItem.CREATOR)
            })
        }
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        with(p0, {
            writeString(code)
            writeString(eligibility)
            writeFloat(providerRate)
            writeFloat(sideCarRate)
            writeTypedList(costItems)
        })
    }

    override fun describeContents(): Int {
        return 0;
    }

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR: Parcelable.Creator<DoctorCost> = object : Parcelable.Creator<DoctorCost> {
            override fun createFromParcel(p0: Parcel): DoctorCost {
                return DoctorCost(p0)
            }

            override fun newArray(p0: Int): Array<DoctorCost?> {
                return arrayOfNulls(p0)
            }
        }
    }
}