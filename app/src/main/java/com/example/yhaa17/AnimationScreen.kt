package com.example.yhaa17

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
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
    private var plusMode = true
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

    var interval = 0
    var currentColor = "#stam"
    lateinit var lastTalker: Talker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_animation_screen)



        currentFileNum = intent.getIntExtra(FILE_NUM, 0)
        sharData = ShareData(this, currentFileNum)
        activatApp = ActivateApp(this)
        talkList = sharData.getTalkingList(1)
        // textTalkList = sharData.createTalkListFromTheStart()

        initValues()
        styleListView()   //list view in the left side
        patamListView()   //second list view from the left
        ttParaListView() // third list viee from the left
        animationMovmentListView()  // list view in the right side

        initButton()
        lastTalker= Talker()
        tranferTalkItem(0)



        moveTheAnimation()     // Let's play
    }

    private fun tranferTalkItem(ind: Int) {
      if (ind==0){
          lastTalker=talkList[counterStep].copy()
         /* lastTalker.swingRepeat=talkList[counterStep].swingRepeat
          lastTalker.radius=talkList[counterStep].radius*/
      }else{
          talkList[counterStep]=lastTalker.copy()
      }



    }

    private fun moveTheAnimation() {
        updateTitleTalkerSituation()
        if (counterStep < 1) counterStep = 1

        //  counterStep = 1           //*********************

        manMode = counterStep % 2 != 0

        animationInAction1.excuteTalker(talkList[counterStep])
    }

    //--------------
    private fun styleListView() {// list view in the left side
        createStyleLV()
        style_ListView.setOnItemClickListener { _, _, position, _ ->
            tranferTalkItem(0)
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
            styleList.add("-")
        }
        styleList.add("NB")
        for (item in Page.styleArray) {
            val st = item.numStyleObject.toString()
            styleList.add(st)
        }
        for (i in 0..15) {
            styleList.add("-")
        }
        val adapter0 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, styleList)
        style_ListView.adapter = adapter0
        style_ListView.setSelection(15)
    }

    //----------------
    private fun patamListView() {
        createParaList()
        para_ListView.setOnItemClickListener { parent, view, position, id ->
            tranferTalkItem(0)
            translaePara(position)
        }
    }

    private fun createParaList() {
        for (i in 0..6) {
            paraList.add("-")
        }
        val list = arrayListOf(
            "Start",
            "-",
            "Swing1",
            "Page",
            "CopyTalk1",
            "-",
            "-",
            "-",
            "TextSize",
            "Duration",
            "-",
            "-",
            "Text Color",
            "Back Color",
            "Bord Color",
            "Bord Line",
            "No Bord",
            "Swing Re."
        )
        paraList.addAll(list)

        for (i in 0..20) {
            paraList.add("-")
        }

        val adapter10 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paraList)
        para_ListView.adapter = adapter10
        para_ListView.setSelection(15)
    }

    private fun translaePara(position: Int) {
        var intv: Int
        if (plusMode) intv = interval else intv = -interval
        when (position) {
            7 -> initIt()
            9 -> changeSwingRepeat(1)
            10 -> enterNewCounterStep()
            11 -> activatApp.copyTalker(talkList, counterStep, 1)
            15 -> talkList[counterStep].textSize = talkList[counterStep].textSize + intv
            16 -> talkList[counterStep].dur = talkList[counterStep].dur + intv
            //  17 ->
            //  18 ->
            19 -> changeTextColor()
            20 -> changeBackColor()
            21 -> changeBorderColor()
            22 -> changeBorderWidth(intv)
            23 -> talkList[counterStep].borderWidth=0
            24 -> changeSwingRepeat(intv)
        }
        chkNewData()
        if (position != 10) moveTheAnimation()
    }

    private fun chkNewData() {
        with(talkList[counterStep]) {
            if (textSize > 300f) textSize = 300f
            if (textSize < 8f) textSize = 8f
            if (dur > 10000f) dur = 10000
            if (dur < 100f) dur = 100
            if (radius > 100f) radius = 100f
            if (radius < 2f) radius = 2f
            if (borderWidth > 70) borderWidth = 70
            if (borderWidth < 0) borderWidth = 0
            if (swingRepeat > 10) swingRepeat = 10
            if (swingRepeat < 0) swingRepeat = 0
        }
    }

    private fun changeSwingRepeat(intv: Int) {
        talkList[counterStep].swingRepeat = talkList[counterStep].swingRepeat + intv
    }

    //------------------------
    private fun ttParaListView() {
        createTtParaTV()
        ttPara_listView.setOnItemClickListener { _, view, position, id ->
            translaeTtPara(position)
            Toast.makeText(
                this,
                "Don't forget to select Para ListView to excute the operation",
                Toast.LENGTH_SHORT
            ).show()
            // moveTheAnimation()
        }
    }

    private fun createTtParaTV() {
        for (i in 0..13) {
            ttParaList.add("-")
        }
        val list = getTtParaList()
        ttParaList.addAll(list)
        for (i in 0..20) {
            ttParaList.add("-")
        }

        val adapter11 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttParaList)
        ttPara_listView.adapter = adapter11
        ttPara_listView.setSelection(15)
    }


    private fun getTtParaList(): List<String> = arrayListOf(
        "Piker Color",
        "Color Nun",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "20",
        "50",
        "100",
        "1000",
        "2000",
        "3000",
        "5000",
        "C-White",
        "C-Black",
        "C-Red",
        "C-Pink",
        "C-Purple",
        "C-Blue",
        "C-LBlue",
        "C-Teal",
        "C-Green",
        "C-LGreen",
        "C-Lime",
        "C-Yellow",
        "C-Amber",
        "C-Orange",
        "C-DOrange",
        "C-Brown",
        "C-Gray",
        "C-BGray"
    )

    private fun translaeTtPara(position: Int) {
        when (position) {
            14 -> selectColor()
            15 -> colorNam_ET.visibility = View.VISIBLE
            16 -> interval = 0
            17 -> interval = 1
            18 -> interval = 2
            19 -> interval = 3
            20 -> interval = 4
            21 -> interval = 5
            22 -> interval = 6
            23 -> interval = 7
            24 -> interval = 8
            25 -> interval = 9
            26 -> interval = 10
            27 -> interval = 11
            28 -> interval = 12
            29 -> interval = 13
            30 -> interval = 14
            31 -> interval = 15
            32 -> interval = 20
            33 -> interval = 50
            34 -> interval = 100
            35 -> interval = 1000
            36 -> interval = 2000
            37 -> interval = 3000
            38 -> interval = 5000
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
                interval = 0
            }
        }
    }

    //------------------------
    private fun animationMovmentListView() {  // list view in the right side
        createAnimLV()
        action_ListView.setOnItemClickListener { _, _, position, _ ->
            tranferTalkItem(0)
            talkList[counterStep].animNum = actionList[position].toInt()
            moveTheAnimation()
        }
    }

    private fun createAnimLV() {

        for (i in 0..15) {
            actionList.add("-")
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
            actionList.add("-")
        }
        val adapter1 =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionList)
        action_ListView.adapter = adapter1
        action_ListView.setSelection(15)
    }

    //------------------

    override fun onClick(v: View) {
        when (v.id) {
            id.textRevBtn -> readAgainTextFile()
            id.newPageBtn -> enterNewCounterStep()
            id.plusAndMinusBtn -> changePlusMinusMode()
            id.displayAgainBtn -> moveTheAnimation()
            id.saveButton -> saveIt()
            id.nextButton -> nextIt()
            id.previousButton -> previousIt()
            id.lastTalker_button -> retriveLastTalker()
            id.reSizeTextBtn -> minTextSize()
            else -> moveTheAnimation()
        }
    }

    private fun retriveLastTalker() {
        tranferTalkItem(1)
        moveTheAnimation()
    }

    private fun minTextSize() {
        tranferTalkItem(0)
        talkList[counterStep].textSize = 12f  // for accsident of bigest text
        moveTheAnimation()
    }

    private fun readAgainTextFile() {
        val textTalkList = sharData.createTalkListFromTheStart()
        talkList = activatApp.textReRead(talkList, textTalkList)
        Handler().postDelayed(
            {
                moveTheAnimation()
            },
            2000 // value in milliseconds
        )
        Toast.makeText(this, "Read all text From the start", Toast.LENGTH_SHORT).show()
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

    private fun changeBorderWidth(intv: Int) {
        talkList[counterStep].borderWidth = talkList[counterStep].borderWidth + intv

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

    private fun showEditText(ind: Int) {
        if (ind == 0) {
            style_ListView.visibility = View.INVISIBLE
            para_ListView.visibility = View.INVISIBLE
            ttPara_listView.visibility = View.INVISIBLE
            action_ListView.visibility = View.INVISIBLE
            pageNumEditText.visibility = View.VISIBLE
        } else {
            style_ListView.visibility = View.VISIBLE
            para_ListView.visibility = View.VISIBLE
            ttPara_listView.visibility = View.VISIBLE
            action_ListView.visibility = View.VISIBLE
            pageNumEditText.visibility = View.INVISIBLE
            pageNumEditText.hideKeyboard()
        }


    }

    fun enterNewCounterStep() {

        showEditText(0)
        pageNumEditText.setHint("Enter New Page")

        pageNumEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                var newPage = 1
                try {
                    newPage = pageNumEditText.text.toString().toInt()
                } catch (e: Exception) {
                    Toast.makeText(this, "IIIigal num entery , try again", Toast.LENGTH_LONG).show()
                    newPage = 0
                }
                if (newPage < 1 || newPage > talkList.size - 1) {
                    Toast.makeText(
                        this,
                        "This Talker not exist,\n enter new talker num",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    counterStep = newPage
                    //upgradeTalker()
                    showEditText(1)
                    moveTheAnimation()
                }
                true
            } else {
                false
            }
        }
    }

    private fun initButton() {
        displayAgainBtn.setOnClickListener { onClick(displayAgainBtn) }
        textRevBtn.setOnClickListener { onClick(textRevBtn) }
        newPageBtn.setOnClickListener { onClick(newPageBtn) }
        plusAndMinusBtn.setOnClickListener { onClick(plusAndMinusBtn) }
        saveButton.setOnClickListener { onClick(saveButton) }
        nextButton.setOnClickListener { onClick(nextButton) }
        previousButton.setOnClickListener { onClick(previousButton) }
        lastTalker_button.setOnClickListener { onClick(lastTalker_button) }
        reSizeTextBtn.setOnClickListener { onClick(reSizeTextBtn) }
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
                "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                        " bord=$borderWidth dur=$dur sw=$swingRepeat"
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
        moveTheAnimation()
    }

    fun previousIt() {
        counterStep--
        if (counterStep < 1) counterStep = 1
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
        moveTheAnimation()
    }

    fun initIt() {
        counterStep = 1
        editor.putInt(CURRENT_SPEAKER, counterStep)
        editor.commit()
        moveTheAnimation()
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



