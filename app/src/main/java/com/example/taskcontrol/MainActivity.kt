package com.example.taskcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskcontrol.Control.DynamicAdapter
import com.example.taskcontrol.Core.RealmsData
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.hide()
        Realm.init(this)
        val realms = RealmsData()
        realms.ConfigureRealm()

        println("Empezo la actividad")

        val list : List<String> = listOf("qwdqwe", "qweqwew")
        val adapter = DynamicAdapter(
            layout = R.layout.proyect_item,
            entries = list,
            action = fun(viewHolder, view, item, position) {
                println("Se cargan los items")
            })

        listProyectRecycler.adapter = adapter


//        insertarProject.setOnClickListener {
//            realms.createProject("proyecto creado", " Descripcion")
//        }
//
//        ver.setOnClickListener {
//            realms.setupControls()
//        }



    }
}
