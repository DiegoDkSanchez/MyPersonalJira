package com.example.taskcontrol.AddTaskWizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.taskcontrol.Control.WizardAddTaskAdapter
import com.example.taskcontrol.R
import com.example.taskcontrol.TasksActivity
import kotlinx.android.synthetic.main.dialog_new_task.*
import kotlinx.android.synthetic.main.dialog_wizard_new_task.*

class AddTaskDialogFragment(idProject: Long) : DialogFragment() {

    private val idProject = idProject
    private val style = STYLE_NO_TITLE
    private var currentPosition = 0
    private lateinit var viewModel: AddTaskDialogViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPagerDialog.adapter = WizardAddTaskAdapter(childFragmentManager)
        configBackgroundAnimation()
        addTaskDialogFragment.setOnClickListener {
            var hours : Int? = null
            if(AddJobsHoursFragment.addHours){
                hours = AddJobsHoursFragment.hours
            }
            val success = validateTaskToSave(
                idProject,
                AddNameFragment.nameTask,
                hours
            )
            if(success){
                (activity as TasksActivity).reload()
                this.dismiss()
            }
        }
    }

    private fun configBackgroundAnimation() {
        backgroundCube.setMinAndMaxProgress(0.0f,0.5f)
        var finishFirstAnimation = false
        backgroundCube.playAnimation()
        viewPagerDialog.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentPosition == 0){
                    if( backgroundCube.progress >= 0.49f) {
                        finishFirstAnimation = true
                        backgroundCube.setMinAndMaxProgress(0.5f, 1.0f)
                        backgroundCube.progress = 0.5f + (positionOffset/2)
                        backgroundCube.resumeAnimation()
                        backgroundCube.pauseAnimation()
                        println(positionOffset)
                    }
                }else if(currentPosition == 1){
                    backgroundCube.progress = 0.5f + (positionOffset/2)
                    backgroundCube.resumeAnimation()
                    backgroundCube.pauseAnimation()
                    println("estas aqui prro")
                }
                if( position == 1 && finishFirstAnimation ){
                    currentPosition = 1
                    backgroundCube.progress = 1 - (positionOffset/2)
                    backgroundCube.resumeAnimation()
                    backgroundCube.pauseAnimation()
                }
            }
            override fun onPageSelected(position: Int) {
                selector.setPosition(position)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddTaskDialogViewModel::class.java)
        setStyle(style, R.style.dialog)
    }

    private fun validateTaskToSave(idProject: Long, name: String, timeExpected : Int?): Boolean {
        if(name.trim() != ""){
            viewModel.addTask(idProject, name, timeExpected)
            return true
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.dialog_wizard_new_task, container, false)
        return view
    }

}