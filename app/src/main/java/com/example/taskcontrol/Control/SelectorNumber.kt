package com.example.taskcontrol.Control

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.taskcontrol.R
import kotlinx.android.synthetic.main.number_selector.view.*
import java.lang.reflect.Constructor

class SelectorNumber: LinearLayout {

    var value : Int = 0
    get() = this.number.text.toString().toInt()

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        val view = LayoutInflater.from(context).inflate(R.layout.number_selector, this, true)
        view.dismiss.setOnClickListener {
            if(value>0) view.number.text = (value - 1 ).toString()
        }
        view.add.setOnClickListener {
            view.number.text = (value + 1).toString()
        }
    }

}