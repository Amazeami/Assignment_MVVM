package mvvm.amit.com.samplelogin.ui.login.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.network.model.Uidatum
import mvvm.amit.com.samplelogin.ui.login.utils.Utility.hideProgressBar
import mvvm.amit.com.samplelogin.ui.login.utils.Utility.isInternetAvailable
import mvvm.amit.com.samplelogin.ui.login.utils.Utility.showProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import mvvm.amit.com.samplelogin.R
import mvvm.amit.com.samplelogin.ui.login.adapters.UsersAdapter


class MainActivity : AppCompatActivity() {

    private var listUsers: MutableList<Uidatum> = mutableListOf<Uidatum>()
    private var adapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listUsers = mutableListOf()
        val bookDataBundle = intent.extras
        bookDataBundle?.let {

            val list = it.getParcelableArrayList<Uidatum>("user_list") as ArrayList<Uidatum>
            listUsers?.addAll(list)

        }

        setContentView(R.layout.activity_main)
        showProgressBar()
        recycler_main.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = UsersAdapter(
                this,
                listUsers
        )
        recycler_main.adapter = adapter

        if (isInternetAvailable()) {
            getUsersData()
        }

    }

    private fun getUsersData() {

        hideProgressBar()
        adapter?.notifyDataSetChanged()
    }

}
