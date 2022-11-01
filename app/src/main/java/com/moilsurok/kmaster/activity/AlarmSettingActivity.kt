package com.moilsurok.kmaster.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moilsurok.kmaster.databinding.ActivityAlarmSettingBinding

class AlarmSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAlarmSettingBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }

    }
}