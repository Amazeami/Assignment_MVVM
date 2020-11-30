package com.example.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Uidatum : Parcelable {
    @SerializedName("uitype")
    @Expose
    var uitype: String? = ""

    @SerializedName("value")
    @Expose
    var value: String? = ""

    @SerializedName("key")
    @Expose
    var key: String? = ""

    @SerializedName("hint")
    @Expose
    var hint: String? = ""

    protected constructor(`in`: Parcel) {
        uitype = `in`.readValue(String::class.java.classLoader) as String
        value = `in`.readValue(String::class.java.classLoader) as String
        key = `in`.readValue(String::class.java.classLoader) as String
        hint = `in`.readValue(String::class.java.classLoader) as String
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(uitype)
        dest.writeValue(value)
        dest.writeValue(key)
        dest.writeValue(hint)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Uidatum> {
        override fun createFromParcel(parcel: Parcel): Uidatum {
            return Uidatum(parcel)
        }

        override fun newArray(size: Int): Array<Uidatum?> {
            return arrayOfNulls(size)
        }
    }
}