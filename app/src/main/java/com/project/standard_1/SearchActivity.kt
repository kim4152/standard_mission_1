package com.project.standard_1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.standard_1.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initRecyclerView()

        binding.arrowBack.setOnClickListener { goMain() }

        with(binding.editText){
            setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchHistoryAdapter.addHistory(text.toString())
                    clearEditText()
                    hideEditText(this)
                    true
                }
                true
            }
        }
        binding.clearText.setOnClickListener { clearEditText() }
    }

    fun initRecyclerView(){
        searchHistoryAdapter = SearchHistoryAdapter(){ Toast.makeText(this,it,Toast.LENGTH_SHORT).show() }
        searchHistoryAdapter.submitList(mutableListOf<Root>(
            Title(1,"인기 검색어"),SearchPopular(3, "홍명보"),Title(1,"검색 기록")
        ))
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(context)
            adapter = searchHistoryAdapter
        }
    }
    fun clearEditText() = binding.editText.setText("")
    fun hideEditText(editText: EditText){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }
    fun goMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

open  class Root(open val type:Int, open val text:String){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
data class Title(override val type:Int = 1, override val text:String):Root(type,text)
data class SearchHistory(override val type:Int = 2, override val text:String):Root(type,text)
data class SearchPopular(override val type:Int = 3, override val text:String):Root(type,text)
