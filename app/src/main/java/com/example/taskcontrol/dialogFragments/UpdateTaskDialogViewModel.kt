package com.example.taskcontrol.dialogFragments

import androidx.lifecycle.ViewModel
import com.example.taskcontrol.Core.Model.Task
import com.example.taskcontrol.Core.RealmsData
import io.realm.Realm

class UpdateTaskDialogViewModel: ViewModel() {
    private val realm = RealmsData()

    fun updateTask(task: Task){
        realm.insertOrUpdateTask(task)
    }
}