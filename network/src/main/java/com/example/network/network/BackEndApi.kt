package com.example.network.network


import com.example.network.model.User
import retrofit2.Call
import retrofit2.http.POST

interface BackEndApi {

    @POST("mobileapps/android_assignment.json")
    fun getUsetData(): Call<User>

}

