package com.example.detector

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    val RESULT_LOAD_IMAGE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1);
        }

        val buttonLoadImage: Button = findViewById(R.id.load_button)
        val detectButton: Button = findViewById(R.id.detect)
        val image: ImageView = findViewById(R.id.image)

        // Image loading functionality
        buttonLoadImage.setOnClickListener{
            val textView: TextView = findViewById(R.id.result_text)
            textView.text = ""
            //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(intent, RESULT_LOAD_IMAGE)
        }

        val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { image.setImageURI(uri) }
        }
        fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")


        // Object detection
        detectButton.setOnClickListener{
            selectImageFromGallery()
        }


    }
}