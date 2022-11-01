package com.moilsurok.kmaster.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.fragment.NoteFragment

class OlcSectorAdapter (
    val context: Context,
    val olcSectorList: ArrayList<String>,

    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_sector, parent, false)
        val olcYear = olcSectorList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as OlcSectorAdapter.ViewHolder).itemView
        val olcSector = olcSectorList[position]
        holder.olcSector.text = olcSector

        Log.d("test", olcSector.toString())

        holder.itemView.setOnClickListener {

//
//            if (binding.btn1.isChecked) {
//                val noteFragmentSearch: NoteFragment =
//                    supportFragmentManager.findFragmentById(R.id.noteFrame) as NoteFragment
//                noteFragmentSearch.doSector(olcSector)
//                binding.layout.closeDrawer((GravityCompat.END))
//            } else {
//                hoo()
//            }
            Toast.makeText(context, olcSector.toString(), Toast.LENGTH_SHORT).show()

        }


    }

    override fun getItemCount(): Int {
        return olcSectorList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val olcSector: TextView = itemView.findViewById(R.id.olcSector)


    }
}