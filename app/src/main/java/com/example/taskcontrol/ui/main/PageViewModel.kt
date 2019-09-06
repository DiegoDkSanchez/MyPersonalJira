package com.example.taskcontrol.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData

class PageViewModel : ViewModel() {

    var listToDo : MutableLiveData<List<Task>> = MutableLiveData()
    var listDoing : MutableLiveData<List<Task>> = MutableLiveData()
    var listDone : MutableLiveData<List<Task>> = MutableLiveData()

    private val realms = RealmsData()


    fun ordenarCategorias(list : List<Task>){
        var listToDoCurrent : ArrayList<Task> = ArrayList()
        var listDoingCurrent : ArrayList<Task> = ArrayList()
        var listDoneCurrent : ArrayList<Task> = ArrayList()
        var listBlockingCurrent : ArrayList<Task> = ArrayList()

        list.forEach { task->
            if(task.state == Constantes.TODO){
                listToDoCurrent.add(task)
            }else if(task.state == Constantes.DOING){
                listDoingCurrent.add(task)
            }else if(task.state == Constantes.DONE){
                listDoneCurrent.add(task)
            }else if(task.state == Constantes.BLOCKING){
                listBlockingCurrent.add(task)
            }
        }
        listToDo.postValue(listToDoCurrent)
        listDoing.postValue(listDoingCurrent)
        listDone.postValue(listDoneCurrent)
    }

    fun updateStateTask(task : Task){
        realms.configureRealm()
        realms.updateStateTask(task)
    }
    fun degradeStateTask(task: Task){
        realms.configureRealm()
        realms.degradeStateTask(task)
    }
}