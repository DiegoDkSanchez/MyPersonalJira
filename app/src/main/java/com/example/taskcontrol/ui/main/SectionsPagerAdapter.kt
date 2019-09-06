package com.example.taskcontrol.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.R

private val TAB_TITLES = arrayOf(
    R.string.tab_to_do,
    R.string.tab_doing,
    R.string.tab_done
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, listTasks: List<Task>) :
    FragmentPagerAdapter(fm) {

    private val list = listTasks

    override fun getItem(position: Int): Fragment {
        var state = String()
        when(position){
            0 -> state = Constantes.TODO
            1 -> state = Constantes.DOING
            2 -> state = Constantes.DONE
        }
        return  PlaceholderFragment.newInstance(list, state)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.count()
    }
}