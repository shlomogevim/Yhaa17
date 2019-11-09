package com.example.yhaa17

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_select_color.*
import kotlinx.android.synthetic.main.picker_layout.*

class SelectColor : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_color)
        btnColorPicker.setOnClickListener {
            colorSelector.visibility = View.VISIBLE
        }

        btnColorSelected.setOnClickListener {
            colorSelector.visibility = View.VISIBLE
        }

        strColor.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 6) {
                    colorA.progress = 255
                    colorR.progress = Integer.parseInt(s.substring(0..1), 16)
                    colorG.progress = Integer.parseInt(s.substring(2..3), 16)
                    colorB.progress = Integer.parseInt(s.substring(4..5), 16)
                } else if (s.length == 8) {
                    colorA.progress = Integer.parseInt(s.substring(0..1), 16)
                    colorR.progress = Integer.parseInt(s.substring(2..3), 16)
                    colorG.progress = Integer.parseInt(s.substring(4..5), 16)
                    colorB.progress = Integer.parseInt(s.substring(6..7), 16)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        colorA.max = 255
        colorA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorR.max = 255
        colorR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorG.max = 255
        colorG.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorB.max = 255
        colorB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val colorStr = getColorString()
                strColor.setText(colorStr.replace("#", "").toUpperCase())
                btnColorPreview.setBackgroundColor(Color.parseColor(colorStr))
            }
        })

        colorCancelBtn.setOnClickListener {
            colorSelector.visibility = View.GONE
        }

        colorOkBtn.setOnClickListener {
            val color: String = getColorString()
            btnColorSelected.setBackgroundColor(Color.parseColor(color))
            colorSelector.visibility = View.GONE
            val intent=Intent()
            intent.putExtra("color",color)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    fun getColorString(): String {
        var a = Integer.toHexString(((255 * colorA.progress) / colorA.max))
        if (a.length == 1) a = "0" + a
        var r = Integer.toHexString(((255 * colorR.progress) / colorR.max))
        if (r.length == 1) r = "0" + r
        var g = Integer.toHexString(((255 * colorG.progress) / colorG.max))
        if (g.length == 1) g = "0" + g
        var b = Integer.toHexString(((255 * colorB.progress) / colorB.max))
        if (b.length == 1) b = "0" + b
        return "#" + a + r + g + b
    }
}

