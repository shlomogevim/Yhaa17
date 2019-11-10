package com.example.yhaa17

import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import com.github.florent37.viewanimator.ViewAnimator

object Utile {
    var wi: Float = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    var hi: Float = Resources.getSystem().displayMetrics.heightPixels.toFloat()


    fun item_scale(ind: Int, textView: TextView, dur: Long) {

        if (ind > -7) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }
    }

    fun item_scale_swing(ind: Int, textView: TextView, dur: Long) {

        if (ind > -7) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(textView)
                .swing()
                .repeatCount(1)
                .start()
        }
    }

    fun item_move(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)
        ViewAnimator
            .animate(textView)
            .scale(1f)
            .duration(1)
            .thenAnimate(textView)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .duration(dur)
            .start()
    }

    fun item_move_swing(ind: Int, textView: TextView, dur: Long,rep:Int) {
        var arr = getCordinateAndSpine(ind)
        if (rep==0){
            ViewAnimator
                .animate(textView)
                .scale(1f)
                .duration(1)
                .thenAnimate(textView)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .start()
        }else {

            ViewAnimator
                .animate(textView)
                .scale(1f)
                .duration(1)
                .thenAnimate(textView)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .thenAnimate(textView)
                .swing()
                .repeatCount(rep)
                .start()
        }
    }

    fun item_move_scale(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)

        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .duration(dur)
            .start()
    }

    fun item_move_scale_rotate(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)
        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .rotation(arr[2])
            .duration(dur)
            .thenAnimate(textView)
            .rotation(-arr[2])
            .duration(1)
            .start()

    }

    fun move_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {

        val linesNum = talker.takingArray.size
        if (selector == 0) {
            for (index in 1..linesNum) {
                item_move(index, arr[index - 1]!!, dur)
            }
        }

        if (selector == 1) {
            if (linesNum>1) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index == 1) {
                            item_move(index, it, dur)
                        } else {
                            item_move_swing(index, it, dur,talker.swingRepeat)

                        }
                    }
                }
            }else{
                item_move_swing(1, arr[0]!!, dur,talker.swingRepeat)

            }
        }
        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_move(index, it, dur)
                    } else {
                        item_move_swing(index, it, dur,talker.swingRepeat)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_move(index, it, dur)
                    } else {
                        item_move_swing(index, it, dur,talker.swingRepeat)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_move(index, it, dur)
                    } else {
                        item_move_swing(index, it, dur,talker.swingRepeat)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_move(index, it, dur)
                    } else {
                        item_move_swing(index, it, dur,talker.swingRepeat)

                    }
                }
            }
        }
    }

    fun scale_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {

        val linesNum = talker.takingArray.size
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        }
        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        }


    }


    fun move_scale_rotate(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {
        val linesNum = talker.takingArray.size
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_move_scale_rotate(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale_rotate(index, it, dur)

                    }
                }
            }
        }

/*    val linesNum = talker.lines
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)

                    }
                }
            }
        } */









        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale_rotate(index, it, dur)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale_rotate(index, it, dur)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale_rotate(index, it, dur)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale_rotate(index, it, dur)

                    }
                }
            }
        }


        if (selector == 6) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= talker.takingArray.size - 1) {
                        item_move_scale_rotate(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)
                    }
                }
            }
        }
    }

    fun apeareOneAfterAnother(arr: ArrayList<TextView?>, dur: Long) {
        if (arr.size == 1) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }

        if (arr.size == 2) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }
        if (arr.size == 3) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }
        if (arr.size == 4) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }
        if (arr.size == 5) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }
        if (arr.size == 6) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }

    }

    fun apeareOneAfterAnotherAndSwing(arr: ArrayList<TextView?>, dur: Long) {
        if (arr.size == 1) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .swing()
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }

        if (arr.size == 2) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }
        if (arr.size == 3) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }
        if (arr.size == 4) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }
        if (arr.size == 5) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }
        if (arr.size == 6) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .swing()
                .start()
        }
    }


    fun godAppearFromTwoPlaces(ind:Int,arr: ArrayList<TextView?>,arr1: ArrayList<TextView?>,color:String,dur: Long) {

       if (ind==0) {
           ViewAnimator
               .animate(arr[0])
               .translationX(-wi / 2, 0f)
               .translationY(hi, 0f)
               .scale(0f, 1f)
               .andAnimate(arr1[0])
               .translationX(wi / 2, 0f)
               .translationY(hi, 0f)
               .scale(0f, 1f)
               .duration(dur)
               .start()
       }
        if (ind==1) {
            arr[0]?.setBackgroundColor(Color.TRANSPARENT)
            arr1[0]?.setBackgroundColor(Color.TRANSPARENT)
            ViewAnimator
                .animate(arr[0])
                .translationX(-wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .andAnimate(arr1[0])
                .translationX(wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[0])
                .backgroundColor(Color.parseColor(color))
                .duration(2000)
                .start()
        }


    }

/*  fun ללללmove_scale(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {

        val linesNum = talker.lines
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_move_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }


        if (selector == 6) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= talker.lines - 1) {
                        item_move_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)
                    }
                }
            }
        }
    }*/


    private fun ttMove1(
        textView: TextView,
        x0: Float,
        x1: Float,
        y0: Float,
        y1: Float,
        scale0: Float,
        scale1: Float,
        duration: Long
    ) {
        ViewAnimator
            .animate(textView)
            .translationX(x0, x1)
            .translationY(y0, y1)
            .scale(scale0, scale1)
            .duration(duration)
            .start()
    }


    private fun ttScale1(textView: TextView, dur: Long) {
        ViewAnimator.animate(textView).scale(0f, 1f).duration(dur).start()
    }

    fun getCordinateAndSpine(ind: Int): Array<Float> {
        var x0 = 0f;
        var y0 = 0f;
        var rotate = 360f;
        when (ind) {
            1 -> {
                x0 = -wi / 2
                y0 = -hi
                rotate = 720f * 2
            }
            2 -> {
                x0 = wi / 2
                y0 = -hi
                rotate = -720f * 2
            }
            3 -> {
                x0 = -wi / 2
                y0 = 0f
                rotate = 360f
            }
            4 -> {
                x0 = wi / 2
                y0 = 0f
                rotate = -360f
            }
            5 -> {
                x0 = -wi / 2
                y0 = hi
                rotate = 720f * 2
            }
            6 -> {
                x0 = wi / 2
                y0 = hi
                rotate = -720f * 2
            }
        }
        return arrayOf(x0, y0, rotate)
    }

    fun ttMoveAndRotate(kind: Int, ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)
        if (kind == 30) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .start()
        }
        if (kind == 31) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .thenAnimate(textView)
                .repeatCount(1)
                .swing()
                .start()
        }
        if (kind == 32) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .thenAnimate(textView)
                .flipHorizontal()
                .repeatCount(2)
                .start()
        }
    }

    fun scale10(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {
            arr[index]?.let { ttScale1(it, dur) }
        }
    }


    fun scale11(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .duration(dur)
            .start()
    }


    fun scale12(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .onStop {
                ViewAnimator
                    .animate(arr[5])
                    .scale(1f, 0f, 1f)
                    .repeatCount(1)
                    .duration(1000)
                    .start()
            }
            .duration(dur)
            .start()
    }

    fun scale13(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .onStop {
                ViewAnimator
                    .animate(arr[5])
                    .swing()
                    .repeatCount(1)
                    .duration(1000)
                    .start()
            }
            .duration(dur)
            .start()
    }


    fun ttMoveAndRotateAndSwing1(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)

        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .rotation(arr[2])
            .duration(dur)
            .swing()
            .start()
    }


    fun move_scale(selector: Int, arr: ArrayList<TextView?>, dur: Long) {

        if (selector == 0) {
            for (index in 0 until arr.size) {
                arr[index]?.let { item_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 0) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 2) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 1) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 2) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 3) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 4) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
    }


    /*   fun scale_swing( selector:Int,talker: Talker,arr: ArrayList<TextView?>, dur: Long) {

           if (selector==0) {
               for (index in 0 until arr.size) {
                   arr[index]?.let { item_scale(index + 1, it, dur) }
               }
           }

           if (selector==1) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                       if (index > 0) {
                           item_scale(index + 1, it, dur)
                       } else {
                           item_scale_swing(index + 1, it, dur)
                       }
                   }
               }
           }


           if (selector==2) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                       if (index == talker.lines-1) {
                           item_scale_swing(index + 1, it, dur)
                       } else {
                           item_scale(index + 1, it, dur)

                       }
                   }
               }
           }
           if (selector==3) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                           item_scale_swing(index + 1, it, dur)
                   }
               }
           }
           *//*     if (selector==4) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 3) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }
                 if (selector==5) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 4) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }*//*

    }*/


    fun moveAndRotate3(kind: Int, arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {
            arr[index]?.let { ttMoveAndRotate(kind, index + 1, it, dur) }
        }
    }


}


/* fun moveAndRotate31(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(1,index + 1, it, dur) }

        }
    }
    fun moveAndRotate32(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(2,index + 1, it, dur) }

        }*/


/*fun move1(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1, -wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
    ttMove1(
        textView2, wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
}

fun move1g(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
    ttMove1(
        textView2,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
}

fun move1god(textView1: TextView, textView2: TextView, dur: Long) {
    ttMove1(textView1, wi / 2, 0f, hi, 0f, 0f, 1f, dur)
    ttMove1(textView2, -wi / 2, 0f, hi, 0f, 0f, 1f, dur)
}
*/
