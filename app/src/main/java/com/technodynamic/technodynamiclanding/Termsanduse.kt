package com.technodynamic.technodynamiclanding

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.technodynamic.technodynamiclanding.databinding.ActivityMainBinding
import com.technodynamic.technodynamiclanding.databinding.ActivityTermsanduseBinding

class Termsanduse : AppCompatActivity() {

    private lateinit var binding: ActivityTermsanduseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTermsanduseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected->
            if (isConnected){
                binding.nointenet.visibility= View.GONE
                binding.linearLayout2.visibility= View.VISIBLE

            }else{
                binding.nointenet.visibility= View.VISIBLE
                binding.linearLayout2.visibility= View.GONE
            }

        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)


        }
        FirebaseDatabase.getInstance().reference.child("ConitionsNew").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.cond1.setText(snapshot.child("cond1").value.toString())
                binding.cond2.setText(snapshot.child("cond2").value.toString())
                binding.cond3.setText(snapshot.child("cond3").value.toString())
                binding.cond4.setText(snapshot.child("cond4").value.toString())
                binding.cond5.setText(snapshot.child("cond5").value.toString())
//                binding.cond6.setText(snapshot.child("cond6").toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}