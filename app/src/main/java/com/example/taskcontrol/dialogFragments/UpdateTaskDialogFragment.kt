package com.example.taskcontrol.dialogFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.R
import com.example.taskcontrol.TasksActivity
import kotlinx.android.synthetic.main.update_task_dialog.*
import java.util.*

class UpdateTaskDialogFragment(task: Task): DialogFragment() {
    private val task = task
    private val style = STYLE_NO_TITLE
    private lateinit var viewModel: UpdateTaskDialogViewModel

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
        seekbarPercent.progress = task.porcent
        titleTaskEditDialog.setText(task.description)
        if(task.commentary!= null && task.commentary.toString().trim() != ""){
            commentEditDialog.setText(task.commentary)
        }
        seekbarPercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, porcent: Int, p2: Boolean) {
                drawPorcent.progress = porcent
                textPorcent.text = porcent.toString()+"%"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        updateTask.setOnClickListener {
            var dateFinish : Date? = null
            var updateState = task.state
            if(drawPorcent.progress == 100){
                dateFinish = Calendar.getInstance().time
                updateState = Constantes.DONE
            }
            val updateTask = Task(
                id = task.id,
                description = titleTaskEditDialog.text.toString(),
                commentary = commentEditDialog.text.toString(),
                porcent = drawPorcent.progress,
                state = updateState,
                dateInit = task.dateInit,
                dateExpected = task.dateExpected,
                dateFinish = dateFinish,
                idProject = task.idProject
            )
            viewModel.updateTask(updateTask)
            TasksActivity.currentView = 1
            (activity as TasksActivity).reload()
            this.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(style, R.style.dialog)
        viewModel = ViewModelProviders.of(this).get(UpdateTaskDialogViewModel::class.java)
    }
}