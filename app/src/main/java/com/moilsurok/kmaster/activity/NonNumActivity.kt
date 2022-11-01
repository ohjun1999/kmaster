package com.moilsurok.kmaster.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moilsurok.kmaster.databinding.ActivityNonNumBinding


class NonNumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNonNumBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNonNumBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)



        binding.goEnd.setOnClickListener {
            finishAffinity()
        }

    }

}