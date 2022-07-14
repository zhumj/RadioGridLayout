package com.example.radiogridlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zhumj.radiogridlayout.RadioGridLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rgTest: RadioGridLayout = findViewById(R.id.rgTest)
        rgTest.setOnCheckedChangeListener(object: RadioGridLayout.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGridLayout?, checkedId: Int) {
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
