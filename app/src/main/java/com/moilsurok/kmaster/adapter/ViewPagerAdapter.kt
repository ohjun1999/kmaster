package com.moilsurok.kmaster.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.moilsurok.kmaster.R

class ViewPagerAdapter(idolList: ArrayList<Int>,
                       val context: Context
) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var item = idolList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.idol.setImageResource(item[position])
        holder.itemView.setOnClickListener {
            when (item[position]) {
                2131231040 -> {
                    Log.d("test1",item[position].toString())
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moilsurok.shop/"))
                    context.startActivity(intent)
                }
                2131231041 ->{
                    Log.d("test2",item[position].toString())
                }
            }
        }
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.idol_list_item, parent, false)) {

        val idol = itemView.findViewById<ImageView>(R.id.imageView_idol)
    }
}