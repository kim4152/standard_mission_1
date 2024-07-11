package com.project.standard_1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.standard_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding

    companion object{
        val IMAGE_URI = "imageUri"
        val IMAGE_NAME = "image_name"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        enableEdgeToEdge()
        setContentView(binding.root)
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
                        .into(binding.icon4)
                }

                val text = data?.getStringExtra(IMAGE_NAME) ?: "No Name"
                binding.iconText4.text = text
            }
        }

        binding.icon4.setOnClickListener{
            val intent = Intent(this,IconAdd::class.java)
            getContent.launch(intent)
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun init(){
        Glide.with(this )
            .load(R.drawable.naver)
            .centerCrop()
            .into(binding.icon1)
        Glide.with(this )
            .load(R.drawable.youtube)
            .centerCrop()
            .into(binding.icon2)
        Glide.with(this )
            .load(R.drawable.sparta)
            .centerCrop()
            .into(binding.icon3)
    }
}