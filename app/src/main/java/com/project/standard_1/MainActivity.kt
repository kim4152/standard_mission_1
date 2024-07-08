package com.project.standard_1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var icon1 :ImageView
    private lateinit var icon2 :ImageView
    private lateinit var icon3 :ImageView
    private lateinit var icon4 :ImageView
    private lateinit var iconText : TextView

    companion object{
        val IMAGE_URI = "imageUri"
        val IMAGE_NAME = "image_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK){
                val data = it.data

                val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data?.getParcelableExtra(IMAGE_URI, Uri::class.java)
                } else {
                    data?.getParcelableExtra(IMAGE_URI) as? Uri
                }

                if (uri != null) {
                    Glide.with(this)
                        .load(uri)
                        .centerCrop()
                        .circleCrop()
                        .into(icon4)
                }

                val text = data?.getStringExtra(IMAGE_NAME) ?: "No Name"
                iconText.text = text
            }
        }

        icon4.setOnClickListener{
            val intent = Intent(this,IconAdd::class.java)
            getContent.launch(intent)
        }
    }

    fun init(){
        icon1 = findViewById(R.id.icon1)
        icon2 = findViewById(R.id.icon2)
        icon3 = findViewById(R.id.icon3)
        icon4 = findViewById(R.id.icon4)
        iconText = findViewById(R.id.icon_text4)

        Glide.with(this )
            .load(R.drawable.naver)
            .centerCrop()
            .into(icon1)
        Glide.with(this )
            .load(R.drawable.youtube)
            .centerCrop()
            .into(icon2)
        Glide.with(this )
            .load(R.drawable.sparta)
            .centerCrop()
            .into(icon3)
    }
}