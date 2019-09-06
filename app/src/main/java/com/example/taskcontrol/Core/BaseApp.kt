package com.example.taskcontrol.Core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApp : Application() {

    companion object {

        fun realm(): Realm = Realm.getDefaultInstance()
        lateinit var instance: BaseApp private set
        fun instance(): BaseApp = instance
        fun context(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val mRealmConfiguration = RealmConfiguration.Builder()
            .name("tasks_control.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(mRealmConfiguration)
    }

}