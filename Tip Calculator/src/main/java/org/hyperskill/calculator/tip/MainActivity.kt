package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showTextValue()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            showTextValue()
        })
    }

    private fun showTextValue() {
        if (edit_text.text.isNullOrEmpty()) {
            text_view.text = ""
        } else {
            val percentage: Double = slider.value.toDouble()
            val bill: Double = edit_text.text.toString().toDouble()
//            text_view.text = "Bill value: ${edit_text.text}, tip percentage: $percentage%"
            text_view.text = "Tip amount: %.2f".format(bill * percentage / 100)
        }
    }
}