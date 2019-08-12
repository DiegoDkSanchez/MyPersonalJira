package com.example.taskcontrol.Core

import com.example.taskcontrol.Core.Model.Project
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmsData {

    companion object {
        fun newInstance() = RealmsData
    }

    private var realmDB: Realm? = null

    fun ConfigureRealm() {

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
        return todos
    }

    fun createProject(name: String, description: String) {
        realmDB!!.beginTransaction()
        val generateId = System.currentTimeMillis().toInt()
        var project = realmDB!!.createObject(Project::class.java, generateId)
        project.name = name
        project.description = description
        realmDB!!.commitTransaction()
    }

}