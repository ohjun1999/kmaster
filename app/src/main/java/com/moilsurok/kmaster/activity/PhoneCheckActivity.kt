package com.moilsurok.kmaster.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.databinding.ActivityPhoneCheckBinding

class PhoneCheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneCheckBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityPhoneCheckBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }
        val theUid = MySharedPreferences.getUid(this)
        binding.hideBtn.setOnClickListener {
            db
                .collection("User").document(theUid).update("phoneNumCheck", "O")
            finish()
            Log.d("test2", theUid)
        }





    }
}