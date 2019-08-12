package com.example.taskcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskcontrol.Control.DynamicAdapter
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.RealmsData
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.proyect_item.view.*
import java.util.*
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.proyect_item.*
import kotlin.collections.ArrayList
import com.example.taskcontrol.AddProject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()
        Realm.init(this)


        val list = realms.getAllProjects()

        val adapter = DynamicAdapter(
            layout = R.layout.proyect_item,
            entries = addItemNewProject(list),
            action = fun(viewHolder, view, item, position) {
                if(item.id.toInt() == 0){
                    view.iconImageView.setImageResource(R.drawable.ic_add_project)
                }
                view.itemProjectTitle.text = item.name
                view.itemProjectDescription.text = item.description

                viewHolder.view.setOnClickListener{
                    if(item.id.toInt() == 0){
                        val activityOption : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, layoutMutable, "layoutMutableTransition")
                        val intent : Intent = Intent(this, com.example.taskcontrol.AddProject::class.java)
                        startActivity(intent, activityOption.toBundle())
                    }
                }
            })

        //this.listProyectRecycler.configureRecycler(true)
        listProyectRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listProyectRecycler.setHasFixedSize(true)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(listProyectRecycler)
        listProyectRecycler.adapter = adapter


//        insertarProject.setOnClickListener {
//            realms.createProject("proyecto creado", " Descripcion")
//        }
//
//        ver.setOnClickListener {
//            realms.setupControls()
//        }



    }

    private fun addItemNewProject(list: List<Project>): List<Project> {
        var newList : ArrayList<Project> = ArrayList()
        list.forEach{ project ->
            newList.add(project)
        }
        newList.add(Project(0,getString(R.string.add_new_project),getString(R.string.description_new_project), null, null, null))
        return newList
    }
}
