package mvvm.amit.com.samplelogin.ui.login.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.network.model.Uidatum
import com.example.network.model.User
import com.example.network.viewmodel.NetworkViewModel
import mvvm.amit.com.samplelogin.ui.login.utils.Utility.isInternetAvailable
import kotlinx.android.synthetic.main.activity_login.*
import mvvm.amit.com.samplelogin.R
import mvvm.amit.com.samplelogin.databinding.ActivityLoginBinding
import java.util.*
import kotlin.concurrent.schedule


class LoginActivity : AppCompatActivity() {

    var binding: ActivityLoginBinding? = null
    var viewmodel: NetworkViewModel? = null
    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)
        binding?.viewmodel = viewmodel
        customeProgressDialog = CustomeProgressDialog(this)
        isInternetAvailable()
        initObservables()
    }

    private fun initObservables() {

        viewmodel?.progressDialog?.observe(this, androidx.lifecycle.Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })

        // Observe Image url
        viewmodel?.fetchCustomUI?.observe(this, androidx.lifecycle.Observer {
            Glide.with(this).load(it?.logoUrl).into(imageView3)
        })




        // Observe Json Data

        viewmodel?.fetchCustomUI?.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "welcome, ${it?.headingText}", Toast.LENGTH_LONG).show()

            Timer("LoadJson", false).schedule(1500) {

                loadData(it)
            }
        })

    }

    private fun loadData(user: User?) {

        val listRandom = user?.uidata as ArrayList<Uidatum>
        val intent = Intent(baseContext, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList("user_list", listRandom)
        intent.putExtras(bundle)
        startActivity(intent)

    }


}
