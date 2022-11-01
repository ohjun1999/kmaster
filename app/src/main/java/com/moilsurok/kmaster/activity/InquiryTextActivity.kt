package com.moilsurok.kmaster.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.moilsurok.kmaster.dataClass.InquiryDataClass

import com.moilsurok.kmaster.databinding.ActivityInquiryTextBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class InquiryTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryTextBinding
    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryTextBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        binding.constraintLayout.setOnClickListener {
            hideKeyboard()
        }

        binding.backKey.setOnClickListener {
            finish()
        }
        binding.goInquiryFire.setOnClickListener {

            var inquiryDataClass = InquiryDataClass()
            inquiryDataClass.uid = id.toString()
            inquiryDataClass.title = binding.inquiryTitle.text.toString()
            inquiryDataClass.content = binding.inquiryContent.text.toString()
            inquiryDataClass.pubDate = nowDate.toString()
            inquiryDataClass.creator = name.toString()
            inquiryDataClass.modifiedDate = nowDate.toString()
            inquiryDataClass.check = "X"


            db.collection("Counter").document("counter")
                .update("question", FieldValue.increment(1))
            db.collection("Question")
                .add(inquiryDataClass)
                .addOnSuccessListener { documentReference ->

                    val doId = documentReference.id
                    db.collection("Question")
                        .document(doId).update("uuid", doId)
                    MySharedPreferences.setUserUid(this, doId)
                }
                .addOnFailureListener { e ->

                }
            hideKeyboard()
            Toast.makeText(this, "문의가 접수 되었습니다", Toast.LENGTH_SHORT).show()

            finish()
        }


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.inquiryContent.windowToken, 0)
    }
}
