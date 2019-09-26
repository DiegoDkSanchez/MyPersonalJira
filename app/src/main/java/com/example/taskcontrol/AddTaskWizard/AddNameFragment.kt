package com.example.taskcontrol.AddTaskWizard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskcontrol.R
import kotlinx.android.synthetic.main.add_name_fragment.*

class AddNameFragment: Fragment() {

    companion object{
        var nameTask = String()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_name_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nameTaskEditText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(name: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameTask = name.toString()
            }
        })
    }
}