package com.example.taskcontrol.Core.Model

import com.example.taskcontrol.Core.TaskState
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.sql.Date

open class Task() : RealmObject() {
    @PrimaryKey var id : Long = 0
    var description : String? = null
    var commentary : String? = null
    var porcent : Int = 0
    var state : TaskState? = null
    var dateInit : Date? = null
    var dateFinish : Date? = null
}