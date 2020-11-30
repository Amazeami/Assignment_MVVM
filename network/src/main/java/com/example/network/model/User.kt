package com.example.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User : Parcelable {
    @SerializedName("logo-url")
    @Expose
    var logoUrl: String? = null

    @SerializedName("heading-text")
    @Expose
    var headingText: String? = null

    @SerializedName("uidata")
    @Expose
    var uidata: List<Uidatum?>? = null

    protected constructor(`in`: Parcel) {
        logoUrl = `in`.readValue(String::class.java.classLoader) as String
        headingText = `in`.readValue(String::class.java.classLoader) as String
        `in`.readList(uidata as List<*>, Uidatum::class.java.classLoader)
    }



    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(logoUrl)
        dest.writeValue(headingText)
        dest.writeList(uidata)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}