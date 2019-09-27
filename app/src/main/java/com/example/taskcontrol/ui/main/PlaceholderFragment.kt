package com.example.taskcontrol.ui.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcontrol.AddTaskWizard.AddTaskDialogFragment
import com.example.taskcontrol.Control.DynamicAdapter
import com.example.taskcontrol.Core.BaseView
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.R
import com.example.taskcontrol.TasksActivity
import com.example.taskcontrol.dialogFragments.UpdateTaskDialogFragment
import kotlinx.android.synthetic.main.dialog_delete_task.view.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.task_detail_done_item.view.*
import kotlinx.android.synthetic.main.task_detail_item.view.*
import kotlinx.android.synthetic.main.task_detail_item.view.cubeAnimation
import kotlinx.android.synthetic.main.task_detail_item.view.itemTimeExpected
import kotlinx.android.synthetic.main.task_detail_porcent_item.view.*
import kotlinx.android.synthetic.main.update_task_dialog.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.zip.Inflater

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
                    drawDoing(root, list)
                })
            }
            Constantes.DONE -> {
                pageViewModel.listDone.observe(this, Observer { list ->
                    titleTaskTab.text = resources.getString(R.string.tab_done)
                    drawDone(root, list)
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
                    val currentShowPosition = position+1
                    if(currentShowPosition < 10){
                        view.taskCounter.text = "0$currentShowPosition"
                    }else{
                        view.taskCounter.text = currentShowPosition.toString()
                    }
                    if(item.dateExpected != null){
                        view.itemTimeExpected.text =
                            getString(R.string.time_expected) + " " +
                            item.dateExpected.toString() + "h"
                    }
                    view.setOnTouchListener { view, motionEvent ->
                        if(motionEvent.action == MotionEvent.ACTION_DOWN){
                            view.cubeAnimation.speed = 1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                            //cuando le pusheas
                        }else if(motionEvent.action == MotionEvent.ACTION_UP){
                            view.cubeAnimation.speed = -1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                            //view.cubeAnimation.playAnimation()
                        }else if(motionEvent.action == MotionEvent.ACTION_CANCEL){
                            view.cubeAnimation.speed = -1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                        }
                        return@setOnTouchListener true
                    }
                }catch (e: Exception){
                    println(e)
                }
            })
        root?.recyclerTasks?.adapter = adapter
    }
    private fun drawDoing(root: View?, list : List<Task>) {
        filterList = list
        isFilterList = true
        configRecycler(root)
        val adapter = DynamicAdapter(
            layout = R.layout.task_detail_porcent_item,
            entries = list,
            action = fun(viewHolder, viewDoing, item, position) {
                try{
                    viewDoing.circlePorcentItem.progress = item.porcent
                    viewDoing.textPorcentItem.text = item.porcent.toString() + "%"
                    viewDoing.titleTaskItem.text = item.description
                    if(item.commentary!= null && item.commentary.toString().trim() != ""){
                        viewDoing.comentary.text = item.commentary
                    }
                    if(item.dateExpected != null){
                        viewDoing.timeExpectedItem.text = getString(R.string.time_expected) + " " + item.dateExpected.toString() + "h"
                    }
                    val formatDate = SimpleDateFormat("dd/MMMM/yyyy")
                    viewDoing.dateInitItem.text = getString(R.string.date_init) + "\n" + formatDate.format(item.dateInit)
                    viewDoing.cubeAnimationPorcentItem.setMinAndMaxProgress(0.0f, 0.5f)
                    viewDoing.editButton.setOnClickListener {
                        val fragmentTransaction = childFragmentManager.beginTransaction()
                        val dialogFragment = UpdateTaskDialogFragment(item)
                        dialogFragment.show(fragmentTransaction,"dialog")
                    }
                    viewDoing.setOnTouchListener { view, motionEvent ->
                        if(motionEvent.action == MotionEvent.ACTION_DOWN){
                            view.cubeAnimationPorcentItem.speed = 1f
                            if(!view.cubeAnimationPorcentItem.isAnimating){
                                view.cubeAnimationPorcentItem.playAnimation()
                            }
                            //cuando le pusheas
                        }else if(motionEvent.action == MotionEvent.ACTION_UP){
                            view.cubeAnimationPorcentItem.speed = -1f
                            if(!view.cubeAnimationPorcentItem.isAnimating){
                                view.cubeAnimationPorcentItem.playAnimation()
                            }
                            //view.cubeAnimation.playAnimation()
                        }else if(motionEvent.action == MotionEvent.ACTION_CANCEL){
                            view.cubeAnimationPorcentItem.speed = -1f
                            if(!view.cubeAnimationPorcentItem.isAnimating){
                                view.cubeAnimationPorcentItem.playAnimation()
                            }
                        }
                        return@setOnTouchListener true
                    }
                }catch (e: Exception){
                    println(e)
                }
            })
        root?.recyclerTasks?.adapter = adapter
    }
    private fun drawDone(root: View?, list : List<Task>) {
        filterList = list
        isFilterList = true
        configRecycler(root)
        val adapter = DynamicAdapter(
            layout = R.layout.task_detail_done_item,
            entries = list,
            action = fun(viewHolder, viewDone, item, position) {
                try{
                    viewDone.titleDoneItem.text = item.description

                    val formatDate = SimpleDateFormat("dd/MMMM/yyyy")
                    viewDone.dateInitItem.text = getString(R.string.date_finish) + "\n" + formatDate.format(item.dateFinish)
                    viewDone.setOnTouchListener { view, motionEvent ->
                        if(motionEvent.action == MotionEvent.ACTION_DOWN){
                            view.cubeAnimation.speed = 1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                            //cuando le pusheas
                        }else if(motionEvent.action == MotionEvent.ACTION_UP){
                            view.cubeAnimation.speed = -1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                            //view.cubeAnimation.playAnimation()
                        }else if(motionEvent.action == MotionEvent.ACTION_CANCEL){
                            view.cubeAnimation.speed = -1f
                            if(!view.cubeAnimation.isAnimating){
                                view.cubeAnimation.playAnimation()
                            }
                        }
                        return@setOnTouchListener true
                    }
                }catch (e: Exception){
                    println(e)
                }
            })
        root?.recyclerTasks?.adapter = adapter
    }
    private fun configRecycler(root: View?) {
        val itemhelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val background: ColorDrawable
                val itemView = viewHolder.itemView
                // Right swipe.
                if (dX > 0) {
                    background = ColorDrawable(resources.getColor(R.color.colorAccent))
                    background.setBounds(0, itemView.getTop(), (itemView.getLeft() + dX).toInt(), itemView.getBottom())
                }
                // Left swipe
                else {
                    if(currentState == Constantes.TODO){
                        background = ColorDrawable(resources.getColor(R.color.red))
                    }else {
                        background = ColorDrawable(resources.getColor(R.color.colorPrimaryDark))
                    }
                    background.setBounds((itemView.right  ) + dX.toInt(), itemView.getTop(), itemView.right, itemView.getBottom())
                }
                background.draw(c)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.RIGHT){
                    //viewHolder.itemView.x
                    if(isFilterList){
                        pageViewModel.updateStateTask(filterList!![viewHolder.adapterPosition])
                        when(currentState){
                            Constantes.TODO -> {}
                            Constantes.DOING -> {}
                        }
                        (activity as TasksActivity).reload()
                    }
                }else if(direction == ItemTouchHelper.LEFT){
                    if(isFilterList){
                        if(currentState == Constantes.TODO){
                            val mDialogView = LayoutInflater.from(activity?.applicationContext).inflate(R.layout.dialog_delete_task, null)
                            val mBuilder = activity?.let { AlertDialog.Builder(it).setView(mDialogView) }
                            val  mAlertDialog = mBuilder?.show()
                            mAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            mAlertDialog?.setOnDismissListener {
                                (activity as TasksActivity).reload()
                            }
                            mDialogView.deleteTaskButton.setOnClickListener {
                                pageViewModel.degradeStateTask(filterList!![viewHolder.adapterPosition])
                                mAlertDialog?.dismiss()
                            }
                        }else{
                            pageViewModel.degradeStateTask(filterList!![viewHolder.adapterPosition])
                            (activity as TasksActivity).reload()
                        }
                    }
                }
            }
        }
        val item = ItemTouchHelper(itemhelper)
        item.attachToRecyclerView(root?.recyclerTasks)
        val animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        root?.recyclerTasks?.layoutAnimation = animationController
        root?.recyclerTasks?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        root?.recyclerTasks?.setHasFixedSize(true)
    }
    companion object {
        @JvmStatic
        fun newInstance(list: List<Task>, state: String): PlaceholderFragment {
            var fragment = PlaceholderFragment()
            fragment.currentState = state
            fragment.currentList = list
            return fragment
        }
    }
}