package com.example.taskcontrol.AddTaskWizard

import androidx.lifecycle.ViewModel
import com.example.taskcontrol.Core.Constantes
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData

class AddTaskDialogViewModel: ViewModel() {

    private val realms = RealmsData()

    fun addTask(idProject: Long, name: String, timeExpected : Int?){
        realms.insertOrUpdateTask(
            Task(
                description = name,
                state = Constantes.TODO,
                idProject = idProject,
                dateExpected = timeExpected
            )
        )
    }
}