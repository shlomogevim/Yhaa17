package com.example.yhaa17

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.god_layout.view.*
import kotlinx.android.synthetic.main.man_layout.view.*
import java.util.*


class AnimationAction(val context: Context, val view: View) {

    val helper = Helper(context)
    var tv0: TextView? = null
    var tv0A: TextView? = null
    var tv1: TextView? = null
    var tv2: TextView? = null
    var tv3: TextView? = null
    var tv4: TextView? = null
    var tv5: TextView? = null

    //val arrTextView = arrayOf(tv0, tv1, tv2, tv3, tv4, tv5)

    var man0: TextView = view.manSpeaking0
    var man1: TextView = view.manSpeaking1
    var man2: TextView = view.manSpeaking2
    var man3: TextView = view.manSpeaking3
    var man4: TextView = view.manSpeaking4
    var man5: TextView = view.manSpeaking5
    var god0: TextView = view.godSpeaking0
    var god0A: TextView = view.godSpeaking0A
    var god1: TextView = view.godSpeaking1
    var god2: TextView = view.godSpeaking2
    var god3: TextView = view.godSpeaking3
    var god4: TextView = view.godSpeaking4
    var god5: TextView = view.godSpeaking5

    var listOfTextview = arrayListOf<TextView?>()
    var listOfTextviewMul = arrayListOf<TextView?>()


    private fun initTextview() {
        tv0 = null
        tv0A = null
        tv1 = null
        tv2 = null
        tv3 = null
        tv4 = null
        tv5 = null
    }

    fun excuteTalker(talker: Talker) {
        // initAllTextview(500)
        if (talker.whoSpeake == "man") {
            configManTextView1(talker)
            listOfTextview.clear()
            listOfTextview = arrayListOf(tv0, tv1, tv2, tv3, tv4, tv5)
            listOfTextview.removeAll(Collections.singleton(null))
            letsMove(talker, listOfTextview, listOfTextviewMul, talker.dur)
        }

        if (talker.whoSpeake == "god") {
            configGodTextView1(talker)
            listOfTextview.clear()
            listOfTextview = arrayListOf(tv0, tv1, tv2, tv3, tv4, tv5)
            listOfTextview.removeAll(Collections.singleton(null))

            listOfTextviewMul.clear()
            listOfTextviewMul = arrayListOf(tv0A)
            listOfTextviewMul.removeAll(Collections.singleton(null))


            letsMove(talker, listOfTextview, listOfTextviewMul, talker.dur)
        }
    }

    private fun letsMove(
        talker: Talker,
        listOfTextview: ArrayList<TextView?>,
        listOfTextviewM: ArrayList<TextView?>,
        dur: Long
    ) {
        when (talker.animNum) {
            10 -> Utile.move_swing(0, talker, listOfTextview, dur)
            11 -> Utile.move_swing(1, talker, listOfTextview, dur)
            12 -> Utile.move_swing(2, talker, listOfTextview, dur)
            13 -> Utile.move_swing(3, talker, listOfTextview, dur)
            14 -> Utile.move_swing(4, talker, listOfTextview, dur)
            15 -> Utile.move_swing(5, talker, listOfTextview, dur)

            20 -> Utile.scale_swing(0, talker, listOfTextview, dur)
            21 -> Utile.scale_swing(1, talker, listOfTextview, dur)
            22 -> Utile.scale_swing(2, talker, listOfTextview, dur)
            23 -> Utile.scale_swing(3, talker, listOfTextview, dur)
            24 -> Utile.scale_swing(4, talker, listOfTextview, dur)
            25 -> Utile.scale_swing(5, talker, listOfTextview, dur)

            30 -> Utile.move_scale(0, listOfTextview, dur)
            31 -> Utile.move_scale(1, listOfTextview, dur)
            32 -> Utile.move_scale(2, listOfTextview, dur)
            33 -> Utile.move_scale(3, listOfTextview, dur)
            34 -> Utile.move_scale(4, listOfTextview, dur)
            35 -> Utile.move_scale(5, listOfTextview, dur)


            40 -> Utile.move_scale_rotate(0, talker, listOfTextview, dur)
            41 -> Utile.move_scale_rotate(1, talker, listOfTextview, dur)
            42 -> Utile.move_scale_rotate(2, talker, listOfTextview, dur)
            43 -> Utile.move_scale_rotate(3, talker, listOfTextview, dur)
            44 -> Utile.move_scale_rotate(4, talker, listOfTextview, dur)
            45 -> Utile.move_scale_rotate(5, talker, listOfTextview, dur)
            46 -> Utile.move_scale_rotate(6, talker, listOfTextview, dur)

            50 -> Utile.apeareOneAfterAnother(listOfTextview, dur)
            51 -> Utile.apeareOneAfterAnotherAndSwing(listOfTextview, dur)

            60 -> if (talker.whoSpeake == "god") {
                Utile.godAppearFromTwoPlaces(listOfTextview, listOfTextviewM, dur)
            } else {
                Utile.move_swing(0, talker, listOfTextview, dur)
            }
            else -> Utile.move_swing(0, talker, listOfTextview, dur)

        }

    }

    private fun styleTextViewTalk1(tv: TextView, st: String, talker: Talker): TextView {


        val shape = GradientDrawable()
        shape.setCornerRadius(60f)

        with(talker) {
            if (colorBack == "none" || !backExist) {
                shape.setColor(Color.parseColor("#00ff0000"))  //00 its all trans
            } else {
                try {
                    shape.setColor(Color.parseColor(colorBack))
                } catch (e: Exception) {
                    shape.setColor(Color.parseColor("#000000"))
                }
            }
            tv.background = shape

            try {
                tv.setTextColor(Color.parseColor(colorText))
            } catch (e: Exception) {
                tv.setTextColor(Color.parseColor("#ffffff"))
            }

            var t = textSize

            tv.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, textSize)
            tv.typeface = helper.getTypeFace(1)
            tv.setPadding(paddingLeft, paddingTop, paddingRight, paddingButton)
            tv.text = st
        }
        return tv
    }




    private fun configGodTextView1(talker: Talker) {
        initTextview()
        initGodTextview(1)

        val arr = talker.takingArray
        val size = arr.size

        tv0 = styleTextViewTalk1(god0, arr[0], talker)
        //  if (talker.animNum == 60) {
        tv0A = styleTextViewTalk1(god0A, arr[0], talker)
        //   } else {
        if (size > 1) tv1 = styleTextViewTalk1(god1, arr[1], talker)
        if (size > 2) tv2 = styleTextViewTalk1(god2, arr[2], talker)
        if (size > 3) tv3 = styleTextViewTalk1(god3, arr[3], talker)
        if (size > 4) tv4 = styleTextViewTalk1(god4, arr[4], talker)
        if (size > 5) tv5 = styleTextViewTalk1(god5, arr[5], talker)
        //  }
        initManTextview(500)
    }

    private fun configManTextView1(talker: Talker) {
        initTextview()
        initManTextview(1)
        /* val st = talker.taking
         val arr = st.split("\n")
         val size = talker.lines
 */
        val arr = talker.takingArray
        val size = arr.size
        if (size == 6) {
            tv0 = styleTextViewTalk1(man0, arr[0], talker)
            if (size > 1) tv1 = styleTextViewTalk1(man1, arr[1], talker)
            if (size > 2) tv2 = styleTextViewTalk1(man2, arr[2], talker)
            if (size > 3) tv3 = styleTextViewTalk1(man3, arr[3], talker)
            if (size > 4) tv4 = styleTextViewTalk1(man4, arr[4], talker)
            if (size > 5) tv5 = styleTextViewTalk1(man5, arr[5], talker)
        }
        if (size == 5) {
            tv0 = styleTextViewTalk1(man1, arr[0], talker)
            if (size > 1) tv1 = styleTextViewTalk1(man2, arr[1], talker)
            if (size > 2) tv2 = styleTextViewTalk1(man3, arr[2], talker)
            if (size > 3) tv3 = styleTextViewTalk1(man4, arr[3], talker)
            if (size > 4) tv4 = styleTextViewTalk1(man5, arr[4], talker)
        }
        if (size == 4) {
            tv0 = styleTextViewTalk1(man2, arr[0], talker)
            if (size > 1) tv1 = styleTextViewTalk1(man3, arr[1], talker)
            if (size > 2) tv2 = styleTextViewTalk1(man4, arr[2], talker)
            if (size > 3) tv3 = styleTextViewTalk1(man5, arr[3], talker)
        }
        if (size == 3) {
            tv0 = styleTextViewTalk1(man3, arr[0], talker)
            if (size > 1) tv1 = styleTextViewTalk1(man4, arr[1], talker)
            if (size > 2) tv2 = styleTextViewTalk1(man5, arr[2], talker)
        }
        if (size == 2) {
            tv0 = styleTextViewTalk1(man4, arr[0], talker)
            if (size > 1) tv1 = styleTextViewTalk1(man5, arr[1], talker)
        }
        if (size == 1) {
            tv0 = styleTextViewTalk1(man5, arr[0], talker)
        }
        initGodTextview(500)
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


    private fun initGodTextview(dur: Long) {
        ViewAnimator
            .animate(god0, god0A, god1, god2, god3, god4, god5)
            .scale(0f)
            .duration(dur)
            .start()
    }

    private fun initManTextview(dur: Long) {
        ViewAnimator
            .animate(man0, man1, man2, man3, man4, man5)
            .scale(0f)
            .duration(dur)
            .start()
    }

    private fun initAllTextview(dur: Long) {
        ViewAnimator
            .animate(man0, man1, man2, man3, man4, man5)
            .scale(0f)
            .duration(dur)
            .start()
        ViewAnimator
            .animate(god0, god0A, god1, god2, god3, god4, god5)
            .scale(0f)
            .duration(dur)
            .start()
    }


    fun manTalk(talker: Talker) {
        configManTextView1(talker)

        val listOfTextview = arrayListOf(tv0, tv1, tv2, tv3, tv4, tv5)
        val dur = talker.dur
        when (talker.animNum) {
            10 -> Utile.move_swing(0, talker, listOfTextview, dur)
            11 -> Utile.move_swing(1, talker, listOfTextview, dur)
            12 -> Utile.move_swing(2, talker, listOfTextview, dur)
            13 -> Utile.move_swing(3, talker, listOfTextview, dur)
            14 -> Utile.move_swing(4, talker, listOfTextview, dur)
            15 -> Utile.move_swing(5, talker, listOfTextview, dur)

            20 -> Utile.scale_swing(0, talker, listOfTextview, dur)
            21 -> Utile.scale_swing(1, talker, listOfTextview, dur)
            22 -> Utile.scale_swing(2, talker, listOfTextview, dur)
            23 -> Utile.scale_swing(3, talker, listOfTextview, dur)
            24 -> Utile.scale_swing(4, talker, listOfTextview, dur)
            25 -> Utile.scale_swing(5, talker, listOfTextview, dur)

            30 -> Utile.move_scale(0, listOfTextview, dur)
            31 -> Utile.move_scale(1, listOfTextview, dur)
            32 -> Utile.move_scale(2, listOfTextview, dur)
            33 -> Utile.move_scale(3, listOfTextview, dur)
            34 -> Utile.move_scale(4, listOfTextview, dur)
            35 -> Utile.move_scale(5, listOfTextview, dur)


            40 -> Utile.move_scale_rotate(0, talker, listOfTextview, dur)
            41 -> Utile.move_scale_rotate(1, talker, listOfTextview, dur)
            42 -> Utile.move_scale_rotate(2, talker, listOfTextview, dur)
            43 -> Utile.move_scale_rotate(3, talker, listOfTextview, dur)
            44 -> Utile.move_scale_rotate(4, talker, listOfTextview, dur)
            45 -> Utile.move_scale_rotate(5, talker, listOfTextview, dur)
            46 -> Utile.move_scale_rotate(6, talker, listOfTextview, dur)

            50 -> Utile.apeareOneAfterAnother(listOfTextview, dur)


            /*     10 -> Utile.scale_swing(0,talker,listOfTextview, dur)
             11 -> Utile.scale_swing(1,talker,listOfTextview, dur)
             12 -> Utile.scale_swing(2,talker,listOfTextview, dur)
             13 -> Utile.scale_swing(3,talker,listOfTextview, dur)
              20->Utile.move_scale(0,listOfTextview,dur)
              21->Utile.move_scale(1,listOfTextview,dur)
              22->Utile.move_scale(2,listOfTextview,dur)
              23->Utile.move_scale(3,listOfTextview,dur)
              24->Utile.move_scale(4,listOfTextview,dur)
              25->Utile.move_scale(5,listOfTextview,dur)
 */
            400 -> Utile.scale11(listOfTextview, dur)
            410 -> Utile.scale12(listOfTextview, dur)
            420 -> Utile.scale13(listOfTextview, dur)
        }
        // fadeDownAllGod(dur)
    }


    fun godTalk(talker: Talker) {
        scaleDownAllTextview()


        val st = talker.taking
        val arr = st.split("\n")
        val size = arr.size

        tv0 = styleTextViewTalk1(god0, arr[0], talker)
        tv0A = styleTextViewTalk1(god0A, arr[0], talker)
        if (size > 1) tv1 = styleTextViewTalk1(god1, arr[1], talker)
        if (size > 2) tv2 = styleTextViewTalk1(god2, arr[2], talker)
        if (size > 3) tv3 = styleTextViewTalk1(god3, arr[3], talker)
        if (size > 4) tv4 = styleTextViewTalk1(god4, arr[4], talker)
        if (size > 5) tv5 = styleTextViewTalk1(god5, arr[5], talker)

        val listOfTextview =
            arrayListOf(tv0, tv1, tv2, tv3, tv4, tv5, tv0A)
        val dur = talker.dur
        when (talker.animNum) {
            //  0 -> Utile.scale10(listOfTextview, dur)
            //    10 -> Utile.scale_swing(0,talker,listOfTextview, dur)
            //    20 -> Utile.scale12(listOfTextview, dur)
            //    3 -> Utile.scale13(listOfTextview, dur)


            //  4 -> Utile.godAppearFromTwoPlaces(listOfTextview, dur)
            10 -> Utile.scale_swing(0, talker, listOfTextview, dur)
            11 -> Utile.scale_swing(1, talker, listOfTextview, dur)
            12 -> Utile.scale_swing(2, talker, listOfTextview, dur)
            13 -> Utile.scale_swing(3, talker, listOfTextview, dur)
            20 -> Utile.move_scale(0, listOfTextview, dur)
            21 -> Utile.move_scale(1, listOfTextview, dur)
            22 -> Utile.move_scale(2, listOfTextview, dur)
            23 -> Utile.move_scale(3, listOfTextview, dur)
            24 -> Utile.move_scale(4, listOfTextview, dur)
            25 -> Utile.move_scale(5, listOfTextview, dur)
            30 -> Utile.move_scale_rotate(0, talker, listOfTextview, dur)
            31 -> Utile.move_scale_rotate(1, talker, listOfTextview, dur)
            32 -> Utile.move_scale_rotate(2, talker, listOfTextview, dur)
            33 -> Utile.move_scale_rotate(3, talker, listOfTextview, dur)
            34 -> Utile.move_scale_rotate(4, talker, listOfTextview, dur)
            35 -> Utile.move_scale_rotate(5, talker, listOfTextview, dur)
            36 -> Utile.move_scale_rotate(6, talker, listOfTextview, dur)


            40 -> Utile.scale11(listOfTextview, dur)
            41 -> Utile.scale12(listOfTextview, dur)
            42 -> Utile.scale13(listOfTextview, dur)


            50 -> Utile.apeareOneAfterAnother(listOfTextview, dur)

        }
    }

    fun scaleDownAllTextview() {
        ViewAnimator
            .animate(man0, man1, man2, man3, man4, man5)
            .scale(0f)
            .duration(500)
            .start()
        ViewAnimator
            .animate(god0, god0A, god1, god2, god3, god4, god5)
            .scale(0f)
            .duration(500)
            .start()
    }

    /* fun removeAllGodTextview() {
         ViewAnimator
             .animate(god0, god0A, god1, god2, god3, god4, god5)
             .scale(0f)
             .duration(1)
             .start()
     }*/

    fun fadeDownAllMan(dur: Long) {

        ViewAnimator
            .animate(man0, man1, man2, man3, man4, man5)
            .scale(0f)
            .duration(dur)
            .start()
    }


    fun fadeDownAllGod(dur: Long) {
        ViewAnimator
            .animate(god0, god1, god0A, god2, god3, god4, god5)
            .scale(0f)
            .duration(dur)
            .start()
    }


    /* fun manTalk(talker: Talker) {
       initAllManTextview()
       configManTextView1(talker)

       val listOfTextview =
           arrayListOf(tv0, tv1, tv2, tv3, tv4, tv5)
       val dur = talker.dur
       when (talker.animNum) {
           0 -> Utile.scale60A(listOfTextview, dur)
           1 -> Utile.scale61A(listOfTextview, dur)
           2 -> Utile.scale62A(listOfTextview, dur)
           3 -> Utile.scale63A(listOfTextview, dur)

       }
       fadeDownAllGod(dur)
   }
*/
}