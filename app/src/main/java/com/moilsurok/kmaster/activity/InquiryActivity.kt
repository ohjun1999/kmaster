package com.moilsurok.kmaster.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moilsurok.kmaster.InquiryBox
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.databinding.ActivityInquiryBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.adapter.InquiryAdapter
import com.moilsurok.kmaster.dataClass.InquiryDataClass
import com.moilsurok.kmaster.fragment.InquiryFragment
import com.moilsurok.kmaster.fragment.NonInquiryFragment


class InquiryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    lateinit var inquiryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        firestore = FirebaseFirestore.getInstance()
        val handler = Handler()
        val theTitle = intent.getStringExtra("title")
        val thePubDate = intent.getStringExtra("pubDate")
        val theUid = MySharedPreferences.getUserUid(this)
        val theContent = MySharedPreferences.getUserUid(this)

        var InquiryList = arrayListOf<InquiryDataClass>()
        db.collection("Question").whereEqualTo("uid", id.toString()).limit(20)
            .addSnapshotListener { result, _ ->
                InquiryList.clear()


                if (result!!.isEmpty) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.inquiryFrame, NonInquiryFragment())
                        .commitAllowingStateLoss()

                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.inquiryFrame, InquiryFragment())
                        .commitAllowingStateLoss()
                }


            }


        binding.backKey.setOnClickListener {
            finish()
        }

        binding.goRealInquiry.setOnClickListener {
            val intent = Intent(this, InquiryTextActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }

    }

}