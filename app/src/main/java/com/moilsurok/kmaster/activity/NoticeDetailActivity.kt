package com.moilsurok.kmaster.activity


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.adapter.BookMarkAdapter
import com.moilsurok.kmaster.dataClass.NoticeDataClass
import com.moilsurok.kmaster.databinding.ActivityNoticeDetailBinding
import com.moilsurok.kmaster.fragment.NoteFragment

class NoticeDetailActivity : AppCompatActivity() {
    lateinit var noticeImg: RecyclerView
    private lateinit var binding: ActivityNoticeDetailBinding
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoticeDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }
        var deNotice: ArrayList<NoticeDataClass> = arrayListOf()
        noticeImg = binding.noticeImg
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val pubDate = intent.getStringExtra("pubDate")
        val files = intent.getStringArrayListExtra("files")
        val uid = intent.getStringExtra("uid")
        var noticeImgList = arrayListOf<String>()
        db.collection("Notice").whereEqualTo("uid", uid).limit(1)
            .get().addOnSuccessListener { result ->

                for (document in result) {
                    if (document.get("files") == null) {

                    } else {
                        noticeImgList = document.get("files") as ArrayList<String>
                    }


                }
                val realNoticeAdapter = RealNoticeAdapter(this, noticeImgList)
                noticeImg.adapter = realNoticeAdapter
                noticeImg.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                Log.d("test12", noticeImgList.toString())
            }

        val theImg = MySharedPreferences.getSearch(this)




        binding.title.text = title
        binding.modifiedDate.text = pubDate.toString()
        binding.noticeText.text = content
    }

    inner class RealNoticeAdapter(
        val context: Context,
        var noticeImgList: ArrayList<String>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_notice_img, parent, false)
            val olcYear = noticeImgList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as RealNoticeAdapter.ViewHolder).itemView
            val notice = noticeImgList[position]


            Glide.with(holder.itemView)
                .load(notice)
                .into(holder.noticeRealImg)


        }

        override fun getItemCount(): Int {
            return noticeImgList.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val noticeRealImg: ImageView = itemView.findViewById(R.id.noticeRealImg)


        }
    }
}