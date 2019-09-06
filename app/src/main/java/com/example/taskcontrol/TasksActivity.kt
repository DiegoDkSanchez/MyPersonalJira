package com.example.taskcontrol

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Helpers.resource
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData
import com.example.taskcontrol.ui.main.SectionsPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.dialog_new_task.*
import java.util.*
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

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_task, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val  mAlertDialog = mBuilder.show()
            mAlertDialog.dialogAddTask.setOnClickListener {
                viewModel.addTask(idProject!!,mAlertDialog.dialogNametask.text.toString())
                Snackbar.make(view, resources.getString(R.string.message_task_created), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                reload()
                mAlertDialog.dismiss()
            }
        }
    }

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
//            projectImageHeader.load(Uri.parse(it.imagePath))
    //        Picasso.with(this).load("https://i.imgur.com/H981AN7.jpg").into(project)
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