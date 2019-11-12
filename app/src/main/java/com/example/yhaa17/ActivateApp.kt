package com.example.yhaa17

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivateApp(val context: Context) : AppCompatActivity() {


    fun textReRead(talkList: ArrayList<Talker>,textTalkList: ArrayList<Talker>): ArrayList<Talker> {
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
        }
        return talkList
    }


    fun textSizeUpgrade(
        talkList: ArrayList<Talker>,
        conterStep: Int,
        plusOrMius: Int,
        inteval: Int
    ): ArrayList<Talker> {
        if (plusOrMius == 1) {
            talkList[conterStep].textSize = talkList[conterStep].textSize + inteval
        }
        if (plusOrMius == 0) {
            talkList[conterStep].textSize = talkList[conterStep].textSize - inteval
        }
        upgradeTalker(talkList, conterStep)

        return talkList
    }

    fun durationUpgrade(
        talkList: ArrayList<Talker>,
        conterStep: Int,
        plusOrMius: Int,
        inteval: Int
    ): ArrayList<Talker> {
        if (plusOrMius == 1) {
            talkList[conterStep].dur = talkList[conterStep].dur + inteval
        }
        if (plusOrMius == 0) {
            talkList[conterStep].dur = talkList[conterStep].dur - inteval
        }
        upgradeTalker(talkList, conterStep)

        return talkList
    }

    private fun upgradeTalker(talkList: ArrayList<Talker>, conterStep: Int) {
        var bo = true
        if (talkList[conterStep].textSize < 3) {
            talkList[conterStep].textSize = 3f
            Toast.makeText(context, "Text Size too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (talkList[conterStep].dur < 100) {
            talkList[conterStep].textSize = 100f
            Toast.makeText(context, "Duration too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (bo) {
            trasferStyle(talkList, conterStep)
        }
    }

    private fun trasferStyle(talkList: ArrayList<Talker>, conterStep: Int) {
        var item = talkList[conterStep]
        val style = findStyleObject(item.styleNum)
        item.colorBack = style.colorBack
        item.colorText = style.colorText
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



    fun copyTalker(talkList: ArrayList<Talker>, conterStep: Int, index: Int) {
        var spicalTalkList = arrayListOf(
            Talker(
                numTalker = 1, styleNum = 411, animNum = 61, textSize = 288f, dur = 3000
            ) // god "YES"
        )
        if (index==1 && talkList[conterStep].whoSpeake=="man") return
        var bo = true
        var i = 0
        while (bo && i < spicalTalkList.size) {
            if (spicalTalkList[i].numTalker == index) {
                val spcialTalk = spicalTalkList[i]
                talkList[conterStep].styleNum = spcialTalk.styleNum
                talkList[conterStep].animNum = spcialTalk.animNum
                talkList[conterStep].textSize = spcialTalk.textSize
                talkList[conterStep].dur = spcialTalk.dur
                val style = findStyleObject(spcialTalk.styleNum)
                talkList[conterStep].colorText = style.colorText
                talkList[conterStep].colorBack = style.colorBack
                bo = false
            }
        }

    }
}




