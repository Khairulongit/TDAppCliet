package com.technodynamic.technodynamiclanding

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.technodynamic.technodynamiclanding.databinding.ActivityLoginBinding
import com.technodynamic.technodynamiclanding.databinding.ActivityMainBinding
import io.paperdb.Paper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.nointenet.visibility= View.GONE
                binding.connected.visibility= View.VISIBLE

            }else{
                binding.nointenet.visibility= View.VISIBLE
                binding.connected.visibility= View.GONE
            }

        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.login, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.login)


        }

        if (Prevalent.name==""){
        Paper.init(this)}


        Log.wtf("prevaled",Prevalent.name)
        Log.wtf("prevaled",Prevalent.toString())


        binding.btnAnnonymous.setOnClickListener {

            if (Prevalent.name!=""){
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            else{

                val intent = Intent(this, RegistrationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }

    }
}