package com.example.taskcontrol.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcontrol.Control.DynamicAdapter
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Helpers.resource
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.R
import com.example.taskcontrol.TasksActivity
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.fragment_tasks.view.titleTaskTab
import kotlinx.android.synthetic.main.task_detail_item.view.*
import java.lang.Exception
import java.util.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private var currentList : List<Task>? = null
    private lateinit var pageViewModel: PageViewModel
    private var currentState = String()
    private var isFilterList = false
    private var filterList : List<Task>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        addObservers(root)

//        root.recyclerTasks.
        //val textView: TextView = root.findViewById(R.id.section_label)
//        pageViewModel.text.observe(this, Observer<String> {
//            textView.text = it
//        })
        if(currentList != null){
            pageViewModel.ordenarCategorias(currentList!!)
        }

        return root
    }

    private fun addObservers(root: View?) {
        when(currentState){
            Constantes.TODO -> {
                pageViewModel.listToDo.observe(this, Observer { list ->
                    titleTaskTab.text = resources.getString(R.string.tab_to_do)
                    drawToDo(root, list)
                })
            }
            Constantes.DOING -> {
                pageViewModel.listDoing.observe(this, Observer { list ->
                    titleTaskTab.text = resources.getString(R.string.tab_doing)
                    drawToDo(root, list)
                })
            }
            Constantes.DONE -> {
                pageViewModel.listDone.observe(this, Observer { list ->
                    titleTaskTab.text = resources.getString(R.string.tab_done)
                    drawToDo(root, list)
                })
            }
        }
    }


    private fun drawToDo(root: View?, list : List<Task>) {
        filterList = list
        isFilterList = true
        configRecycler(root)
        val adapter = DynamicAdapter(
            layout = R.layout.task_detail_item,
            entries = list,
            action = fun(viewHolder, view, item, position) {
                try{
                    view.textViewItem.text = item.description
                    viewHolder.view.setOnClickListener {
                    }
                }catch (e: Exception){
                    println(e)
                }
            })
        root?.recyclerTasks?.adapter = adapter
    }

    private fun configRecycler(root: View?) {
        val itemhelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.RIGHT){
                    if(isFilterList){
                        pageViewModel.updateStateTask(filterList!![viewHolder.adapterPosition])
                        (activity as TasksActivity).reload()
                    }
                }else if(direction == ItemTouchHelper.LEFT){
                    if(isFilterList){
                        pageViewModel.degradeStateTask(filterList!![viewHolder.adapterPosition])
                        (activity as TasksActivity).reload()
                    }
                }
            }
        }
        val item = ItemTouchHelper(itemhelper)
        item.attachToRecyclerView(root?.recyclerTasks)
        root?.recyclerTasks?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        root?.recyclerTasks?.setHasFixedSize(true)
    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(list: List<Task>, state: String): PlaceholderFragment {
            var fragment = PlaceholderFragment()
            fragment.currentState = state
            fragment.currentList = list
            return fragment
//            return PlaceholderFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
        }
    }
}