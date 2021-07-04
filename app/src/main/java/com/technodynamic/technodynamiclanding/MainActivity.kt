package com.technodynamic.technodynamiclanding

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.technodynamic.technodynamiclanding.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var storeprodref: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)

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

        storeprodref= FirebaseStorage.getInstance().reference.child("Picture/tdvisit.jpg")

        val localfile:File = File.createTempFile("tdvist","jpg")

        storeprodref!!.getFile(localfile).addOnSuccessListener {

          val  bitmap:Bitmap= BitmapFactory.decodeFile(localfile.absolutePath)
            binding.tdvisitcard.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "Error Occurred while Retrieving FIle", Toast.LENGTH_SHORT).show()
            Log.wtf("exception",it.message)

        }





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)


        }


        binding.ratechart.setOnClickListener {

            val intent = Intent(applicationContext, Termsanduse::class.java)
            startActivity(intent)

        }


        binding.cardStartup.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Startup")
            intent.putExtra("resId", R.drawable.startup)
            startActivity(intent)
        }
        binding.cardEducation.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Education")
            intent.putExtra("resId", R.drawable.education)

            startActivity(intent)
        }
        binding.cardBeautyParlour.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "BeautyParlour")
            intent.putExtra("resId", R.drawable.beautyparlour)

            startActivity(intent)
        }
        binding.cardDoctorsAppo.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "DoctorsAppo")
            intent.putExtra("resId", R.drawable.doctorappointment)

            startActivity(intent)
        }
        binding.cardEmergency.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Emergency")
            intent.putExtra("resId", R.drawable.emergency)

            startActivity(intent)
        }
        binding.cardHotel.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Hotel")
            intent.putExtra("resId", R.drawable.hotel)

            startActivity(intent)
        }
        binding.cardPathalogy.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Path0logy")
            intent.putExtra("resId", R.drawable.pathalogy)

            startActivity(intent)
        }

        binding.cardPharmacy.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Pharmacy")
            intent.putExtra("resId", R.drawable.pharmacy)

            startActivity(intent)
        }

        binding.cardResturant.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Restaurant")
            intent.putExtra("resId", R.drawable.restorent)

            startActivity(intent)
        }

        binding.cardSalon.setOnClickListener {
            val intent = Intent(applicationContext, QueryActivity::class.java)
            intent.putExtra("category", "Salon")
            intent.putExtra("resId", R.drawable.saloon)


            startActivity(intent)
        }



    }
}