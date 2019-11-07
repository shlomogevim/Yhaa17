package com.example.yhaa17

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_animation_screen.*
import kotlinx.android.synthetic.main.current_position_layout1.*
import kotlinx.android.synthetic.main.god_layout.*
import kotlinx.android.synthetic.main.man_layout.*


class AnimationScreen : AppCompatActivity() {
    companion object {
        const val FILE_NUM = "file_num"
    }

    lateinit var talkList: ArrayList<Talker>
    lateinit var textTalkList: ArrayList<Talker>
    lateinit var spicalTalkList: ArrayList<Talker>

    var currentFileNum = 10
    var STORELIST = "storelist"

    lateinit var sharData: ShareData


    var current_styleNum = 10
    var current_animNum = 10
    var current_dur = 1L
    var current_textSize = 1f

    private var manMode = true
    private var counterStep = 1

    lateinit var animationInAction1: AnimationAction

    val PREFS_NAME = "myPrefs"
    val CURRENT_SPEAKER = "stam_speaker"
    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var animList = arrayListOf<String>()
    var actionAnimList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_screen)

        currentFileNum = intent.getIntExtra(FILE_NUM, 0)
        sharData = ShareData(this, currentFileNum)
        talkList = sharData.getTalkingList(1)
        textTalkList = sharData.createTalkListFromTheStart()

        initValues()
        buttonZone()

        generalOperation()     // Let's play
    }

    private fun generalOperation() {

        if (counterStep < 1) counterStep = 1

        //  counterStep = 1           //*********************

        manMode = counterStep % 2 != 0

        // tranferValue(0)
        updateTitleTalkerSituation()
        animationInAction1.excuteTalker(talkList[counterStep])
    }

    private fun trySomething() {

        talkList[counterStep].colorBorder="#ffb300"
        generalOperation()
      //  copyTalker(1)
    }



    fun copyTalker(index: Int) {
        var bo = true
        var i = 0
        while (bo && i < spicalTalkList.size) {
            if (spicalTalkList[i].numTalker == index) {
                val spcialTalk = spicalTalkList[i]
                talkList[counterStep].styleNum = spcialTalk.styleNum
                talkList[counterStep].animNum = spcialTalk.animNum
                talkList[counterStep].textSize = spcialTalk.textSize
                talkList[counterStep].dur = spcialTalk.dur
                talkList[counterStep].colorText = spcialTalk.colorText
                talkList[counterStep].colorBack = spcialTalk.colorBack
                bo = false
            }
        }
        upgradeTalker()
    }

    fun createSpecialTalkList() {
        spicalTalkList = arrayListOf(

            Talker(
                numTalker = 1,styleNum = 411,animNum = 61, textSize = 288f,dur = 3000) // god "YES"
        )
    }
    private fun initValues() {

        myPref = getSharedPreferences(PREFS_NAME, 0)
        editor = myPref.edit()
        counterStep = myPref.getInt(CURRENT_SPEAKER, 1)
        animationInAction1 = AnimationAction(this, mainLayout)
        manipulateListView()
        createSpecialTalkList()
    }

    private fun findStyleObject(index: Int): StyleObject {
        var style1 = StyleObject()
        var bo = true
        var i = 0
        while (bo && i < Page.styleArray.size) {

            if (Page.styleArray[i].numStyleObject == index) {
                style1 = Page.styleArray[i]
                bo = false
            }
            i++
        }
        if (bo) style1 = Page.styleArray[10]
        return style1
    }

    private fun manipulateListView() {
        Page.createBasicStyle()
        for (i in 0..15) {
            animList.add("1")
        }
        animList.add("NB")
        for (item in Page.styleArray) {
            val st = item.numStyleObject.toString()
            animList.add(st)
        }
        for (i in 0..15) {
            animList.add("1000")
        }
        val adapter0 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animList)
        animView.adapter = adapter0

        for (i in 0..15) {
            actionAnimList.add("1")
        }
        val list = arrayListOf(
            "4",
            "10", "11", "12", "13", "14", "15",
            "20", "21", "22", "23", "24", "25",
            "30", "31", "32", "33", "34", "35",
            "40", "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54", "55", "506",
            "60", "61", "62", "63", "64", "65"
        )
        actionAnimList.addAll(list)
        for (i in 0..15) {
            actionAnimList.add("1000")
        }
        val adapter1 =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionAnimList)
        actioAnimLv.adapter = adapter1
        animView.setSelection(15)
        actioAnimLv.setSelection(15)
    }


    private fun updateTitleTalkerSituation() {
        with(talkList[counterStep]) {
            val text =
                "line->${takingArray.size} style->$styleNum anim->$animNum size->$textSize dur->$dur $whoSpeake"
            tvAnimatinKind.text = text
            tvPage.text = counterStep.toString()
        }

       }



    private fun trasferStyle() {

        var item = talkList[counterStep]
        val style = findStyleObject(item.styleNum)
        item.colorBack = style.colorBack
        item.colorText = style.colorText
    }

    private fun upgradeTalker() {

        var bo = true
        if (talkList[counterStep].textSize < 3) {
            talkList[counterStep].textSize = 3f
            Toast.makeText(this, "Text Size too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (talkList[counterStep].dur < 100) {
            talkList[counterStep].textSize = 100f
            Toast.makeText(this, "Duration too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (bo) {
            trasferStyle()
            updateTitleTalkerSituation()
            generalOperation()
        }

    }

    private fun buttonZone() {
        animView.setOnItemClickListener { _, _, position, _ ->
            if (position == 16) {
                talkList[counterStep].backExist = false
            } else {
                talkList[counterStep].backExist = true
                talkList[counterStep].styleNum = animList[position].toInt()
                // trasferStyle()
            }
            upgradeTalker()
            // updateTitleTalkerSituation()
            // generalOperation()
        }

        actioAnimLv.setOnItemClickListener { _, _, position, _ ->
            talkList[counterStep].animNum = actionAnimList[position].toInt()
            upgradeTalker()
            // updateTitleTalkerSituation()
            // generalOperation()
        }

        saveButton.setOnClickListener {
            //tranferValue(0)
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
            updateTitleTalkerSituation()
            sharData.saveData(talkList)
            Toast.makeText(this, "It's save Mr", Toast.LENGTH_SHORT).show()
        }
        nextButton.setOnClickListener {
            counterStep++
            val max = talkList.size - 1
            if (counterStep > max) counterStep = max
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
            generalOperation()
        }
        previousButton.setOnClickListener {
            counterStep--
            if (counterStep < 1) counterStep = 1
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
            generalOperation()
        }

        init_button.setOnClickListener {
            counterStep = 1
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
            generalOperation()
        }

        btnTry.setOnClickListener {
            trySomething()
        }
        butTP1.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize + 1
            upgradeTalker()
        }

        butTP5.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize + 5
            upgradeTalker()
        }
        butTP20.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize + 20
            upgradeTalker()
        }

        butTM1.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize - 1
            upgradeTalker()
        }


        butTM5.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize - 5
            upgradeTalker()
        }
        butTM20.setOnClickListener {
            talkList[counterStep].textSize = talkList[counterStep].textSize - 20
            upgradeTalker()
        }
        butDP100.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur + 100
            upgradeTalker()
        }
        butDP500.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur + 500
            upgradeTalker()
        }
        butDP1000.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur + 1000
            upgradeTalker()
        }
        butDM100.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur - 100
            upgradeTalker()
        }
        butDM500.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur - 500
            upgradeTalker()
        }
        butDM1000.setOnClickListener {
            talkList[counterStep].dur = talkList[counterStep].dur - 1000
            upgradeTalker()
        }

        textRevBtn.setOnClickListener {
            textTalkList = sharData.createTalkListFromTheStart()
            for (index in 0..talkList.size - 1) {
                val st1 = textTalkList[index].taking
                var arr = st1.split("\n")
                val ar = arrayListOf<String>()
                for (item in arr) {
                    if (item != "") {
                        ar.add(item)
                    }
                }
                talkList[index].takingArray = ar
                talkList[index].taking = textTalkList[index].taking
                generalOperation()
            }
            Toast.makeText(this, "Read all text From the start", Toast.LENGTH_SHORT).show()
        }

        pageNumBtn.setOnClickListener {
            pageNumEditText.visibility = View.VISIBLE
        }


        pageNumEditText.setOnClickListener {
            var currentPage = 1
            counterStep = pageNumEditText.text.toString().toInt()
            upgradeTalker()
            pageNumEditText.visibility=View.INVISIBLE
            pageNumEditText.hideKeyboard()

        }

        displayAgainBtn.setOnClickListener {
            generalOperation()
        }
        goddy.setOnClickListener {
            if (manMode) {
                counterStep++
                generalOperation()
            } else {
                Toast.makeText(this, "נסה שוב, זה התור של האדם לדבר", Toast.LENGTH_LONG).show()
            }
        }
        man.setOnClickListener {
            if (!manMode) {
                counterStep++
                generalOperation()
            } else {
                Toast.makeText(this, "נסה שוב, זה התור של האין סוף להגיב", Toast.LENGTH_LONG).show()
            }
        }
        mainLayout.setOnClickListener {
            /* counterStep++
             generalOperation()
             editor.putInt(CURRENT_SPEAKER, counterStep)
             editor.commit()*/
        }

    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun tranferValue(ind: Int) {


        with(talkList[counterStep]) {
            if (ind == 0) {
                current_styleNum = styleNum
                current_animNum = animNum
                current_dur = dur
                current_textSize = textSize
            } else {
                styleNum = current_styleNum
                trasferStyle()
                animNum = current_animNum
                if (current_dur > 100) {
                    dur = current_dur
                } else {
                    current_dur = 100
                }
                if (current_textSize > 10) {
                    textSize = current_textSize
                } else {
                    current_textSize = 10f
                }
            }
        }
        updateTitleTalkerSituation()
    }

    private fun addStyleValueToTalkingList() {
        for (item in talkList) {
            val numStyle = item.styleNum
            val style = findStyleObject(numStyle)
            item.colorBack = style.colorBack
            item.colorText = style.colorText
        }

    }

    private fun saveTalkingList() {
        val gson = Gson()
        val talkingString = gson.toJson(talkList)
        editor.putString(STORELIST, talkingString)
        editor.apply()
    }

    private fun retriveTalkingList() {
        talkList = arrayListOf()
        val gson = Gson()
        val jsonString = myPref.getString(STORELIST, null)
        if (jsonString == null) {
            updateTalkList()

        } else {
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList = gson.fromJson(jsonString, type)
        }
    }

    private fun updateTalkList() {
        //   operateList = FileStyling.initFileText11()
        for (ind in 1 until talkList.size) {
            talkList[ind] = enterDefaltValueToTalkList(talkList[ind])
        }
    }

    fun enterDefaltValueToTalkList(talker: Talker): Talker {

        if (talker.whoSpeake == "man") {
            talker.styleNum = 10
            talker.animNum = 20
            talker.dur = 2000L
            talker.textSize = 28f
        }
        if (talker.whoSpeake == "god") {
            talker.styleNum = 10
            talker.animNum = 20
            talker.dur = 2000L
            talker.textSize = 28f
        }
        return talker

    }

}


/* private fun retrieveData() {
       operateList.clear()
       val gson = Gson()
       val jsonString = myPref.getString(OPERATELIST, null)
       if (jsonString == null) {
           saveData()
       } else {
           val type = object : TypeToken<ArrayList<List<Int>>>() {}.type
           operateList = gson.fromJson(jsonString, type)
       }
   }*/



