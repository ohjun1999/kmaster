package com.moilsurok.kmaster.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.activity.NoteActivity
import com.moilsurok.kmaster.activity.NoteProfileDetailActivity
import com.moilsurok.kmaster.adapter.BookMarkAdapter
import com.moilsurok.kmaster.dataClass.UserDataClass
import com.moilsurok.kmaster.databinding.FragmentBookmarkBinding
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.collections.ArrayList

class BookmarkFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var bookmarkRecyclerView: RecyclerView

    val first =
        firestore
            ?.collection("User")?.whereArrayContainsAny("bookmark", listOf("010-2281-4489"))
            ?.limit(20)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        val TAG = "과연"
        val theUid = MySharedPreferences.getUid(requireContext())
        bookmarkRecyclerView = binding.bookmarkRecyclerView

        var UserList = arrayListOf<UserDataClass>()

        db
            .collection("User").whereEqualTo("uid", theUid)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    var bookMark: ArrayList<String> = document.get("bookmark") as ArrayList<String>

                    for (i in bookMark){
                        Log.d("test", i)
                        db
                            .collection("User").whereEqualTo("uid", i)
                            .get().addOnSuccessListener { results ->
                                for (document in results) {

                                    val item = document.toObject(UserDataClass::class.java)
                                    UserList.add(item)

                                }
                                val bookmarkAdapter = BookMarkAdapter(theUid, NoteActivity(), UserList)
                                bookmarkRecyclerView.adapter = bookmarkAdapter
                                bookmarkRecyclerView.layoutManager =
                                    LinearLayoutManager(NoteActivity(), RecyclerView.VERTICAL, false)
                            }

                    }


                }
            }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}