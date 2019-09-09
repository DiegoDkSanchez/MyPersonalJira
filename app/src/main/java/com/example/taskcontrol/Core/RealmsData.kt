package com.example.taskcontrol.Core

import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.Model.Task
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*
import kotlin.collections.ArrayList
import io.realm.RealmResults



class RealmsData {

    companion object {
        fun newInstance() = RealmsData
    }

    private var realmDB: Realm = BaseApp.realm()

    fun configureRealm() {

        val mRealmConfiguration = RealmConfiguration.Builder()
            .name("tasks_control.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        realmDB = Realm.getInstance(mRealmConfiguration)
        Realm.setDefaultConfiguration(mRealmConfiguration)
    }


    fun getAllProjects(): List<Project> {
        var todos = realmDB!!.where(Project::class.java).findAll()
        if(todos == null){
            return ArrayList()
        }
        return todos
    }

    fun getAllTaskToProject(idProject: Long): List<Task>{
        var tasks = realmDB!!.where(Task::class.java).equalTo("idProject",idProject).findAll()
        if(tasks == null){
            return ArrayList()
        }
        return tasks
    }


    fun getProject(id : Long): Project?{
        return realmDB!!.where(Project::class.java).equalTo("id", id).findAll()[0]
    }

    fun getTask(id: Long): Task?{
        return realmDB!!.where(Task::class.java).equalTo("id", id).findFirst()
    }


    fun insertOrUpdateProject(project: Project){
        if(project.id == 0L ){
            val generateId = System.currentTimeMillis()
            project.id = generateId
            project.dateInit = Calendar.getInstance().time
            project.active = true
        }
        realmDB!!.beginTransaction()
        realmDB!!.insertOrUpdate(project)
        realmDB!!.commitTransaction()
    }

    fun insertOrUpdateTask(task: Task){
        if(task.id == 0L){
            val generateId = System.currentTimeMillis()
            task.id = generateId
            task.dateInit = Calendar.getInstance().time
        }
        realmDB!!.beginTransaction()
        realmDB!!.insertOrUpdate(task)
        realmDB!!.commitTransaction()
    }

    fun updateStateTask(task : Task){
        realmDB!!.beginTransaction()
        when(task.state){
            Constantes.TODO -> task.state = Constantes.DOING
            Constantes.DOING -> task.state = Constantes.DONE
        }
        realmDB!!.insertOrUpdate(task)
        realmDB!!.commitTransaction()
    }

    fun degradeStateTask(task: Task){
        realmDB!!.beginTransaction()
        var isDeleted = false
        when(task.state){
            Constantes.TODO -> {
                val allData = realmDB!!.where(Task::class.java).equalTo("id", task.id).findFirst()
                allData?.deleteFromRealm()
                isDeleted = true
                realmDB!!.commitTransaction()
            }
            Constantes.DOING -> task.state = Constantes.TODO
            Constantes.DONE -> task.state = Constantes.DOING
        }
        if(!isDeleted){
            realmDB!!.insertOrUpdate(task)
            realmDB!!.commitTransaction()
        }
    }

    fun deleteTask(task: Task){
        realmDB!!.beginTransaction()
        val allData = realmDB!!.where(Task::class.java).equalTo("id", task.id).findFirst()
        allData?.deleteFromRealm()
        realmDB!!.commitTransaction()
    }

    fun deleteProject(project: Project){
        realmDB!!.beginTransaction()
        val allData = realmDB!!.where(Project::class.java).equalTo("id", project.id).findFirst()
        allData?.deleteFromRealm()
        realmDB!!.commitTransaction()
    }



}