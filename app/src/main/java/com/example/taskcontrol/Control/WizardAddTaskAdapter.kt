package com.example.taskcontrol.Control

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.taskcontrol.AddTaskWizard.AddJobsHoursFragment
import com.example.taskcontrol.AddTaskWizard.AddNameFragment

class WizardAddTaskAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return AddNameFragment()
            }
            1 -> {
                return AddJobsHoursFragment()
            }
        }
        return AddNameFragment()
    }

    override fun getCount(): Int {
        return 2
    }

}