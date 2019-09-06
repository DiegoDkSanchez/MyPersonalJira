package com.example.taskcontrol

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskcontrol.Control.DynamicAdapter
import com.example.taskcontrol.Core.Model.Project
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.proyect_item.view.*
import androidx.recyclerview.widget.LinearSnapHelper
import coil.ImageLoader
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.taskcontrol.Core.Constantes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.proyect_item.*
import kotlinx.android.synthetic.main.task_detail_item.view.*
import java.io.File
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private fun addObservers() {
        val projectsObserver = Observer<List<Project>> { listProjects ->
            loadAdapter(listProjects)
        }
        viewModel.listProjects.observe(this, projectsObserver)
    }

    private fun loadAdapter(list: List<Project>) {
        val adapter = DynamicAdapter(
            layout = R.layout.proyect_item,
            entries = addItemNewProject(list),
            action = fun(viewHolder, view, item, position) {
                if(item.id.toInt() == 0){
                    //view.iconImageView.setImageResource(R.drawable.ic_add_project)
                    view.dateInitTextView.visibility = View.GONE
                    view.iconoFechaInicio.visibility = View.GONE
                    view.settingButton.visibility = View.GONE
                    view.stateProject.visibility = View.GONE
                }
                view.itemProjectTitle.text = item.name
                view.itemProjectDescription.text = item.description
                val formatDate = SimpleDateFormat("dd/M/yyyy")
                var date = String()
                if(item.dateInit!= null && item.dateInit.toString() != ""){
                    date = formatDate.format(item.dateInit)
                }
                view.dateInitTextView.text = getString(R.string.date_init) + " "+ date
                if(item.imagePath != "" && item.imagePath != null){
//                    val imageLoader = ImageLoader(this){
//                        bitmapPoolPercentage(0.5)
//                        availableMemoryPercentage(0.5)
//                        crossfade(true)
//                    }
                    //val imguri = Uri.parse(item.imagePath)
                    //view.imageBackground.load(File(imguri.path))
                    //Picasso.get().load(Uri.parse(item.imagePath)).into(view.imageBackground);

                    Glide.with(this)
                        .asBitmap()
                        .load(Uri.parse(item.imagePath))
                        .apply(Constantes.CONFIG_GLIDE)
                        .into(view.imageBackground)
                    //view.imageBackground.setImageURI(Uri.parse(item.imagePath))
                }
                if(item.active!= null){
                    if(item.active!!){
                        view.stateProject.text = getString(R.string.project_active)
                    }else{
                        view.stateProject.text = getString(R.string.project_finish)
                    }
                }

                view.settingButton.setOnClickListener {
                    val activityOption : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, layoutMutable, "layoutMutableTransition")
                    val intent : Intent = Intent(this, com.example.taskcontrol.AddProject::class.java)
                    intent.putExtra("id", item.id)
                    println(item.id)
                    startActivity(intent, activityOption.toBundle())
                }

                viewHolder.view.setOnClickListener{
                    if(item.id.toInt() == 0){
                        val activityOption : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, layoutMutable, "layoutMutableTransition")
                        val intent = Intent(this, com.example.taskcontrol.AddProject::class.java)
                        startActivity(intent, activityOption.toBundle())
                    }else{
                        val intent = Intent (this, TasksActivity::class.java)
                        intent.putExtra("id", item.id)
                        startActivity(intent)
                    }
                }
            })

        //this.listProyectRecycler.configureRecycler(true)
        listProyectRecycler.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        actionBar?.hide()
        addObservers()
        viewModel.loadProjects()

        listProyectRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listProyectRecycler.setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listProyectRecycler)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProjects()
    }

    private fun addItemNewProject(list: List<Project>): List<Project> {
        var newList : ArrayList<Project> = ArrayList()
        list.forEach{ project ->
            newList.add(project)
        }
        newList.reverse()
        newList.add(Project(0,getString(R.string.add_new_project),getString(R.string.description_new_project), null, null, null))
        return newList
    }
}
