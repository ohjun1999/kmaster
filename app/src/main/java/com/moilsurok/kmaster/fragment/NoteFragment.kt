package com.moilsurok.kmaster.fragment


import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.activity.NoteActivity
import com.moilsurok.kmaster.adapter.NoteAdapter
import com.moilsurok.kmaster.adapter.NoteSearchAdapter
import com.moilsurok.kmaster.adapter.SearchAdapter
import com.moilsurok.kmaster.dataClass.SearchUserDataClass
import com.moilsurok.kmaster.dataClass.UserDataClass
import com.moilsurok.kmaster.dataClass.UserSearchDataClass
import com.moilsurok.kmaster.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val deNote: ArrayList<UserDataClass> = arrayListOf()
    //firestore 변수

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var noteRecyclerView: RecyclerView
    val first =
        firestore
            ?.collection("User")?.orderBy("year", Query.Direction.ASCENDING)
            ?.orderBy("name", Query.Direction.ASCENDING)?.limit(7)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root
//firestore 변수 초기화
        db = Firebase.firestore
        auth = Firebase.auth

        val theUid = MySharedPreferences.getUid(requireContext())



        noteRecyclerView = binding.noteRecyclerView

        var UserList = arrayListOf<UserDataClass>()

        var searchList = arrayListOf<UserSearchDataClass>()
        getNote()
        // 검색 옵션 변수
        var searchOption = "name"
        // 스피너 옵션에 따른 동작
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (binding.spinner.getItemAtPosition(position)) {
                    "이름" -> {
                        searchOption = "name"
                    }
                    "회사" -> {
                        searchOption = "company"
                    }
                }
            }
        }


        binding.searchWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
                binding.deleteBtn.isEnabled = true // 버튼 활성화
                binding.deleteBtn.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
                binding.deleteBtn.visibility = View.GONE
                binding.deleteBtn.isEnabled = false // 버튼 비활성화
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                if (binding.searchWord.length() < 1) { // 패스워드의 길이가 1미만이면

                    binding.deleteBtn.visibility = View.GONE
                    binding.deleteBtn.isEnabled = false // 버튼 비활성화
                } else {
                    binding.deleteBtn.isEnabled = true // 버튼 활성화
                    binding.deleteBtn.visibility = View.VISIBLE
                }
            }
        })

        binding.deleteBtn.setOnClickListener {
//            refreshFragment(this, parentFragmentManager)
            getNote()
            context?.hideKeyboard(noteRecyclerView)
            binding.searchWord.text = null
            binding.deleteBtn.visibility = View.GONE
            binding.searchWord.clearFocus()
            binding.deleteBtn.isEnabled = false // 버튼 비활성화
        }



        binding.searchWord.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                IME_ACTION_SEARCH -> {

                    noteRecyclerView.clearOnScrollListeners()
                    val searchWord = binding.searchWord.text.toString()
                    Log.d("test", searchWord)
                    val searchDB =
                        db.collection("User").whereGreaterThanOrEqualTo(searchOption, searchWord)
                            .whereLessThanOrEqualTo(searchOption, searchWord + "\uF7FF")
                    if (searchWord.isEmpty()) {
                        Toast.makeText(context, "한글자 이상 입력해주시길 바랍니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        searchDB.limit(7)
                            .get().addOnSuccessListener { result ->
                                searchList.clear()
                                if (result.isEmpty) {
                                    Toast.makeText(context, "등록되지 않은 이름입니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    context?.hideKeyboard(noteRecyclerView)
                                } else {
                                    for (document in result) {
                                        var item =
                                            document.toObject(UserSearchDataClass::class.java)
                                        searchList.add(item)

                                    }
                                }
                                Log.d("test12", searchList.toString())
                                //Adapter
                                val noteSearchAdapter =
                                    NoteSearchAdapter(theUid, NoteActivity(), searchList)
                                noteRecyclerView.adapter = noteSearchAdapter
                                noteRecyclerView.layoutManager =
                                    LinearLayoutManager(
                                        NoteActivity(),
                                        RecyclerView.VERTICAL,
                                        false
                                    )

                                if (result.isEmpty) {


                                } else {
                                    var lastVisible = result.documents[result.size() - 1]

                                    var next =
                                        searchDB
                                            .startAfter(lastVisible)
                                            .limit(7)
                                    Log.d("test123", lastVisible.toString())

                                    binding.noteRecyclerView.addOnScrollListener(object :
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
                                                                    UserSearchDataClass::class.java
                                                                )
                                                            searchList.add(item)
                                                        }

                                                        if (result.size() > 0) {
                                                            lastVisible =
                                                                result.documents[result.size() - 1]
                                                            next =
                                                                searchDB
                                                                    .startAfter(lastVisible)
                                                                    .limit(7)

                                                        }
                                                        noteSearchAdapter.notifyDataSetChanged()
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

                                    context?.hideKeyboard(noteRecyclerView)

                                }
                            }

                    }

                }
            }
            true
        })
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        return root
    }

    fun doSomething(year: String) {
        val theUid = MySharedPreferences.getUid(requireContext())
        var UserList = arrayListOf<UserDataClass>()
        db
            .collection("User").whereEqualTo("year", year)
            .orderBy("name", Query.Direction.ASCENDING).limit(7)
            .get().addOnSuccessListener { result ->
                UserList.clear()
                for (document in result!!.documents) {
                    var item = document.toObject(UserDataClass::class.java)
                    UserList.add(item!!)


                }
//Adapter
                val noteAdapter =
                    NoteAdapter(theUid, NoteActivity(), UserList)
                noteRecyclerView.adapter = noteAdapter
                noteRecyclerView.layoutManager =
                    LinearLayoutManager(NoteActivity(), RecyclerView.VERTICAL, false)

                var lastVisible = result.documents[result.size() - 1]
                var next =
                    db.collection("User").whereEqualTo("year", year)
                        .orderBy("name", Query.Direction.ASCENDING)
                        .startAfter(lastVisible)
                        .limit(7)

                binding.noteRecyclerView.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                        val itemTotalCount =
                            recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                        // 스크롤이 끝에 도달했는지 확인
                        if (lastVisibleItemPosition == itemTotalCount && result.size() > 0) {

                            next
                                .get().addOnSuccessListener { result ->

                                    // ArrayList 비워줌
                                    for (document in result) {
                                        val item =
                                            document.toObject(UserDataClass::class.java)
                                        UserList.add(item)


                                    }
                                    if (result.size() > 0) {
                                        lastVisible =
                                            result.documents[result.size() - 1]

                                        next =
                                            db.collection("User").whereEqualTo("year", year)
                                                .orderBy("name", Query.Direction.ASCENDING)
                                                .startAfter(lastVisible)
                                                .limit(7)
                                    }
                                    noteAdapter.notifyDataSetChanged()
                                }

                        } else if (lastVisibleItemPosition == itemTotalCount && result.size() < 0) {
                            lastVisible =
                                result.documents[result.size() - 1]
                            Toast.makeText(
                                context,
                                "더이상 불러올 데이터가 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })


            }
    }

    fun doSector(sector: String) {
        val theUid = MySharedPreferences.getUid(requireContext())
        var UserList = arrayListOf<UserDataClass>()
        db
            .collection("User").whereEqualTo("sector", sector)
            .orderBy("year", Query.Direction.ASCENDING)
            .orderBy("name", Query.Direction.ASCENDING).limit(7)
            .get().addOnSuccessListener { result ->
                UserList.clear()
                for (document in result!!.documents) {
                    var item = document.toObject(UserDataClass::class.java)
                    UserList.add(item!!)


                }
                Log.d("test1234", "test1234")
//Adapter
                val noteAdapter =
                    NoteAdapter(theUid, NoteActivity(), UserList)
                noteRecyclerView.adapter = noteAdapter
                noteRecyclerView.layoutManager =
                    LinearLayoutManager(NoteActivity(), RecyclerView.VERTICAL, false)

                var lastVisible = result.documents[result.size() - 1]
                var next =
                    db.collection("User").whereEqualTo("sector", sector)
                        .orderBy("year", Query.Direction.ASCENDING)
                        .orderBy("name", Query.Direction.ASCENDING)
                        .startAfter(lastVisible)
                        .limit(7)

                binding.noteRecyclerView.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                        val itemTotalCount =
                            recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                        // 스크롤이 끝에 도달했는지 확인
                        if (lastVisibleItemPosition == itemTotalCount && result.size() > 0) {

                            next
                                .get().addOnSuccessListener { result ->

                                    // ArrayList 비워줌
                                    for (document in result) {
                                        val item =
                                            document.toObject(UserDataClass::class.java)
                                        UserList.add(item)


                                    }
                                    if (result.size() > 0) {
                                        lastVisible =
                                            result.documents[result.size() - 1]

                                        next =
                                            db.collection("User").whereEqualTo("sector", sector)
                                                .orderBy("year", Query.Direction.ASCENDING)
                                                .orderBy("name", Query.Direction.ASCENDING)
                                                .startAfter(lastVisible)
                                                .limit(7)
                                    }
                                    noteAdapter.notifyDataSetChanged()
                                }

                        } else if (lastVisibleItemPosition == itemTotalCount && result.size() < 0) {
                            lastVisible =
                                result.documents[result.size() - 1]
                            Toast.makeText(
                                context,
                                "더이상 불러올 데이터가 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })


            }
    }

    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

    fun getNote() {
        val theUid = MySharedPreferences.getUid(requireContext())
        var UserList = arrayListOf<UserDataClass>()
        db
            .collection("User").orderBy("year", Query.Direction.ASCENDING)
            .orderBy("name", Query.Direction.ASCENDING).limit(7)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    var item = document.toObject(UserDataClass::class.java)


                    UserList.add(item)


                }


                //Adapter
                var noteAdapter = NoteAdapter(theUid, NoteActivity(), UserList)
                noteRecyclerView.adapter = noteAdapter
                noteRecyclerView.layoutManager =
                    LinearLayoutManager(NoteActivity(), RecyclerView.VERTICAL, false)


                var lastVisible = result.documents[result.size() - 1]
                var next =
                    db
                        .collection("User")
                        .orderBy("year", Query.Direction.ASCENDING)
                        .orderBy("name", Query.Direction.ASCENDING)
                        .startAfter(lastVisible)
                        .limit(7)

                binding.noteRecyclerView.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                        val itemTotalCount =
                            recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                        // 스크롤이 끝에 도달했는지 확인
                        if (lastVisibleItemPosition == itemTotalCount && result.size() > 0) {

                            next
                                .get().addOnSuccessListener { result ->

                                    // ArrayList 비워줌
                                    for (document in result) {
                                        val item =
                                            document.toObject(UserDataClass::class.java)
                                        UserList.add(item)


                                    }
                                    lastVisible =
                                        result.documents[result.size() - 1]
                                    next = db
                                        .collection("User")
                                        .orderBy("year", Query.Direction.ASCENDING)
                                        .orderBy("name", Query.Direction.ASCENDING)
                                        .startAfter(lastVisible)
                                        .limit(7)
                                    noteAdapter.notifyDataSetChanged()
                                }

                        } else if (lastVisibleItemPosition == itemTotalCount && result.size() < 0) {
                            lastVisible =
                                result.documents[result.size() - 1]
                            Toast.makeText(
                                context,
                                "더이상 불러올 데이터가 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })


            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun btn01() {
        binding.search.visibility = View.VISIBLE

    }

    fun btn02() {
        binding.search.visibility = View.GONE
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}