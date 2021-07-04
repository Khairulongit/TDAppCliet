package com.technodynamic.technodynamiclanding

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.database.FirebaseDatabase
import com.technodynamic.technodynamiclanding.databinding.ActivityQueryBinding

class QueryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQueryBinding
    val nameregex = "[a-zA-Z][a-zA-Z ]*".toRegex()
    val phoneregex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$".toRegex()
    val pincoderegex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$".toRegex()
    val addrregex = "^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$".toRegex()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityQueryBinding.inflate(layoutInflater)

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
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)


        }

        binding.shopname.setText(Prevalent.name)
        binding.sellercontact.setText(Prevalent.contact)


        val category= intent.getStringExtra("category").toString()
        val resId= intent.getIntExtra("resId",0)


        binding.mainImg.setImageResource(resId)
        binding.category.text = category

        binding.submitForm.setOnClickListener {
            submitquery(category)
        }
    }

    private fun submitquery(category: String) {

        val name=binding.shopname.text.toString()
        val contactnumber=binding.sellercontact.text.toString()
        val query=binding.query.text.toString()


        when {

            TextUtils.isEmpty(name) -> Toast.makeText(this, "Your Name is Empty", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(contactnumber) -> Toast.makeText(this, "Your Phone is Empty", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(query) -> Toast.makeText(this, "Your Query is Empty", Toast.LENGTH_SHORT).show()

            (!name.matches(nameregex))-> {Toast.makeText(this, "WARNING: Enter a valid name !", Toast.LENGTH_SHORT).show() }

            (!contactnumber.matches(phoneregex))->{Toast.makeText(this, "WARNING: Enter a valid Number !", Toast.LENGTH_SHORT).show() }

            (!query.matches(addrregex))->{Toast.makeText(this, "WARNING: Enter Valid Address!", Toast.LENGTH_SHORT).show() }

            else -> {

                val sellerdialog = ProgressDialog(this)
                sellerdialog.setTitle("Sending Query")
                sellerdialog.setMessage("Please wait,while we are updating the team..")
                sellerdialog.setCanceledOnTouchOutside(false)
                sellerdialog.show()

                var sid = Prevalent.uid
                val rootref = FirebaseDatabase.getInstance().reference.child("Interested")

                val sellermap = java.util.HashMap<String, Any>()

                var queryfor:String=category

                sellermap["contact"] = contactnumber
                sellermap["name"] = name
                sellermap[queryfor] = query
                rootref.child(sid).updateChildren(sellermap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "Your Query Has been Sent!!", Toast.LENGTH_SHORT).show()
                            sellerdialog.dismiss()

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
    }

