package com.example.caculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var maintext : TextView
    private lateinit var prevtext : TextView
    private lateinit var operation : TextView

    private fun refresh() {
        prevtext.setText("")
        operation.setText("")
        maintext.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maintext = findViewById(R.id.display)

        prevtext = findViewById(R.id.prevdisplay)

        operation = findViewById(R.id.operation)

        refresh()
    }

    private fun standard(s: String) : String {
        var res : String
        res = s.dropLastWhile { it == '0' }
        res = res.dropLastWhile { it == '.' }
        return res
    }

    fun touchDigit(view: View) {
        if ((view as Button).text == ".")
            if (maintext.text.toString() == "" || maintext.text.toString().contains('.'))
                return
        if (maintext.text.toString() == "0" && (view as Button).text != ".")
            maintext.setText("")
        maintext.append((view as Button).text)
    }

    fun onBackspace(view: View) {
        maintext.setText(maintext.text.dropLast(1))
        if (maintext.text.toString() == "-0")
            maintext.setText("0")
    }

    fun onClear(view: View) {
        refresh()
    }

    fun onEqual(view: View) : Boolean {
        if (maintext.text.toString() == "")
            return prevtext.text.toString() == "" || prevtext.text.toString().toFloatOrNull() == null
        if (prevtext.text.toString() == "" || operation.text.toString() == "") {
            prevtext.setText(standard(maintext.text.toString()))
            maintext.setText("")
            return prevtext.text.toString() == "" || prevtext.text.toString().toFloatOrNull() == null
        }
        val a : Float = prevtext.text.toString().toFloat()
        val b : Float = maintext.text.toString().toFloat()
        var res : String = ""
        val oper : String = operation.text.toString()
        if (oper == getString(R.string.mul)) {
            res = standard((a * b).toString())
        }
        if (oper == getString(R.string.div)) {
            if (b == 0.0f)
                res = "Không thể chia được cho 0"
            else
                res = standard((a / b).toString())
        }
        if (oper == getString(R.string.add)) {
            res = standard((a + b).toString())
        }
        if (oper == getString(R.string.sub)) {
            res = standard((a - b).toString())
        }
        refresh()
        prevtext.setText(res)
        return prevtext.text.toString() == "" || prevtext.text.toString().toFloatOrNull() == null
    }

    fun onBasicOperation(view: View) {
        if (onEqual(view))
            return
        operation.setText((view as Button).text)
    }

    fun onSqrt(view: View) {
        if (onEqual(view))
            return
        val a : Float = prevtext.text.toString().toFloat()
        if (a < 0)
            prevtext.setText("Không thể lấy căn bậc hai của số "+prevtext.text.toString())
        else
            prevtext.setText(standard(sqrt(a).toString()))
    }

    fun onSqr(view: View) {
        if (onEqual(view))
            return
        val a : Float = prevtext.text.toString().toFloat()
        prevtext.setText(standard((a*a).toString()))
    }

    fun onNegative(view: View) {
        if (maintext.text.toString() == "" || maintext.text.toString().toFloat() == 0.0f)
            return
        maintext.setText(standard((-maintext.text.toString().toFloat()).toString()))
    }
}