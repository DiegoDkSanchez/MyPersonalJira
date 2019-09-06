package com.example.taskcontrol.Core

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.taskcontrol.R

object Constantes {
    val TODO = "ToDo"
    val DOING = "Doing"
    val BLOCKING = "Blocking"
    val DONE  = "Done"
    val FINISH = "Finish"

    val CONFIG_GLIDE = RequestOptions()
        .placeholder(R.mipmap.icon_task_manager)
        .priority(Priority.NORMAL)
        .format(DecodeFormat.PREFER_RGB_565)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .dontAnimate()
        .fitCenter()
}