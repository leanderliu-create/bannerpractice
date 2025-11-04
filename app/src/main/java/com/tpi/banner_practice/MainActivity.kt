package com.tpi.banner_practice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.tpi.banner_practice.banner.view.BannerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadBannerFragment()
    }


    /**
     * 載入 BannerFragment
     */
    private fun loadBannerFragment() {
        // 檢查是否已經有 Fragment，避免重複添加
        val existingFragment = supportFragmentManager.findFragmentById(R.id.fvBannerContainer)
        if (existingFragment == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fvBannerContainer, BannerFragment::class.java, null)
            }
        }
    }
}