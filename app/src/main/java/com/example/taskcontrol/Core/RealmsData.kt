package com.example.taskcontrol.Core

import com.example.taskcontrol.Core.Model.Project
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*
import kotlin.collections.ArrayList

class RealmsData {

    companion object {
        fun newInstance() = RealmsData
    }

    private var realmDB: Realm? = null

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

    fun getProject(id : Long): Project?{
        return realmDB!!.where(Project::class.java).equalTo("id",id).findFirst()
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



}