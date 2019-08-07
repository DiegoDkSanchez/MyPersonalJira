package com.example.taskcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskcontrol.Core.RealmsData
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)

        val realms = RealmsData()
        realms.ConfigureRealm()


        insertarProject.setOnClickListener {
            realms.setupControls()
        }
    }
}
