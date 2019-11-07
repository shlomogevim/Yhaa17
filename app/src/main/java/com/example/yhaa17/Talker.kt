package com.example.yhaa17

import java.io.Serializable

class Talker(
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
    var colorBorder: String = "#000000"
//left,up,right,down

) : Serializable