package com.moilsurok.kmaster.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.activity.NoteActivity
import com.moilsurok.kmaster.adapter.NoteYearAdapter
import com.moilsurok.kmaster.dataClass.UserYearDataClass
import com.moilsurok.kmaster.databinding.FragmentNoteYearBinding

class NoteYearFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentNoteYearBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var noteYearRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteYearBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        val theUid = MySharedPreferences.getUid(requireContext())
        noteYearRecyclerView = binding.noteYearRecyclerView
        val year = MySharedPreferences.getYear(requireContext())
        var userYearList = arrayListOf<UserYearDataClass>()

        db
            .collection("User").whereEqualTo("year", year)
            .orderBy("name", Query.Direction.ASCENDING).limit(15)
            .get().addOnSuccessListener { result ->
                userYearList.clear()
                for (document in result) {

                    val item = document.toObject(UserYearDataClass::class.java)

                    userYearList.add(item)


                }

                val noteYearAdapter = NoteYearAdapter(theUid, NoteActivity(), userYearList)
                noteYearRecyclerView.adapter = noteYearAdapter
                noteYearRecyclerView.layoutManager =
                    LinearLayoutManager(NoteActivity(), RecyclerView.VERTICAL, false)

                if (result.isEmpty) {


                } else {
                    var lastVisible = result.documents[result.size() - 1]

                    var next =
                        db
                            .collection("User").whereEqualTo("year", year)
                            .orderBy("name", Query.Direction.ASCENDING)
                            .startAfter(lastVisible)
                            .limit(15)

                    Log.d("test123", lastVisible.toString())
                    binding.noteYearRecyclerView.addOnScrollListener(object :
                        RecyclerView.OnScrollListener() {
                        override fun onScrolled(
                            recyclerView: RecyclerView,
                            dx: Int,
                            dy: Int
                        ) {
                            super.onScrolled(recyclerView, dx, dy)

                            val lastVisibleItemPosition =
                                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                            val itemTotalCount =
                                recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
                            // 스크롤이 끝에 도달했는지 확인
                            if (lastVisibleItemPosition == itemTotalCount && result.size() > 0) {
                                Log.d("cnt", "1")
                                next
                                    .get().addOnSuccessListener { result ->

                                        for (document in result) {
                                            var item =
                                                document.toObject(
                                                    UserYearDataClass::class.java
                                                )
                                            userYearList.add(item)
                                        }

                                        if (result.size() > 0) {
                                            lastVisible =
                                                result.documents[result.size() - 1]
                                            next =
                                                db
                                                    .collection("User").whereEqualTo("year", year)
                                                    .orderBy("name", Query.Direction.ASCENDING)
                                                    .startAfter(lastVisible)
                                                    .limit(15)

                                        }
                                        noteYearAdapter.notifyDataSetChanged()
                                    }
                            } else if (lastVisibleItemPosition == itemTotalCount && result.size() < 0) {
                                Toast.makeText(
                                    context,
                                    "더이상 불러올 데이터가 없습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })

                    context?.hideKeyboard(noteYearRecyclerView)

                }
            }


        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager



        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}