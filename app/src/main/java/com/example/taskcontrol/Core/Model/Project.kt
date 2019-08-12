package com.example.taskcontrol.Core.Model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Project (
    @PrimaryKey var id : Long = 0,
    var name : String? = null,
    var description : String? = null,
    var active : Boolean? = null,
    var dateInit : Date? = null,
    var dateFinish : Date? = null,
    var imagePath : String? = null,
    var tasks : RealmList<Task>? = null
) : RealmObject()