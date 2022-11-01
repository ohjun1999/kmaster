package com.moilsurok.kmaster.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences

import com.moilsurok.kmaster.databinding.ActivityProfileDetailBinding

class NoteProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        binding.backKey.setOnClickListener {
            finish()
        }
        binding.request.setOnClickListener {
            val input = binding.dePhoneNum.text.toString()
            val myUri = Uri.parse("tel:${input}")
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(myIntent)
        }

        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val comPosition = intent.getStringExtra("comPosition")
        val comAdr = intent.getStringExtra("comAdr")
        val faxNum = intent.getStringExtra("faxNum")
        val check = intent.getStringExtra("check")
        val files = intent.getStringExtra("files")
        val filenames = intent.getStringExtra("filenames")
        val iduser = intent.getStringExtra("iduser")
        val comTel = intent.getStringExtra("comTel")
        val uid = intent.getStringExtra("uid")
        val sector = intent.getStringExtra("sector")


        if (files.toString() == "null") {
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
                .into(binding.deImg)
        } else {
            Glide.with(this)
                .load(files.toString())
                .into(binding.deImg)
        }

        if (year == null) {
            binding.deYear.text = "미기입"
        } else {
            binding.deYear.text = year
        }
        if (name.toString() == "null") {
            binding.deName.text = "미기입"
        } else {
            binding.deName.text = name
        }
        if (phoneNum.toString() == "null") {
            binding.dePhoneNum.text = "미기입"
        } else {
            binding.dePhoneNum.text = phoneNum
        }
        if (email.toString() == "null") {
            binding.deEmail.text = "미기입"
        } else {
            binding.deEmail.text = email
        }
        if (company.toString() == "null") {
            binding.deCompany.text = "미기입"
        } else {
            binding.deCompany.text = company
        }
        if (comPosition.toString() == "null") {
            binding.deComPosition.text = "미기입"
        } else {
            binding.deComPosition.text = comPosition
        }
        if (comAdr.toString() == "null") {
            binding.deComAdr.text = "미기입"
        } else {
            binding.deComAdr.text = comAdr
        }
        if (faxNum.toString() == "null") {
            binding.deFaxNum.text = "미기입"
        } else {
            binding.deFaxNum.text = faxNum
        }
        if (comTel.toString() == "null") {
            binding.frComNum.text = "미기입"
        } else {
            binding.frComNum.text = comTel
        }
        if (sector.toString() == "null") {
            binding.frCom.text = "미기입"
        } else {
            binding.frCom.text = sector
        }
        if (check == "O") {

            binding.img2.visibility = View.VISIBLE
        } else {
            binding.img2.visibility = View.GONE
        }
        val theUid = MySharedPreferences.getUid(this)
        Log.d("test1", theUid)
        Log.d("test2", uid.toString())
        Log.d("test3", iduser.toString())
        binding.img1.setOnClickListener {

            db.collection("User").document(uid.toString())
                .update("bookmark", FieldValue.arrayUnion(iduser))
            binding.img2.visibility = View.VISIBLE


        }
        binding.img2.setOnClickListener {

            db.collection("User").document(uid.toString())
                .update("bookmark", FieldValue.arrayRemove(iduser))
            binding.img2.visibility = View.GONE



        }

    }


}