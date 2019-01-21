package com.example.vthai.sidecarprototype.model

import android.os.Parcel
import android.os.Parcelable

class Address: Parcelable {
    var street = ""
    var street2 = ""
    var city = ""
    var state = ""
    var zipCode = ""
    override fun toString(): String {
        return String.format(ADDRESS_FORMAT_ST_ST2_CITY_ST_ZIP,
                street,
                street2,
                city,
                state,
                zipCode)
    }

    constructor(parcel: Parcel) {
        with(parcel) {
            street = parcel.readString()
            street2 = parcel.readString()
            city = parcel.readString()
            state = parcel.readString()
            zipCode = parcel.readString()
        }
    }

    constructor(street: String, street2: String, city: String, state: String, zipCode: String) {
        this.street = street
        this.street2 = street2
        this.city = city
        this.state = state
        this.zipCode = zipCode
    }

    constructor() {
        this.street = ""
        this.street2 = ""
        this.city = ""
        this.state = ""
        this.zipCode = ""
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.let {
            it.writeString(street)
            it.writeString(street2)
            it.writeString(city)
            it.writeString(state)
            it.writeString(zipCode)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR: Parcelable.Creator<Address> = object: Parcelable.Creator<Address> {
            override fun createFromParcel(p0: Parcel): Address {
                return Address(p0)
            }

            override fun newArray(p0: Int): Array<Address?> {
                return arrayOfNulls(p0)
            }
        }
        const val ADDRESS_FORMAT_ST_ST2_CITY_ST_ZIP = "%s%s %s, %s %s"
    }
}