package com.example.taskcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.RealmsData

class MainActivityViewModel: ViewModel() {
    var listProjects : MutableLiveData<List<Project>> = MutableLiveData()
    private val realms = RealmsData()

    fun loadProjects() {
        listProjects.postValue(realms.getAllProjects())
    }

}