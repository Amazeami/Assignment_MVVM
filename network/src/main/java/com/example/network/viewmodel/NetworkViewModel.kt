package com.example.network.viewmodel


import android.R
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.network.model.User
import com.example.network.network.BackEndApi
import com.example.network.network.WebServiceClient
import com.example.network.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkViewModel(application: Application) : AndroidViewModel(application), Callback<User> {

    private val context = application.applicationContext
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var fetchCustomUI: MutableLiveData<User>? = null
    var fetchImage: MutableLiveData<String>? = null
    private var progressBar: ProgressBar? = null

    init {
        progressDialog = SingleLiveEvent<Boolean>()
        fetchCustomUI = MutableLiveData<User>()
        fetchImage = MutableLiveData<String>()
    }

    fun fetchData() {
        progressDialog?.value = true

        if (isInternetAvailable()) {

            WebServiceClient.client.create(BackEndApi::class.java).getUsetData()
                    .enqueue(this)
        }else {
            progressDialog?.value = false
            hideProgressBar()
        }
    }

    override fun onResponse(call: Call<User>?, response: Response<User>?) {

        progressDialog?.value = false
        progressDialog?.value = false
        fetchCustomUI?.value = response?.body()
        fetchImage?.value = response?.body()?.logoUrl
    }

    override fun onFailure(call: Call<User>?, t: Throwable?) {
        progressDialog?.value = false
    }

    fun isInternetAvailable(): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                true
            } else {
                showErrorToast("Internet not available. Please try again!!")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun showErrorToast(message: String?) {

        try {
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)

            // set message color
            val textView = toast.view.findViewById(R.id.message) as? TextView
            textView?.setTextColor(Color.WHITE)
            textView?.gravity = Gravity.CENTER

            // set background color
            toast.view.setBackgroundColor(Color.RED)

            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)

            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // show progressbar
    fun showProgressBar() {
        try {
            val layout = (this as? Activity)?.findViewById<View>(R.id.content)?.rootView as? ViewGroup

            progressBar = ProgressBar(context, null, R.attr.progressBarStyleLarge)
            progressBar?.let { it1 ->
                it1.isIndeterminate = true

                val params = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val rl = RelativeLayout(context)

                rl.gravity = Gravity.CENTER
                rl.addView(it1)

                layout?.addView(rl, params)

                it1.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // hide progressbar
    fun hideProgressBar() {
        try {
            progressBar?.let {
                it.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}