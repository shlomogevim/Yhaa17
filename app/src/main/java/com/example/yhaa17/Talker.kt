package com.example.yhaa17
import java.io.Serializable

class Talker(
    var whoSpeake: String = "man",
    var taking: String = "tadam",
    var takingArray:ArrayList<String> = arrayListOf(),
    var styleNum: Int = 0,
    var animNum: Int = 0,
    var dur: Long = 1000,
    var textSize:Float=28f,
var num:Int=0,
var lines:Int=1
) : Serializable