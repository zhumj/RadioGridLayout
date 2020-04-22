package com.example.multilineradiogroupdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rgTest.setOnCheckedChangeListener(object: MultiLineRadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: MultiLineRadioGroup?, checkedId: Int) {
                val message = when (checkedId) {
                    R.id.rbTest1 -> "01"
                    R.id.rbTest2 -> "02"
                    R.id.rbTest3 -> "03"
                    R.id.rbTest4 -> "04"
                    R.id.rbTest5 -> "05"
                    R.id.rbTest6 -> "06"
                    R.id.rbTest7 -> "07"
                    R.id.rbTest8 -> "08"
                    R.id.rbTest9 -> "09"
                    R.id.rbTest10 -> "10"
                    R.id.rbTest11 -> "11"
                    R.id.rbTest12 -> "12"
                    R.id.rbTest13 -> "13"
                    else -> "14"
                }
                showToast("选中$message")
            }
        })
    }

    private var toast: Toast? = null
    private fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
