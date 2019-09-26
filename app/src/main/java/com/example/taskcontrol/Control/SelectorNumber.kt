package com.example.taskcontrol.Control

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.taskcontrol.AddTaskWizard.AddJobsHoursFragment
import com.example.taskcontrol.R
import kotlinx.android.synthetic.main.number_selector.view.*

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
            var willSet : String
            if(value - 1 < 10 ) willSet = "0" + (value - 1 ).toString()
            else willSet = (value - 1 ).toString()
            if(value>1){
                view.number.text = willSet
                AddJobsHoursFragment.hours = willSet.toInt()
            }
        }
        view.add.setOnClickListener {
            var willSet: String
            if(value + 1 < 10 ) willSet = "0" + (value + 1 ).toString()
            else willSet = (value + 1 ).toString()
            view.number.text = willSet
            AddJobsHoursFragment.hours = willSet.toInt()
        }
    }


}