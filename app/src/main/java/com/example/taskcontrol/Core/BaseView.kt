package com.example.taskcontrol.Core

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface BaseView {
    fun viewContext(): Context? {
        if (this is AppCompatActivity) {
            var activity =  this  as AppCompatActivity
            return activity

        } else if (this is Fragment) {
            var fragment =  this  as Fragment
            return fragment.activity!!
        }
        return null
    }
}