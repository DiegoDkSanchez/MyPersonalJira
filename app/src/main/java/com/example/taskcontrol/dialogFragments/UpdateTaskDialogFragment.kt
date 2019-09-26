package com.example.taskcontrol.dialogFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.R
import kotlinx.android.synthetic.main.update_task_dialog.*

class UpdateTaskDialogFragment(task: Task): DialogFragment() {
    private val task = task
    private val style = STYLE_NO_TITLE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(R.layout.update_task_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drawPorcent.progress = task.porcent
        textPorcent.text = task.porcent.toString()+"%"
        seekbarPercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, porcent: Int, p2: Boolean) {
                drawPorcent.progress = porcent
                textPorcent.text = porcent.toString()+"%"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(style, R.style.dialog)
    }
}