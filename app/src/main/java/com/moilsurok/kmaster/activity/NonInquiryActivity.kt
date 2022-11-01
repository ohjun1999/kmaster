package com.moilsurok.kmaster.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moilsurok.kmaster.databinding.ActivityNonInquiryBinding

class NonInquiryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNonInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNonInquiryBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        binding.backKey.setOnClickListener {
            finish()
        }

        binding.goRealInquiry.setOnClickListener {
            val intent = Intent(this, InquiryTextActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }
}