package com.example.taskcontrol

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.example.taskcontrol.Core.Model.Project
import com.example.taskcontrol.Core.RealmsData
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_project.*
import java.io.File

class AddProject : AppCompatActivity() {

    private var fileUri: Uri? = null
    private val realms = RealmsData()
    private var id : Long? = null
    private var loadProject : Project? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        realms.configureRealm()
        id = intent.getLongExtra("id", 0)
        if(id!= 0L){
            loadProject = realms.getProject(id?: 0)
            projectNameEditText.setText(loadProject?.name)
            descriptionEditText.setText(loadProject?.description)
            pathOfImageTextView.text = loadProject?.imagePath
            fileUri = loadProject?.imagePath?.toUri()
            addProjectButton.text = getString(R.string.save)
            deleteProjectButton.visibility = View.VISIBLE
        }

        cameraBotton.setOnClickListener {
            askCameraPermission()
        }
        addProjectButton.setOnClickListener {
            addProject()
        }
        deleteProjectButton.setOnClickListener{
            deleteProject()
        }
    }

    private fun deleteProject() {
        if(loadProject != null){
            realms.deleteProject(loadProject!!)
            this.onBackPressed()
        }
    }

    private fun addProject() {
        if(loadProject != null){
            var updateProject = Project(
                id = loadProject!!.id,
                name = projectNameEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                imagePath = fileUri.toString(),
                dateInit = loadProject!!.dateInit
            )
            saveProject(updateProject)
        }else{
            var newProject = Project(
                name = projectNameEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                imagePath = fileUri.toString()
            )
            saveProject(newProject)
        }
    }

    private fun saveProject(project: Project){
        realms.insertOrUpdateProject(project)
        this.onBackPressed()
    }


    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        fileUri = contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, 1)
        }
    }

    fun askCameraPermission(){
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                    if(report.areAllPermissionsGranted()){
                        //once permissions are granted, launch the camera
                        launchCamera()
                    }else{
                        Toast.makeText(this@AddProject, "All permissions need to be granted to take photo", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {/* ... */
                    //show alert dialog with permission options
                    AlertDialog.Builder(this@AddProject)
                        .setTitle(
                            "Permissions Error!")
                        .setMessage(
                            "Please allow permissions to take photo with camera")
                        .setNegativeButton(
                            android.R.string.cancel,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.cancelPermissionRequest()
                            })
                        .setPositiveButton(android.R.string.ok,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.continuePermissionRequest()
                            })
                        .setOnDismissListener({
                            token?.cancelPermissionRequest() })
                        .show()
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && resultCode == -1) {
            pathOfImageTextView.text = fileUri.toString()
        }else if(resultCode == Activity.RESULT_OK && resultCode == -1){
            fileUri = getPickImageResultUri(data)
            pathOfImageTextView.text = fileUri.toString()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun getPickImageResultUri(data: Intent?): Uri? {
        var uri:Uri? = null
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        if (isCamera){
            uri = getCaptureImageOutputUri()
        }else {
            uri =  data!!.data
        }
        return uri
    }

    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = externalCacheDir
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "pickImageResult.jpeg"))
        }
        return outputFileUri
    }
}
