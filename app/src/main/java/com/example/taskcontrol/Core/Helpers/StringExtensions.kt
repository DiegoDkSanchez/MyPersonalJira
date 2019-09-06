package com.example.taskcontrol.Core.Helpers

import com.example.taskcontrol.Core.BaseApp

fun String.resource(res: Int) : String{
    if (BaseApp.context() != null) {
        return  BaseApp.context().resources.getString(res)
    }
    return this
}