package com.example.yhaa17

import java.io.Serializable

class StyleObject(
    val num:Int=0,
    val colorBack: String = "none",
    val colorText: String = "#ffffff",
    val sizeText: Float =20f,
    val styleText: Int = 0,
    val paddingLeft:Int=0,
    val paddingTop:Int=0,
    val paddingRight:Int=0,
    val paddingButton:Int=0

): Serializable
