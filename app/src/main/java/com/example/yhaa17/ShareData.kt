package com.example.yhaa17

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.preference.PreferenceManager
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class ShareData(val context: Context,val fileNum:Int) : AppCompatActivity() {

    val TALKLIST="talklist"+fileNum.toString()
    lateinit var talkList: ArrayList<Talker>
    lateinit var operateList: ArrayList<List<Int>>

    var myPref: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        myPref= PreferenceManager.getDefaultSharedPreferences(context)
        editor = myPref.edit()
    }


    fun getTalkingList(ind:Int): ArrayList<Talker> {
        var talkList1: ArrayList<Talker>
        val gson = Gson()
        val jsonString = myPref.getString(TALKLIST, null)

        if (ind==0 || jsonString == null) {
            talkList1=createTalkListFromTheStart()
            saveData(talkList1)

        } else {
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonString, type)
        }
        return talkList1
    }
    fun saveData(talkingList:ArrayList<Talker>) {
        val gson = Gson()
        val jsonString = gson.toJson(talkingList)
        editor.putString(TALKLIST, jsonString)
        editor.apply()
    }

     fun createTalkListFromTheStart(): ArrayList<Talker> {
        var talkList1 = arrayListOf<Talker>()
        val ADAM = "-אדם-"
        val GOD = "-אלוהים-"
        val CURRENT_FILE = "text/text"+fileNum.toString()+".txt"

        var countItem = 0
        var text = context.assets.open(CURRENT_FILE).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list1 = text.split(ADAM)

        var talker = Talker()

        talkList1.add(countItem, talker)

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
                    numTalker = countItem
                    var arr = st1.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText="#000000"
                    colorBack="#ffffff"
                    animNum=10
                }

                talkList1.add(talker)

                countItem++
                talker = Talker()
                with (talker) {
                    whoSpeake = "god"
                    talker.taking = st2.trim()
                    talker.numTalker = countItem
                    var arr = st2.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText="#000000"
                    colorBack="#ffffff"
                    animNum=10
                }
                talkList1.add(talker)
            }
        }
  return talkList1
    }

    private fun improveString(st: String) = st.substring(1, st.length - 1)




}