package com.example.yhaa17

import android.content.SharedPreferences
import android.os.Bundle
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
        talkList = sharData.getTalkingList()

        initValues()
        buttonZone()

        generalOperation()     // Let's play
    }

    private fun generalOperation() {

        if (counterStep < 1) counterStep = 1

        counterStep = 1           //*********************

        manMode = counterStep % 2 != 0

        val talker = talkList[counterStep]
        tranferValue(0)
        updateTitleTalkerSituation()
        animationInAction1.excuteTalker(talker)

    }


    private fun initValues() {

        myPref = getSharedPreferences(PREFS_NAME, 0)
        editor = myPref.edit()
        counterStep = myPref.getInt(CURRENT_SPEAKER, 1)
        animationInAction1 = AnimationAction(this, mainLayout)
        manipulateListView()
    }

    private fun findStyleObject(index: Int): StyleObject {
        var style1 = StyleObject()
        var bo = true
        var i = 0
        while (bo && i < Page.styleArray.size) {

            if (Page.styleArray[i].num == index) {
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
        for (item in Page.styleArray) {
            val st = item.num.toString()
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

    private fun trySomething() {

        talkList[counterStep].colorText = "#000000"
        talkList[counterStep].colorBack = "#ffffff"
        talkList[counterStep].animNum = 20
        talkList[counterStep].styleNum = 10
        tranferValue(0)
    }


    private fun updateTitleTalkerSituation() {
        val talker = talkList[counterStep]
        with(talker) {
            val text =
                "line->$lines style->$styleNum anim->$animNum size->$textSize dur->$dur $whoSpeake"
            tvAnimatinKind.text = text
        }
        tvPage.text = counterStep.toString()
        val text1 =
            "current: style->$current_styleNum action->$current_animNum size->$current_textSize dur->$current_dur "
        currentParameterTv.text = text1
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

    private fun trasferStyle() {

        var item = talkList[counterStep]
        val style = findStyleObject(current_styleNum)
        item.styleNum = current_styleNum
        item.colorBack = style.colorBack
        item.colorText = style.colorText
        item.paddingButton = style.paddingButton
        item.paddingTop = style.paddingTop
        item.paddingLeft = style.paddingLeft
        item.paddingRight = style.paddingRight
    }

    private fun buttonZone() {
        animView.setOnItemClickListener { _, _, position, _ ->
            current_styleNum = animList[position].toInt()
            trasferStyle()
            updateTitleTalkerSituation()
            tranferValue(1)
            generalOperation()
        }

        actioAnimLv.setOnItemClickListener { _, _, position, _ ->
            current_animNum = actionAnimList[position].toInt()
            talkList[counterStep].animNum=current_animNum
            updateTitleTalkerSituation()
            generalOperation()
        }

        saveButton.setOnClickListener {
            tranferValue(0)
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
            updateTitleTalkerSituation()
            sharData.saveData(talkList)

        }
        nextButton.setOnClickListener {
            counterStep++
            val max = talkList.size - 1
            if (counterStep > max) counterStep = max
            generalOperation()
            editor.putInt(CURRENT_SPEAKER, counterStep)
            editor.commit()
        }
        previousButton.setOnClickListener {
            counterStep--
            if (counterStep < 1) counterStep = 1
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
            current_textSize = current_textSize + 1
            tranferValue(1)
            generalOperation()
        }

        butTP5.setOnClickListener {
            current_textSize = current_textSize + 5
            tranferValue(1)
            generalOperation()
        }
        butTP20.setOnClickListener {
            current_textSize = current_textSize + 20
            tranferValue(1)
            generalOperation()
        }
        butTM1.setOnClickListener {
            current_textSize = current_textSize - 1
            tranferValue(1)
            generalOperation()
        }

        butTM5.setOnClickListener {
            current_textSize = current_textSize - 5
            tranferValue(1)
            generalOperation()
        }
        butTM20.setOnClickListener {
            current_textSize = current_textSize - 20
            tranferValue(1)
            generalOperation()
        }
        butDP100.setOnClickListener {
            current_dur = current_dur + 100
            tranferValue(1)
            generalOperation()
        }
        butDP500.setOnClickListener {
            current_dur = current_dur + 500
            tranferValue(1)
            generalOperation()
        }
        butDP1000.setOnClickListener {
            current_dur = current_dur + 1000
            tranferValue(1)
            generalOperation()
        }
        butDM100.setOnClickListener {
            current_dur = current_dur - 100
            tranferValue(1)
            generalOperation()
        }
        butDM500.setOnClickListener {
            current_dur = current_dur - 500
            tranferValue(1)
            generalOperation()
        }
        butDM1000.setOnClickListener {
            current_dur = current_dur - 1000
            tranferValue(1)
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

    private fun addStyleValueToTalkingList() {
        for (item in talkList) {
            val numStyle = item.styleNum
            val style = findStyleObject(numStyle)
            item.colorBack = style.colorBack
            item.colorText = style.colorText
            item.paddingButton = style.paddingButton
            item.paddingTop = style.paddingTop
            item.paddingLeft = style.paddingLeft
            item.paddingRight = style.paddingRight
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
            talkList[ind] = enterDefaltValueToTalkList(ind, talkList[ind])
        }
    }

    fun enterDefaltValueToTalkList(ind: Int, talker: Talker): Talker {

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



