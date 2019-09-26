package com.example.taskcontrol.Control

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.taskcontrol.R

class TabCircleSelector: LinearLayout {

    private var viewTab : View? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
            viewTab =  LayoutInflater.from(context).inflate(R.layout.tab_circle, this, true)
    }

    fun setPosition(position : Int){
        var positionOne = viewTab?.findViewById(R.id.positionOne) as ImageView
        var positionTwo = viewTab?.findViewById(R.id.positionTwo) as ImageView
        when(position){
            0 -> {
                positionOne.setImageResource(R.drawable.tab_indicator_selected)
                positionTwo.setImageResource(R.drawable.tab_indicator_default)
            }
            1 -> {
                positionOne.setImageResource(R.drawable.tab_indicator_default)
                positionTwo.setImageResource(R.drawable.tab_indicator_selected)
            }
        }
    }

}
