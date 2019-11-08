package com.example.yhaa17

import android.content.Context
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.current_position_layout1.*


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
    private var counterStep = 1

    lateinit var animationInAction1: AnimationAction
    lateinit var activatApp: ActivateApp

    val PREFS_NAME = "myPrefs"
    val CURRENT_SPEAKER = "stam_speaker"
    lateinit var myPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var animList = arrayListOf<String>()
    var paraList = arrayListOf<String>()
    var ttParaList = arrayListOf<String>()
    var actionAnimList = arrayListOf<String>()
    var ttParaText=0f
    var ttParaDur=0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_animation_screen)



        currentFileNum = intent.getIntExtra(FILE_NUM, 0)
        sharData = ShareData(this, currentFileNum)
        activatApp = ActivateApp(this)
        talkList = sharData.getTalkingList(1)
        textTalkList = sharData.createTalkListFromTheStart()

        initValues()
        initButton()
        //buttonZ()
        buttonZone()

        //  trySome()


        generalOperation()     // Let's play
    }

    private fun initButton() {
        butTP1.setOnClickListener { onClick(butTP1) }
        butTP5.setOnClickListener { onClick(butTP5) }
        butTP20.setOnClickListener { onClick(butTP20) }
        butTM1.setOnClickListener { onClick(butTM1) }
        butTM5.setOnClickListener { onClick(butTM5) }
        butTM20.setOnClickListener { onClick(butTM20) }
        butDP100.setOnClickListener { onClick(butDP100) }
        butDP100.setOnClickListener { onClick(butDP100) }
        butDP500.setOnClickListener { onClick(butDP500) }
        butDP1000.setOnClickListener { onClick(butDP1000) }
        textRevBtn.setOnClickListener { onClick(textRevBtn) }
        pageNumBtn.setOnClickListener { onClick(pageNumBtn) }
        pageNumEditText.setOnClickListener { onClick(pageNumEditText) }
        btnTry.setOnClickListener { onClick(btnTry) }
        saveButton.setOnClickListener { onClick(saveButton) }
        nextButton.setOnClickListener { onClick(nextButton) }
        previousButton.setOnClickListener { onClick(previousButton) }
        init_button.setOnClickListener { onClick(init_button) }

    }


    override fun onClick(v: View) {
        when (v.id) {
            id.butTP1 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 1)
            id.butTP5 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 5)
            id.butTP20 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 20)
            id.butTM1 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 1)
            id.butTM5 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 5)
            id.butTM20 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 20)
            id.butDP100 -> activatApp.durationUpgrade(talkList, counterStep, 1, 100)
            id.butDP500 -> activatApp.durationUpgrade(talkList, counterStep, 1, 500)
            id.butDP1000 -> activatApp.durationUpgrade(talkList, counterStep, 1, 1000)
            id.textRevBtn -> {
                val textTalkList = sharData.createTalkListFromTheStart()
                activatApp.textReRead(talkList, textTalkList)
            }
            id.pageNumBtn -> pageNumEditText.visibility = View.VISIBLE
            id.pageNumEditText -> pageNumEdit()
            id.btnTry -> trySomething()
            id.saveButton -> saveIt()
            id.nextButton -> nextIt()
            id.previousButton -> previousIt()
            id.init_button -> initIt()
            else -> generalOperation()
        }
        generalOperation()
    }


    private fun generalOperation() {
        updateTitleTalkerSituation()

        if (counterStep < 1) counterStep = 1

        //  counterStep = 1           //*********************

        manMode = counterStep % 2 != 0
        updateTitleTalkerSituation()
        animationInAction1.excuteTalker(talkList[counterStep])
    }

    fun pageNumEdit() {
        var currentPage = 1
        counterStep = pageNumEditText.text.toString().toInt()
        upgradeTalker()
        pageNumEditText.visibility = View.INVISIBLE
        pageNumEditText.hideKeyboard()
    }

    private fun trySomething() {
        talkList[counterStep].colorBorder = "#ffb300"
        generalOperation()
        //  activatApp.copyTalker(talkList,counterStep,1)
    }


    private fun initValues() {

        myPref = getSharedPreferences(PREFS_NAME, 0)
        editor = myPref.edit()
        counterStep = myPref.getInt(CURRENT_SPEAKER, 1)
        animationInAction1 = AnimationAction(this, mainLayout)
        manipulateListView()
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

    private fun manipulateListView() {
        createStyleLV()
        createParaLV()
        createTtParaTV()
        createAnimLV()

        animView.setSelection(15)
        actioAnimLv.setSelection(15)
        para_ListView.setSelection(15)
        ttPara_listView.setSelection(15)
    }
    private fun createTtParaTV() {
        for (i in 0..15) {
            ttParaList.add("1")
        }

        val list = arrayListOf(

            "T+50", "T+20", "T+5", "T+1", "T-1", "T-5", "T-20", "T-50",
            "D+2000","D+1000","D+500","D+100","D-100","D-500","D-1000","D-2000"
        )
        ttParaList.addAll(list)

        for (i in 0..20) {
            ttParaList.add("TT-Para $i")
        }

        val adapter11 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttParaList)
        ttPara_listView.adapter = adapter11
    }

    private fun createParaLV() {
        for (i in 0..15) {
            paraList.add("1")
        }
        val list = arrayListOf(

            "TextSize", "Duration"
        )
        paraList.addAll(list)

        for (i in 0..20) {
            paraList.add("Para $i")
        }

        val adapter10 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paraList)
        para_ListView.adapter = adapter10

    }


    private fun createAnimLV() {

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
    }


    private fun createStyleLV() {
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

    private fun buttonZone() {
        animView.setOnItemClickListener { _, _, position, _ ->
            if (position == 16) {
                talkList[counterStep].backExist = false
            } else {
                talkList[counterStep].backExist = true
                talkList[counterStep].styleNum = animList[position].toInt()
            }
            upgradeTalker()
        }

        actioAnimLv.setOnItemClickListener { _, _, position, _ ->
            talkList[counterStep].animNum = actionAnimList[position].toInt()
            upgradeTalker()
        }
        ttPara_listView.setOnItemClickListener { parent, view, position, id ->
            translaeTtPara(position)
        }
        para_ListView.setOnItemClickListener { parent, view, position, id ->
            translaePara(position)
        }

    }

    private fun translaePara(position: Int) {
        when (position){
            16->talkList[counterStep].textSize=talkList[counterStep].textSize+ttParaText
            17->talkList[counterStep].dur=talkList[counterStep].dur+ttParaDur

        }
        generalOperation()
    }

    private fun translaeTtPara(position: Int) {
        when (position){
            16->ttParaText=50f
            17->ttParaText=20f
            18->ttParaText=5f
            19->ttParaText=1f
            20->ttParaText=-1f
            21->ttParaText=-5f
            22->ttParaText=-20f
            23->ttParaText=-50f
            24->ttParaDur=2000
            25->ttParaDur=1000
            26->ttParaDur=500
            27->ttParaDur=100
            28->ttParaDur=-100
            29->ttParaDur=-500
            30->ttParaDur=-1000
            31->ttParaDur=-2000

            else->{
                ttParaText=0f
                ttParaDur=0
            }
        }

Toast.makeText(this,"Don't gorget to select Para to excute",Toast.LENGTH_LONG).show()

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    /* private fun trySome() {
          view.setOnClickListener { v ->
              when (v.id) {
                  id.butTP1 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 1)
                  id.butTP5 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 5)
                  id.butTP20 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 20)
                  id.butTM1 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 1)
                  id.butTM5 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 5)
                  id.butTM20 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 20)
                  id.butDP100 -> activatApp.durationUpgrade(talkList, counterStep, 1, 100)
                  id.butDP500 -> activatApp.durationUpgrade(talkList, counterStep, 1, 500)
                  id.butDP1000 -> activatApp.durationUpgrade(talkList, counterStep, 1, 1000)
                  id.textRevBtn -> {
                      val textTalkList = sharData.createTalkListFromTheStart()
                      activatApp.textReRead(talkList, textTalkList)
                  }
                  id.pageNumBtn -> pageNumEditText.visibility = View.VISIBLE
                  id.pageNumEditText -> pageNumEdit()
                  id.btnTry -> trySomething()
                  id.saveButton -> saveIt()
                  id.nextButton -> nextIt()
                  id.previousButton -> previousIt()
                  id.init_button -> initIt()
                  else->generalOperation()
              }
              generalOperation()
          }
      }*/
    /* fun initButton{
           butTP1.setOnClickListener { onClick() }
             id.butTP5 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 5)
             id.butTP20 -> activatApp.textSizeUpgrade(talkList, counterStep, 1, 20)
             id.butTM1 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 1)
             id.butTM5 -> activatApp.textSizeUpgrade(talkList, counterStep, 0, 5)
             id.butTM20 -> actbivatApp.textSizeUpgrade(talkList, counterStep, 0, 20)
             id.butDP100 -> activatApp.durationUpgrade(talkList, counterStep, 1, 100)
             id.butDP500 -> activatApp.durationUpgrade(talkList, counterStep, 1, 500)
             id.butDP1000 -> activatApp.durationUpgrade(talkList, counterStep, 1, 1000)
             id.textRevBtn -> {
                 val textTalkList = sharData.createTalkListFromTheStart()
                 activatApp.textReRead(talkList, textTalkList)
             }
             id.pageNumBtn -> pageNumEditText.visibility = View.VISIBLE
             id.pageNumEditText -> pageNumEdit()
             id.btnTry -> trySomething()
             id.saveButton -> saveIt()
             id.nextButton -> nextIt()
             id.previousButton -> previousIt()
             id.init_button -> initIt()
             else->generalOperation()
         }
         generalOperation()
     }*/


/* private fun buttonZ() {
        *//* butTP1.setOnClickListener {
             activatApp.textSizeUpgrade(talkList,counterStep,1,1)

         }*//*
        butTP1.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 1, 1)
        }
        butTP5.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 1, 5)
        }
        butTP20.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 1, 20)
        }
        butTM1.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 0, 1)
        }
        butTM5.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 0, 5)
        }
        butTM20.setOnClickListener {
            activatApp.textSizeUpgrade(talkList, counterStep, 0, 20)
        }
        butDP100.setOnClickListener {
            activatApp.durationUpgrade(talkList, counterStep, 1, 100)
        }
        butDP500.setOnClickListener {
            activatApp.durationUpgrade(talkList, counterStep, 1, 500)
        }
        butDP1000.setOnClickListener {
            activatApp.durationUpgrade(talkList, counterStep, 1, 1000)
        }
        textRevBtn.setOnClickListener {
            val textTalkList = sharData.createTalkListFromTheStart()
            activatApp.textReRead(talkList, textTalkList)
        }
        pageNumBtn.setOnClickListener {
            pageNumEditText.visibility = View.VISIBLE
        }
        pageNumEditText.setOnClickListener {
            pageNumEdit()
        }
        btnTry.setOnClickListener {
            trySomething()
        }
        displayAgainBtn.setOnClickListener {

        }
        saveButton.setOnClickListener {
            saveIt()
        }
        nextButton.setOnClickListener {
            nextIt()
            generalOperation()
        }
        previousButton.setOnClickListener {
            previousIt()
            generalOperation()
        }
        init_button.setOnClickListener {
            initIt()
        }

        generalOperation()
    }*/
    /* fun copyTalker(index: Int) {
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
    }*/

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



