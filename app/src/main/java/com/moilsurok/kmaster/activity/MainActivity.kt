package com.moilsurok.kmaster.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.adapter.MainNoticeAdapter
import com.moilsurok.kmaster.adapter.ViewPagerAdapter
import com.moilsurok.kmaster.dataClass.EventDataClass
import com.moilsurok.kmaster.dataClass.InquiryDataClass
import com.moilsurok.kmaster.dataClass.NoticeDataClass
import com.moilsurok.kmaster.dataClass.SectorDataClass
import com.moilsurok.kmaster.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMainBinding

    private var doubleBackToExit = false

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var mainNoticeRecyclerView: RecyclerView
    var currentPosition = 0

    //핸들러 설정
    //ui 변경하기
    val handler = Handler(Looper.getMainLooper()) {
//        setPage()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMainBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val bookMark = intent.getStringExtra("bookMark")
        val field = intent.getStringExtra("field")
        val occupation = intent.getStringExtra("occupation")
        val num = intent.getStringExtra("num")
        val id = intent.getStringExtra("id")
//        Log.d("test", name.toString())
//        Log.d("MainActivity", phoneNum.toString())
//        Log.d("MainActivity", occupation.toString())
//        Log.d("MainActivity", field.toString())


        // 접근 가능
//        binding.goAlarm.setOnClickListener {
//            val intent = Intent(this, AlarmActivity::class.java)
//            startActivity(intent)
//        }
        binding.goExtra.setOnClickListener {
            val intent = Intent(this, ExtraActivity::class.java)
            intent.putExtra("company", company)
            intent.putExtra("name", name)
            intent.putExtra("year", year)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("email", email)
            intent.putExtra("id", id)
            intent.putExtra("field", field)
            intent.putExtra("occupation", occupation)
            intent.putExtra("num", num)
            startActivity(intent)
        }
        binding.goNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("bookMark", bookMark)
            intent.putExtra("phoneNum", phoneNum)
            startActivity(intent)
        }
        binding.goNotice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
        binding.goNotice2.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
        var deEvent: ArrayList<EventDataClass> = arrayListOf()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = today.toString().format(formatter)
        val formatted2 = today.toString().format(formatter2)

        binding.goDate.setOnClickListener {
            val intent = Intent(this, DateActivity::class.java)
            startActivity(intent)




        }
//        binding.goExplain.setOnClickListener {
//            val intent = Intent(this, ExplainGroupActivity::class.java)
//            startActivity(intent)
//        }
//        binding.goHome.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moilsurok.shop/"))
//            startActivity(intent)
//        }
        binding.goLabor.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.moel.go.kr/index.do")
            )
            startActivity(intent)
        }
        binding.goHomePage.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://kmasterhand.or.kr/")
            )
            startActivity(intent)

        }




        binding.todayText.text =
            formatted2.substring(5, 7) + "월 " + formatted2.substring(8, 10) + "일"
        val first =
            db

                .collection("Schedule")
                .whereGreaterThanOrEqualTo("date", formatted)
                .whereLessThanOrEqualTo("date", formatted + "\uF7FF")
                .orderBy("date", Query.Direction.DESCENDING).limit(1)


        // firebase data 불러오기


        first

            .addSnapshotListener { querySnapshot, _ ->
                // ArrayList 비워줌


                for (snapshot in querySnapshot!!.documents) {

                    val date = snapshot.getString("date").toString()
                    val title = snapshot.getString("title").toString()
                    val content = snapshot.getString("content").toString()
                    binding.todayDateTitle.text = title

                    binding.goToday.setOnClickListener {
                        val intent = Intent(this, ScheduleDetailActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("date", date.format("yyyy-MM-dd-hh-mm"))
                        intent.putExtra("content", content)
                        startActivity(intent)
                    }
                }


            }
//        binding.maYear.text = year
//        binding.maName.text = name
        mainNoticeRecyclerView = binding.mainNoticeRecyclerView
        var noticeList = arrayListOf<NoticeDataClass>()
        db
            .collection("Notice").limit(3).orderBy("pubDate", Query.Direction.DESCENDING)
            .addSnapshotListener { result, _ ->
                noticeList.clear()
                for (document in result!!) {

                    val item = document.toObject(NoticeDataClass::class.java)

                    noticeList.add(item)


                }

                val mainNoticeAdapter = MainNoticeAdapter(this, noticeList)
                mainNoticeRecyclerView.adapter = mainNoticeAdapter
                mainNoticeRecyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }


        val firstYear =
            db.collection("User").whereNotEqualTo("year", "사무국")
                .orderBy("year", Query.Direction.ASCENDING).limit(1)
        val endYear =
            db.collection("User").whereNotEqualTo("year", "사무국")
                .orderBy("year", Query.Direction.DESCENDING).limit(1)


        firstYear.get().addOnSuccessListener { result ->

            for (document in result) {
                val firstYear1 = document.get("year").toString()
                Log.d("firstYear", firstYear1)
                MySharedPreferences.setFirstYear(this, firstYear1)
            }
        }

        endYear.get().addOnSuccessListener { result ->

            for (document in result) {
                val endYear1 = document.get("year").toString()
                Log.d("endYear", endYear1)
                MySharedPreferences.setEndYear(this, endYear1)
            }
        }

//        binding.banner.adapter = ViewPagerAdapter(getIdolList(),this) // 어댑터 생성
//        binding.banner.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
//

        val thread = Thread(PagerRunnable())
        thread.start()
//        binding.maCompany.text = company
//        binding.banner.adapter = ViewPagerAdapter(getIdolList()) // 어댑터 생성
//        binding.banner.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
    }

    //페이지 변경하기
//    fun setPage(){
//        if(currentPosition==getIdolList().size) currentPosition=0
//        binding.banner.setCurrentItem(currentPosition,true)
//        currentPosition+=1
//    }

    //2초 마다 페이지 넘기기
    inner class PagerRunnable : Runnable {
        override fun run() {
            while (true) {
                Thread.sleep(2000)
                handler.sendEmptyMessage(0)
            }
        }
    }

    // 뷰 페이저에 들어갈 아이템
    private fun getIdolList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.ic_pmo_logo, R.drawable.ic_moil_logo)
    }

    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false
            }
        }
    }


    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}