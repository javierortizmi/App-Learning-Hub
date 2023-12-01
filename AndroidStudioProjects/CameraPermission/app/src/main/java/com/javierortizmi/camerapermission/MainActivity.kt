package com.javierortizmi.camerapermission

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Code for the camera
        val cameraContract: ActivityResultContracts.TakePicturePreview =
            ActivityResultContracts.TakePicturePreview()
        val cameraCallback = CameraResults()
        cameraLauncher = registerForActivityResult(cameraContract, cameraCallback)
        // We can oly launch if we have permission for camera

        val cameraGrantedPermission: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (cameraGrantedPermission == PackageManager.PERMISSION_GRANTED) {
            Log.w(MA, "Camera Permission already granted")
            cameraLauncher.launch(null)
        } else {
            Log.w(MA, "Need to ask camera permission\n" +
                    "Return permission: $cameraGrantedPermission\n" +
                    "Needed permission: ${PackageManager.PERMISSION_GRANTED}")
            val contract: ActivityResultContracts.RequestPermission = ActivityResultContracts.RequestPermission()
            val callback = Results()
            launcher = registerForActivityResult(contract, callback)
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    inner class Results: ActivityResultCallback<Boolean> {
        override fun onActivityResult(result: Boolean) {
            if (result) {
                Log.w(MA, "Permission granted by user\n" +
                        "Permission code: " +
                        "${ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)}")
                // Start using the camera
                cameraLauncher.launch(null)
            } else {
                Log.w(MA, "Permission denied by user")
            }
        }
    }

    inner class CameraResults: ActivityResultCallback<Bitmap?> {
        override fun onActivityResult(result: Bitmap?) {
            Log.w(MA, "Display the image in the imageView")
        }
    }

    companion object {
        const val MA: String = "MainActivity"
    }
}