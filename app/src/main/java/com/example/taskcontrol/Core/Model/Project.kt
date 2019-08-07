package com.example.taskcontrol.Core.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Project (
    @PrimaryKey var id : Long = 0,
    var name : String? = null,
    var description : String? = null,
    var tasks : List<Task>? = null
) : RealmObject()