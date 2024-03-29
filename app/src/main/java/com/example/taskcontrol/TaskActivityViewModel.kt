package com.example.taskcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData

class TaskActivityViewModel : ViewModel() {

    var listTasks : MutableLiveData<List<Task>> = MutableLiveData()
    var project : MutableLiveData<Project> = MutableLiveData()
    private val realms = RealmsData()
    init {
        realms.configureRealm()
    }
    fun loadTasks(idProject: Long){
        listTasks.postValue(realms.getAllTaskToProject(idProject))
    }
    fun loadProject(idProject: Long){
        project.postValue(realms.getProject(idProject))
    }

}