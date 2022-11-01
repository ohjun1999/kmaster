package com.moilsurok.kmaster.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.NoticeDetailActivity
import com.moilsurok.kmaster.dataClass.NoticeDataClass
import com.moilsurok.kmaster.dataClass.UserDataClass

class MainNoticeAdapter  (val context: Context, val NoticeList: ArrayList<NoticeDataClass>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    private val deNote: ArrayList<UserDataClass> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_notice, parent, false)
        val notice = NoticeList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as MainNoticeAdapter.ViewHolder).itemView
        val notice: NoticeDataClass = NoticeList[position]
        holder.date.text = notice.pubDate.toString().substring(8,10)
        holder.yearMonth.text = notice.pubDate.toString().substring(0,7)
        holder.title.text = notice.title.toString()
        holder.content.text = notice.content.toString()

        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, NoticeDetailActivity::class.java)
            intent.putExtra("title", notice.title)
            intent.putExtra("content", notice.content)
            intent.putExtra("creator", notice.creator)
            intent.putExtra("pubDate", notice.pubDate)
            intent.putExtra("uid", notice.uid)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }


    }

    override fun getItemCount(): Int {
        return NoticeList.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val date: TextView = itemView.findViewById(R.id.date)
        val yearMonth: TextView = itemView.findViewById(R.id.yearMonth)
        val title: TextView = itemView.findViewById(R.id.title)
        val content: TextView = itemView.findViewById(R.id.content)


    }
}