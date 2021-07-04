package com.technodynamic.technodynamiclanding

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.technodynamic.technodynamiclanding.databinding.ActivityRegistrationBinding
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer


class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var Savecurrentdate: String = ""
    private var Savecurrenttime: String = ""
    private var checkedvalue: Boolean = false

    val nameregex = "[a-zA-Z][a-zA-Z ]*".toRegex()
    val phoneregex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$".toRegex()
    val pincoderegex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$".toRegex()
    val addrregex = "^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)

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

        binding.registerBuyer.setOnClickListener {


            registerbuyer()
        }


    }

    private fun registerbuyer() {


        val name=binding.buyername.text.toString()
        val contactnumber=binding.contactnumber.text.toString()
        val typeoforg=binding.typeoforg.text.toString()
        val pincode=binding.buyerpincide.text.toString()


        when {

            TextUtils.isEmpty(name) -> Toast.makeText(
                    this,
                    "Your Name is Empty",
                    Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(contactnumber) -> Toast.makeText(
                    this,
                    "Your Phone is Empty",
                    Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(pincode) -> Toast.makeText(
                    this,
                    "Your Pincode is Empty",
                    Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(typeoforg) -> Toast.makeText(
                    this,
                    "Your Organization Name is Empty",
                    Toast.LENGTH_SHORT
            ).show()

            (!name.matches(nameregex))-> {Toast.makeText(this, "WARNING: Enter a valid name !", Toast.LENGTH_SHORT).show() }

            (!typeoforg.matches(nameregex))->{Toast.makeText(this, "WARNING: Enter a valid Type of Business!", Toast.LENGTH_SHORT).show() }

            (!contactnumber.matches(phoneregex))->{Toast.makeText(this, "WARNING: Enter a valid Number !", Toast.LENGTH_SHORT).show() }

            (!pincode.matches(pincoderegex))->{Toast.makeText(this, "WARNING: Enter Valid Pin Code !", Toast.LENGTH_SHORT).show() }

            else -> {

                val sellerdialog = ProgressDialog(this)
                sellerdialog.setTitle("Updating User Account")
                sellerdialog.setMessage("Please wait,while we are updating our database..")
                sellerdialog.setCanceledOnTouchOutside(false)
                sellerdialog.show()


                var calendar : Calendar = Calendar.getInstance()
                var currentdate= SimpleDateFormat("MMM dd,yyyy")
                Savecurrentdate = currentdate.format(calendar.time)

                var currenttime= SimpleDateFormat("HH:mm:ss a")
                Savecurrenttime = currenttime.format(calendar.time)


                val sid=Savecurrentdate+Savecurrenttime

                val rootref = FirebaseDatabase.getInstance().reference.child("Interested")

                val sellermap = java.util.HashMap<String, Any>()

                sellermap.put("contact", contactnumber)
                sellermap.put("userid", sid)
                sellermap.put("pincode", pincode)
                sellermap.put("name", name)
                sellermap.put("tyoforg", typeoforg)
                rootref.child(sid).updateChildren(sellermap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                    applicationContext,
                                    "Thanks for the Registration",
                                    Toast.LENGTH_SHORT
                            ).show()
                            sellerdialog.dismiss()
                            Prevalent.name=name
                            Prevalent.contact=contactnumber
                            Prevalent.tyoforg=typeoforg
                            Prevalent.pincode=pincode
                            Prevalent.uid=sid
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            task.exception?.let { regisexception ->
                                throw regisexception

                            }
                        }
                    }


            }
        }
    }

    private fun userinfovalid(name: String, contactnumber: String, pincode: String, typeoforg: String): Boolean {

        /*clint side validation checking*/


        /*clint side validation checking*/

        if (!name.matches(nameregex))
        {
            Toast.makeText(this, "WARNING: Enter a valid name !", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!typeoforg.matches(nameregex))
        {
            Toast.makeText(this, "WARNING: Enter a valid Type of Business!", Toast.LENGTH_SHORT).show()
            return false
        }


        if (!contactnumber.matches(phoneregex))
        {
            Toast.makeText(this, "WARNING: Enter a valid Number !", Toast.LENGTH_SHORT).show()
            return false

        }

        if (!pincode.matches(pincoderegex))
        {
            Toast.makeText(this, "WARNING: Enter Valid Pin Code !", Toast.LENGTH_SHORT).show()
            return false
        }

//        if (full_address.length() == 0) {
//            Toast.makeText(this@EnquiryForm, "WARNING: Enter Valid Address !", Toast.LENGTH_SHORT)
//                .show()
//            if (!full_address.getText().toString()
//                    .matches("^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$")
//            ) {
//                Toast.makeText(
//                    this@EnquiryForm,
//                    "WARNING: Enter Valid Address !",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return
//            }
//        }

        /*clint side validation checking*/
            return true
    }



}
