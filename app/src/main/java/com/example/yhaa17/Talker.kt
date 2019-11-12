package com.example.yhaa17

import java.io.Serializable

data class Talker(
    var whoSpeake: String = "man",
    var taking: String = "tadam",
    var takingArray: ArrayList<String> = arrayListOf(),
    var styleNum: Int = 0,
    var animNum: Int = 0,
    var dur: Long = 1000,
    var textSize: Float = 28f,
    var colorBack: String = "none",
    var backExist:Boolean=true,
    var colorText: String = "#ffffff",
    var numTalker: Int = 0,
    var radius: Float = 30f,
    var padding:ArrayList<Int> = arrayListOf(10,0,10,0),
    var borderColor: String = "#000000",
    var borderWidth: Int = 20,
    var swingRepeat:Int=0
//left,up,right,down

) : Serializable