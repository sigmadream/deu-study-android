package com.sangkon.ch18

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sangkon.ch18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var retrofitFragment: RetrofitFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofitFragment= RetrofitFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, retrofitFragment)
            .commit()
    }
}