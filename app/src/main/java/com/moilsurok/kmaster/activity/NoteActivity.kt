package com.moilsurok.kmaster.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.moilsurok.kmaster.R
import com.moilsurok.kmaster.dataClass.UserDataClass
import com.moilsurok.kmaster.databinding.ActivityNoteBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.moilsurok.kmaster.MySharedPreferences
import com.moilsurok.kmaster.dataClass.checkboxData
import com.moilsurok.kmaster.fragment.NoteFragment
import com.moilsurok.kmaster.fragment.NoteYearFragment
import com.moilsurok.kmaster.setOnSingleClickListener


class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
//    private lateinit var noteAdapter: NoteAdapter

    var firestore: FirebaseFirestore? = null
    var isCheck = BooleanArray(50)
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private var noteFragment: NoteFragment? = null
    private var noteYearFragment: NoteYearFragment? = null
    lateinit var olcYearRecyclerView: RecyclerView
    lateinit var olcSectorRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoteBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)


        // bottomSheetDialog.setContentView(R.layout.bottom_sheet) 이렇게 사용 가능
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        val id = intent.getStringExtra("id")
        val phoneNum = intent.getStringExtra("phoneNum")

//noteFragment = NoteFragment()
        db = Firebase.firestore
        auth = Firebase.auth

        binding.btn1.isChecked = true

        olcYearRecyclerView = binding.olcYearRecyclerView
        olcSectorRecyclerView = binding.olcSectorRecyclerView
        binding.backKey.setOnClickListener {
            finish()
        }
        binding.goFavor.setOnClickListener {
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.noteFrame, NoteFragment())
            .commit()

        binding.btn1.setOnSingleClickListener {


            supportFragmentManager.beginTransaction()
                .replace(R.id.noteFrame, NoteFragment())
                .commit()
        }

        binding.btn2.setOnSingleClickListener {

            supportFragmentManager.beginTransaction()
                .replace(R.id.noteFrame, NoteYearFragment())
                .commit()
        }
//      \
        binding.getYear.setOnClickListener {
            binding.layout.openDrawer((GravityCompat.END))

        }

        val firstYearNum = MySharedPreferences.getFirstYear(this)
        val endYearNum = MySharedPreferences.getEndYear(this)
        var olcYearList = arrayListOf<String>(
            "사무국"
        )

        for (i in firstYearNum.toInt()..endYearNum.toInt()) {
            if (i.toString().length == 1) {
                val s = "%02d".format(i)
                olcYearList.add(s)
            } else {
                olcYearList.add(i.toString())
            }


        }


        val olcYearAdapter = OlcYearAdapter(this, olcYearList)
        olcYearRecyclerView.adapter = olcYearAdapter
        olcYearRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var olcSectorList = arrayListOf(

            "금속재료",
            "기계가공",
            "전자",
            "건축",
            "전기",
            "공예",
            "차량·철도",
            "패션",
            "산업안전",
            "폐지",
            "화학물 및 화학공정관리",
            "섬유제조",
            "금형",
            "통신기술",
            "기계조립·관리정비",
            "산업환경",
            "소재개발",
            "디자인",
            "제과.제빵",
            "조리",
            "해양자원",
            "이,미용",
            "선박·항공",
            "농업",
            "품질관리",
            "기계설계",
            "정보기술",
            "에너지.자원",
            "식품가공",
            "인쇄.출판",
            "차량.철도",
            "기계조립.관리정비",
            "차량철도",


            )
        val olcSectorAdapter = OlcSectorAdapter(this, olcSectorList)
        olcSectorRecyclerView.adapter = olcSectorAdapter
        olcSectorRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

//        binding.noteBtn1.setOnClickListener {
//
//            if (binding.btn1.isChecked) {
//                val noteFragmentSearch: NoteFragment =
//                    supportFragmentManager.findFragmentById(R.id.noteFrame) as NoteFragment
//                noteFragmentSearch.doSomething("01기")
//                binding.layout.closeDrawer((GravityCompat.END))
//            } else {
//                hoo()
//            }
//
//
//        }

    }

    fun goneSearch() {
//        binding.menuBtn2.visibility = View.GONE
    }

    private fun hoo() {
        Toast.makeText(this, "본인 기수 페이지 입니다.", Toast.LENGTH_SHORT).show()
        binding.layout.closeDrawer((GravityCompat.END))
    }

    inner class OlcYearAdapter(
        val context: Context,
        val olcYearList: ArrayList<String>,

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

            if (olcYear == "교수진") {
                holder.olcYear.text = "교수진"
            } else {
                holder.olcYear.text = olcYear
            }



            holder.itemView.setOnClickListener {


                if (binding.btn1.isChecked) {
                    val noteFragmentSearch: NoteFragment =
                        supportFragmentManager.findFragmentById(R.id.noteFrame) as NoteFragment
                    noteFragmentSearch.doSomething(olcYear)
                    binding.layout.closeDrawer((GravityCompat.END))
                } else {
                    hoo()
                }
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

    inner class OlcSectorAdapter(
        val context: Context,
        val olcSectorList: ArrayList<String>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note_sector, parent, false)
            val olcYear = olcSectorList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as OlcSectorAdapter.ViewHolder).itemView
            val olcSector = olcSectorList[position]
            holder.olcSector.text = olcSector



            holder.itemView.setOnClickListener {


                if (binding.btn1.isChecked) {
                    val noteFragmentSearch: NoteFragment =
                        supportFragmentManager.findFragmentById(R.id.noteFrame) as NoteFragment
                    noteFragmentSearch.doSector(olcSector)
                    binding.layout.closeDrawer((GravityCompat.END))
                } else {
                    hoo()
                }
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


    inner class NoteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val checkboxStatus = SparseBooleanArray()
        private val deNote: ArrayList<UserDataClass> = arrayListOf()
        val heart = intent.getStringExtra("phoneNum")
        var checkboxList = arrayListOf<checkboxData>()
        val uid = intent.getStringExtra("id")
        private var ck = 0
        private val limit = 10
        val first =
            firestore
                ?.collection("User")?.orderBy("year", Query.Direction.ASCENDING)
                ?.orderBy("num", Query.Direction.ASCENDING)?.limit(20)

        //        // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
        fun search(searchWord: String, option: String) {
            firestore
                ?.collection("User")
                ?.addSnapshotListener { querySnapshot, _ ->
                    // ArrayList 비워줌
                    deNote.clear()

                    for (snapshot in querySnapshot!!.documents) {
                        if (snapshot.getString(option)!!.contains(searchWord)) {
                            var item = snapshot.toObject(UserDataClass::class.java)
                            deNote.add(item!!)
                        }
                    }
                    notifyDataSetChanged()
                }
        }
//
//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val name: TextView = itemView.findViewById(R.id.noteName)
//            val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
//            val email: TextView = itemView.findViewById(R.id.mailAdress)
//            val company: TextView = itemView.findViewById(R.id.companyName)
//            val year: TextView = itemView.findViewById(R.id.noteYear)
//            val comPosition: TextView = itemView.findViewById(R.id.companyInfo)
//            val check: CheckBox = itemView.findViewById(R.id.bookmark)
//
//
//        }
//
//
//    }


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.noteFrame.windowToken, 0)
    }

}



