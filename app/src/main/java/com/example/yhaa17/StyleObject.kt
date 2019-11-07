package com.example.yhaa17

import java.io.Serializable

class StyleObject(
    val numStyleObject:Int=0,
    val colorBack: String = "none",
    val colorText: String = "#ffffff",
    val sizeText: Float =20f,
    val fontText: Int = 10
): Serializable
