package com.example.yhaa17

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yhaa17.AnimationScreen.Companion.FILE_NUM
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val CURRENT_NUM=20


   /* val CURRENT_FILE = "text/text"+CURRENT_NUM+".txt"
    val STYLE_FILE = "style/style11.txt"
    val ADAM = "-אדם-"
    val GOD = "-אלוהים-"
    lateinit var talkerList: ArrayList<Talker>
    var operateList = arrayListOf<List<Int>>()*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            //  mainStartLayout.setOnClickListener {
            val intent=Intent(this,AnimationScreen::class.java)
            intent.putExtra(FILE_NUM,CURRENT_NUM)
            startActivity(intent)

     //   }






       /* createTalkList()
        getStyleData()
        sendData()*/
    }

   /* private fun sendData() {
        val intent = Intent(this, AnimationScreen::class.java)

        intent.putExtra(TALKER, talkerList)
        intent.putExtra(STYLE, operateList)

        startActivity(intent)
    }

    private fun getStyleData() {
        var countStyle = 1
        var text = applicationContext.assets.open(STYLE_FILE).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list = text.split("#")
        operateList = arrayListOf()
        operateList.add(0, arrayListOf())
        for (element in list) {
            if (element != "") {
                var ar = element.split(",")
                operateList.add(
                    countStyle,
                    arrayListOf(
                        ar[0].trim().toInt(),
                        ar[1].trim().toInt(),
                        ar[2].trim().toInt(),
                        ar[3].trim().toInt()
                    )
                )
                countStyle++
            }
        }

    }


    private fun createTalkList() {
        var countItem = 0
        var text = applicationContext.assets.open(CURRENT_FILE).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list1 = text.split(ADAM)

        // speakerList = arrayListOf()
        talkerList = arrayListOf()

        var talker = Talker()

        talkerList.add(countItem, talker)

        for (element in list1) {
            //  if (element != "" && element.length > 25) {
            if (element != "") {
                var list2 = element.split(GOD)
                var st1 = improveString(list2[0])
                var st2 = improveString(list2[1])
                countItem++

                talker = Talker()
                with(talker) {
                    whoSpeake = "man"
                    taking = st1.trim()
                    num = countItem
                    var arr = st1.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    lines = takingArray.size
                }
                talkerList.add(talker)

                countItem++
                talker = Talker()
                with (talker) {
                    whoSpeake = "god"
                    talker.taking = st2.trim()
                    talker.num = countItem
                    var arr = st2.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    talker.lines = takingArray.size
                }
                talkerList.add(talker)
            }
        }

    }


    private fun improveString(st: String) = st.substring(1, st.length - 1)

*/
}



