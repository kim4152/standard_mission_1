package com.project.standard_1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.project.standard_1.MainActivity.Companion.IMAGE_NAME
import com.project.standard_1.MainActivity.Companion.IMAGE_URI

class IconAdd : AppCompatActivity() {

    private lateinit var icon: ImageView
    private lateinit var editText: EditText
    private lateinit var btnCancel: ImageView
    private lateinit var btnSave: MaterialButton
    private lateinit var imgInfo: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_icon_add)

        init()


        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    imgInfo = uri
                    Glide.with(this)
                        .load(uri)
                        .centerCrop()
                        .circleCrop()
                        .into(icon)
                }
            }


        icon.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnCancel.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            if (editText.text.toString() != "") {
                val intent = Intent(this, MainActivity::class.java)
                val bundle = bundleOf(IMAGE_URI to imgInfo, IMAGE_NAME to editText.text.toString())
                intent.putExtras(bundle)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    fun init() {
        icon = findViewById(R.id.icon_add)
        editText = findViewById(R.id.icon_edit_text)
        btnCancel = findViewById(R.id.btn_cancel)
        btnSave = findViewById(R.id.btn_save)
    }

}