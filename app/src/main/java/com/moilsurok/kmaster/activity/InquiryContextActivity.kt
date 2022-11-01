package com.moilsurok.kmaster.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.dataClass.AnswerDataClass
import com.moilsurok.kmaster.databinding.ActivityInquiryContextBinding

class InquiryContextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryContextBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryContextBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val creator = intent.getStringExtra("creator")
        val pubDate = intent.getStringExtra("pubDate")
        val check = intent.getStringExtra("check")
        val uuid = intent.getStringExtra("uuid")
        val uid = intent.getStringExtra("uid")
        Log.d("test", uuid.toString())
        val theContent = MySharedPreferences.getUserUid(this)
        firestore = FirebaseFirestore.getInstance()
        var InquiryAnswerList = arrayListOf<AnswerDataClass>()
        db.collection("Answer").whereEqualTo("question", uuid.toString())
            .get().addOnSuccessListener { result ->

                if (result.isEmpty) {

                } else {
                    binding.answer.visibility = View.VISIBLE
                    for (document in result) {

                        val item = document.toObject(AnswerDataClass::class.java)


                        binding.contextAnswer.text = document.getString("content")
                        binding.contextPubDate.text = document.getString("pubDate")

                        MySharedPreferences.setQues(this, document.id)
                        InquiryAnswerList.add(item)


                    }
                }


            }



        binding.backKey.setOnClickListener {
            finish()
        }
        binding.deleteBtn.setOnClickListener {
            val idd = MySharedPreferences.getQues(this)
            Log.d("test", idd)
            if (binding.contextAnswer.text.trim().isEmpty()) {

            } else {
                db.collection("Answer").document(idd).delete().addOnSuccessListener {

                    }
            }

            db.collection("Question").document(uuid.toString()).delete().addOnSuccessListener {


                }

            db.collection("Counter").document("counter")
                .update("question", FieldValue.increment(-1))


            finish()

        }
        if (check.toString() == "X") {
            binding.inquiryEnd.visibility = View.GONE
            binding.inquiryIng.visibility = View.VISIBLE

        }
        binding.contextTitle.text = title.toString()
        binding.contextContent.text = content.toString()
        binding.contextDate.text = pubDate.toString()
    }
}