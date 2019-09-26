package com.example.taskcontrol.AddTaskWizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskcontrol.R
import kotlinx.android.synthetic.main.add_job_hours_fragment.*
import kotlinx.android.synthetic.main.dialog_new_task.*

class AddJobsHoursFragment: Fragment() {

    companion object{
        var hours = 1
        var addHours = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_job_hours_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkboxNotHours.setOnCheckedChangeListener { compoundButton, check ->
            if(check) {
                hoursSelector.visibility = View.GONE
                addHours = false
            }else{
                hoursSelector.visibility = View.VISIBLE
                addHours = true
            }
        }
    }
}