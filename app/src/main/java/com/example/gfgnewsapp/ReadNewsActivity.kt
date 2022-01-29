package com.example.gfgnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class ReadNewsActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_news)

        webView = findViewById(R.id.wb)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        intent.getStringExtra("url")?.let {
            webView.loadUrl(
                it
            )
        }
    }
}