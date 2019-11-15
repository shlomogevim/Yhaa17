package com.example.yhaa17

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yhaa17.AnimationScreen.Companion.FILE_NUM
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val CURRENT_NUM = 20

    private var convList: ArrayList<Conversation>? = null
    private var adapter: ConvListAtapter? = null
    private var layoutManger: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAll()
        recyclerView.layoutManager = layoutManger
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()


    }

    private fun initAll() {
         convList= arrayListOf()

        var st1 = ""
        var st2 = ""
        val st3="\n"
        for (i in 1..30) {

            when (i) {

                1 -> {
                    st1 = "כן ולא"
                    val st20 = "ישנם שלש תשובות"
                    val st21="כן, לא"
                     val st22="והתשובה השלישית היא כן ולא"
                    val st23="עליה השיחה"
                    st2=st20+st3+st21+st3+st22+st3+st23
                }
                else -> {
                    st1 = "talk num. $i"
                    st2 ="still not prepared"
                }
            }
        val conv = Conversation(i, st1, st2)
            convList?.add(conv)
    }

    layoutManger= LinearLayoutManager(this)
    adapter= ConvListAtapter(convList!!,onClickListener =
    {
        conversation ->
        operateConverastion(conversation)

       // Toast.makeText(this, "You press on Item no ${conversation.numC}", Toast.LENGTH_SHORT).show()
    })
}

private fun operateConverastion(conversation: Conversation) {
    var CURRENT_NUM=0

    when (conversation.numC){
        1->CURRENT_NUM=20

    }

    val intent= Intent(this,AnimationScreen::class.java)
    intent.putExtra(FILE_NUM,CURRENT_NUM)
    startActivity(intent)
}

    private fun getFirstTalk() {

       /* val CURRENT_NUM=20


        *//* val CURRENT_FILE = "text/text"+CURRENT_NUM+".txt"
         val STYLE_FILE = "style/style11.txt"
         val ADAM = "-אדם-"
         val GOD = "-אלוהים-"
         lateinit var talkerList: ArrayList<Talker>
         var operateList = arrayListOf<List<Int>>()*//*


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            //  mainStartLayout.setOnClickListener {
            val intent=Intent(this,AnimationScreen::class.java)
            intent.putExtra(FILE_NUM,CURRENT_NUM)
            startActivity(intent)

            //   }
        */










    }


}



