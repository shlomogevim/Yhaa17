package com.example.yhaa17

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yhaa17.R.id
import com.example.yhaa17.R.layout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_animation_screen.*
import kotlinx.android.synthetic.main.helper_view_layout.*


class AnimationScreen : AppCompatActivity(), View.OnClickListener {


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
    private var plusMode=true
    private var counterStep = 1

    lateinit var animationInAction1: AnimationAction
    lateinit var activatApp: ActivateApp

    val PREFS_NAME = "myPrefs"
    val CURRENT_SPEAKER = "stam_speaker"
    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var styleList = arrayListOf<String>()
    var paraList = arrayListOf<String>()
    var ttParaList = arrayListOf<String>()
    var actionList = arrayListOf<String>()
    var floatingInterval = 0f
    var longInterval = 0L
    var simpleNum = 0
    var currentColor = "#stam"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_animation_screen)



        currentFileNum = intent.getIntExtra(FILE_NUM, 0)
        sharData = ShareData(this, currentFileNum)
        activatApp = ActivateApp(this)
        talkList = sharData.getTalkingList(1)
        textTalkList = sharData.createTalkListFromTheStart()

        initValues()
        styleListView()   //list view in the left side
        patamListView()   //second list view from the left
        ttParaListView() // third list viee from the left
        animationMovmentListView()  // list view in the right side

        initButton()

        moveTheAnimation()     // Let's play
    }

    //--------------
    private fun styleListView() {// list view in the left side
        createStyleLV()
        style_ListView.setOnItemClickListener { _, _, position, _ ->
            if (position == 16) {     // ther is NB
                talkList[counterStep].backExist = false
            } else {
                talkList[counterStep].backExist = true
                talkList[counterStep].styleNum = styleList[position].toInt()
            }
            upgradeTalker()
        }
    }

    private fun createStyleLV() {
        Page.createBasicStyle()
        for (i in 0..15) {
            styleList.add("1")
        }
        styleList.add("NB")
        for (item in Page.styleArray) {
            val st = item.numStyleObject.toString()
            styleList.add(st)
        }
        for (i in 0..15) {
            styleList.add("1000")
        }
        val adapter0 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, styleList)
        style_ListView.adapter = adapter0
        style_ListView.setSelection(15)
    }

    //----------------
    private fun patamListView() {
        createParaList()
        para_ListView.setOnItemClickListener { parent, view, position, id ->
            translaePara(position)
        }
    }

    private fun createParaList() {
        for (i in 0..15) {
            paraList.add("1")
        }
        val list = arrayListOf(
            "TextSize", "Duration", "CopyTalk", "Page", "Bord Color", "Text Color", "Back Color",
            "Border W.","Repeat S."
        )
        paraList.addAll(list)

        for (i in 0..20) {
            paraList.add("Para $i")
        }

        val adapter10 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paraList)
        para_ListView.adapter = adapter10
        para_ListView.setSelection(15)
    }

    private fun translaePara(position: Int) {
        when (position) {
            16 -> talkList[counterStep].textSize = talkList[counterStep].textSize + floatingInterval
            17 -> talkList[counterStep].dur = talkList[counterStep].dur + longInterval
            18 -> activatApp.copyTalker(talkList, counterStep, simpleNum)
            19 -> enterNewCounterStep()
            20 -> changeBorderColor()
            21 -> changeTextColor()
            22 -> changeBackColor()
            23 -> changeBorderWidth()
            24->changeSwingRepeat()
        }
        moveTheAnimation()
    }

    private fun changeSwingRepeat() {
        talkList[counterStep].swingRepeat=talkList[counterStep].swingRepeat+simpleNum
    }

    //------------------------
    private fun ttParaListView() {
        createTtParaTV()
        ttPara_listView.setOnItemClickListener { parent, view, position, id ->
            translaeTtPara(position)
            moveTheAnimation()
        }
    }

    private fun createTtParaTV() {
        for (i in 0..15) {
            ttParaList.add("1")
        }
        val list = getTtParaList()
        ttParaList.addAll(list)
        for (i in 0..20) {
            ttParaList.add("TT-Para $i")
        }

        val adapter11 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttParaList)
        ttPara_listView.adapter = adapter11
        ttPara_listView.setSelection(15)
    }

   /* private fun getTtParaList(): List<String> = arrayListOf(
        "T+50", "T+20", "T+5", "T+1", "T-1", "T-5", "T-20", "T-50",
        "D+2000", "D+1000", "D+500", "D+100", "D-100", "D-500", "D-1000", "D-2000",
        "1", "2", "3", "4", "5", "Piker Color", "Color Nun", "C-White", "C-Black", "C-Red",
        "C-Pink", "C-Purple", "C-Blue", "C-LBlue", "C-Teal", "C-Green", "C-LGreen", "C-Lime",
        "C-Yellow", "C-Amber", "C-Orange", "C-DOrange", "C-Brown", "C-Gray", "C-BGray"
    )*/

    private fun getTtParaList(): List<String> = arrayListOf(
        "+50", "+20", "+5", "+1", "-1", "-5", "-20", "-50",
        "+2000", "+1000", "+500", "+100", "-100", "-500", "-1000", "-2000",
        "1", "2", "3", "4", "5", "Piker Color", "Color Nun", "C-White", "C-Black", "C-Red",
        "C-Pink", "C-Purple", "C-Blue", "C-LBlue", "C-Teal", "C-Green", "C-LGreen", "C-Lime",
        "C-Yellow", "C-Amber", "C-Orange", "C-DOrange", "C-Brown", "C-Gray", "C-BGray"
    )

    private fun translaeTtPara(position: Int) {
        when (position) {
            16 -> floatingInterval = 50f
            17 -> floatingInterval = 20f
            18 -> floatingInterval = 5f
            19 -> floatingInterval = 1f
            20 -> floatingInterval = -1f
            21 -> floatingInterval = -5f
            22 -> floatingInterval = -20f
            23 -> floatingInterval = -50f
            24 -> longInterval = 2000
            25 -> longInterval = 1000
            26 -> longInterval = 500
            27 -> longInterval = 100
            28 -> longInterval = -100
            29 -> longInterval = -500
            30 -> longInterval = -1000
            31 -> longInterval = -2000
            32, 33, 34, 35, 36 -> simpleNum = ttParaList[position].toInt()
            37 -> selectColor()
            38 -> colorNam_ET.visibility = View.VISIBLE
            39 -> currentColor = "#ffffff"
            40 -> currentColor = "#000000"
            41 -> currentColor = "#8e0000"
            41 -> currentColor = "#ad1457"
            42 -> currentColor = "#9c27b0"
            43 -> currentColor = "#1565c0"
            44 -> currentColor = "#03a9f4"
            45 -> currentColor = "#009688"
            46 -> currentColor = "#00701a"
            47 -> currentColor = "#9ccc65"
            48 -> currentColor = "#a0af22"
            49 -> currentColor = "#fdd835"
            50 -> currentColor = "#ffc107"
            51 -> currentColor = "#ff9800"
            52 -> currentColor = "#ff5722"
            53 -> currentColor = "#4b2c20"
            54 -> currentColor = "#9e9e9e"
            55 -> currentColor = "#90a4ae"

            else -> {
                floatingInterval = 0f
                longInterval = 0
                simpleNum = 0
            }
        }
        Toast.makeText(this, "Don't gorget to select Para to excute", Toast.LENGTH_LONG).show()
    }

    //------------------------
    private fun animationMovmentListView() {  // list view in the right side
        createAnimLV()
        action_ListView.setOnItemClickListener { _, _, position, _ ->
            talkList[counterStep].animNum = actionList[position].toInt()
            moveTheAnimation()
        }
    }

    private fun createAnimLV() {

        for (i in 0..15) {
            actionList.add("1")
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
        actionList.addAll(list)
        for (i in 0..15) {
            actionList.add("1000")
        }
        val adapter1 =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionList)
        action_ListView.adapter = adapter1
        action_ListView.setSelection(15)
    }

    //------------------
    private fun moveTheAnimation() {
        updateTitleTalkerSituation()
        if (counterStep < 1) counterStep = 1

        //  counterStep = 1           //*********************

        manMode = counterStep % 2 != 0

        animationInAction1.excuteTalker(talkList[counterStep])
    }


    private fun selectColor() {
        val intent = Intent(this, SelectColor::class.java)
        startActivityForResult(intent, 12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            currentColor = data?.getStringExtra("color")!!

        }
    }

    private fun changeBorderColor() {
        try {
            val color = Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(this, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talkList[counterStep].borderColor = currentColor

    }
    private fun changeBorderWidth() {
        talkList[counterStep].borderWidth = talkList[counterStep].borderWidth+floatingInterval.toInt()

    }

    private fun changeTextColor() {
        try {
            val color = Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(this, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talkList[counterStep].colorText = currentColor

    }

    private fun changeBackColor() {
        try {
            val color = Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(this, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talkList[counterStep].colorBack = currentColor

    }

    fun enterNewCounterStep() {
        var newPage = 1
        try {
            newPage = pageNumEditText.text.toString().toInt()
        } catch (e: Exception) {
            Toast.makeText(this, "IIIigal num entery , try again", Toast.LENGTH_LONG).show()
            newPage = 0
        }
        if (newPage < 1 || newPage > talkList.size - 1) {
            Toast.makeText(this, "This Talker not exist,\n enter new talker num", Toast.LENGTH_LONG)
                .show()
        } else {
            counterStep = newPage
            upgradeTalker()
            pageNumEditText.visibility = View.INVISIBLE
            pageNumEditText.hideKeyboard()
        }
    }

    private fun initButton() {
        displayAgainBtn.setOnClickListener { onClick(displayAgainBtn) }
        textRevBtn.setOnClickListener { onClick(textRevBtn) }
        numInBtn.setOnClickListener { onClick(numInBtn) }
        plusAndMinusBtn.setOnClickListener { onClick(plusAndMinusBtn) }
        saveButton.setOnClickListener { onClick(saveButton) }
        nextButton.setOnClickListener { onClick(nextButton) }
        previousButton.setOnClickListener { onClick(previousButton) }
        init_button.setOnClickListener { onClick(init_button) }
        reSizeBtn.setOnClickListener { onClick(reSizeBtn) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            id.textRevBtn -> {
                val textTalkList = sharData.createTalkListFromTheStart()
                activatApp.textReRead(talkList, textTalkList)
            }
            id.numInBtn -> pageNumEditText.visibility = View.VISIBLE
            id.plusAndMinusBtn -> changePlusMinusMode()
            id.displayAgainBtn->moveTheAnimation()
            id.saveButton -> saveIt()
            id.nextButton -> nextIt()
            id.previousButton -> previousIt()
            id.init_button -> initIt()
            id.reSizeBtn -> talkList[counterStep].textSize = 12f
            else -> moveTheAnimation()
        }
        moveTheAnimation()
    }

    private fun changePlusMinusMode() {
        if (plusMode) {
            plusMode = false
            plusAndMinusBtn.setText("-")
        } else {
            plusMode = true
            plusAndMinusBtn.setText("+")
        }
    }


    private fun initValues() {

        myPref = getSharedPreferences(PREFS_NAME, 0)
        editor = myPref.edit()
        counterStep = myPref.getInt(CURRENT_SPEAKER, 1)
        animationInAction1 = AnimationAction(this, mainLayout)
        //manipulateListView()
        //createSpecialTalkList()
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


    private fun updateTitleTalkerSituation() {
        with(talkList[counterStep]) {
            val text =
  "l=${takingArray.size} style=$styleNum anim=$animNum size=${textSize.toInt()}"+
          " bord=$borderWidth dur->$dur $whoSpeake"
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
            moveTheAnimation()
        }

    }

    fun saveIt() {
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
        updateTitleTalkerSituation()
        sharData.saveData(talkList)
        Toast.makeText(this, "It's save Mr", Toast.LENGTH_SHORT).show()
    }

    fun nextIt() {
        counterStep++
        val max = talkList.size - 1
        if (counterStep > max) counterStep = max
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
    }

    fun previousIt() {
        counterStep--
        if (counterStep < 1) counterStep = 1
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
    }

    fun initIt() {
        counterStep = 1
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
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



