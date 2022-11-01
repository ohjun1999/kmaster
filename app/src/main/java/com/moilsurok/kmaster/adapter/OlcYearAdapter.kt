package com.moilsurok.kmaster.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.NoteActivity
import com.moilsurok.kmaster.activity.NoteProfileDetailActivity
import com.moilsurok.kmaster.dataClass.OlcNoteYearDataClass
import com.moilsurok.kmaster.fragment.NoteFragment


class OlcYearAdapter(
    val context: Context,
    val olcYearList: ArrayList<Int>,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_year, parent, false)
        val olcYear = olcYearList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as OlcYearAdapter.ViewHolder).itemView
        val olcYear = olcYearList[position]
        holder.olcYear.text = "OLC " + olcYear.toString() + "기"

        Log.d("test", olcYear.toString())

        holder.itemView.setOnClickListener {
//
//                val noteFragmentSearch: NoteFragment =
//                    supportFragmentManager.findFragmentById(R.id.noteFrame) as NoteFragment
//                noteFragmentSearch.doSomething(olcYear)
//                layout.closeDrawer((GravityCompat.END))

            Toast.makeText(context, olcYear.toString(), Toast.LENGTH_SHORT).show()

        }


    }

    override fun getItemCount(): Int {
        return olcYearList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val olcYear: TextView = itemView.findViewById(R.id.olcYear)


    }
}