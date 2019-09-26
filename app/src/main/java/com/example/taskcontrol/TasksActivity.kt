package com.example.taskcontrol

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.taskcontrol.AddTaskWizard.AddTaskDialogFragment
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData
import com.example.taskcontrol.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.dialog_new_task.*
import kotlinx.android.synthetic.main.dialog_new_task.dialogAddTask
import kotlinx.android.synthetic.main.dialog_wizard_new_task.*
import kotlin.collections.ArrayList

class TasksActivity : AppCompatActivity() {

    companion object{
        fun newInstance() = TasksActivity()
    }

    private lateinit var viewModel: TaskActivityViewModel

    private var idProject : Long? = null
    private var listTasks : List<Task> = ArrayList()
    private var project : Project? = null
    //To do
    private val realms = RealmsData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        viewModel = ViewModelProviders.of(this).get(TaskActivityViewModel::class.java)
        idProject = intent.getLongExtra("id", 0)

        addObservers()
        if(idProject != null){
            viewModel.loadTasks(idProject!!)
            viewModel.loadProject(idProject!!)
        }
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            //addTaskDialog(view)
            showWizzardAddTask(view)
        }
    }

    private fun showWizzardAddTask(view: View?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(idProject != null){
            val dialogFragment = AddTaskDialogFragment(idProject!!)
            dialogFragment.show(fragmentTransaction,"dialog")
        }
    }
//
//
//    private fun addTaskDialog(view: View?) {
//        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_task, null)
//        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
//        val  mAlertDialog = mBuilder.show()
//        mAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        mAlertDialog?.isDateFinish?.setOnCheckedChangeListener { compoundButton, state ->
//            if(compoundButton.isChecked){
//                mAlertDialog.numberSelector.visibility = View.VISIBLE
//            }else{
//                mAlertDialog.numberSelector.visibility = View.GONE
//            }
//        }
//        mAlertDialog.dialogAddTask.setOnClickListener {
//            var hours : Int? = null
//            if(mAlertDialog.isDateFinish.isChecked){
//                hours = mAlertDialog.numberSelector.value
//            }
//            val success = validateTaskToSave(
//                idProject!!,
//                mAlertDialog.dialogNametask.text.toString(),
//                hours
//            )
//            if(success){
//                Snackbar.make(view!!, resources.getString(R.string.message_task_created), Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//                reload()
//                mAlertDialog.dismiss()
//            }else{
//                mAlertDialog.dialogNametask.hint = getString(R.string.need_put_name)
//            }
//        }
//    }




    fun reload(){
        viewModel.loadTasks(idProject!!)
    }

    private fun addObservers() {
        viewModel.listTasks.observe(this, androidx.lifecycle.Observer {
            listTasks = it
            createTabs()
        })
        viewModel.project.observe(this, androidx.lifecycle.Observer {
            project = it
            taskToolbar.title = it.name
            taskToolbar.title
            taskToolbar.popupTheme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar
            setSupportActionBar(taskToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            Glide.with(this)
                .asBitmap()
                .load(Uri.parse(it.imagePath))
                .apply(Constantes.CONFIG_GLIDE)
                .into(projectImageHeader)
        })
    }




    private fun createTabs() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, listTasks)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}