package com.javierortizmi.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val webView = WebView(this)
        webView.loadUrl("https://www.google.com")

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true

        setContentView(webView)
    }
}