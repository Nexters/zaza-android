package com.nexters.zaza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_member.*

class MemberActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        image_member_jk.setOnClickListener(this)
        text_member_jk.setOnClickListener(this)

        webview_member.settings.javaScriptEnabled = true
        webview_member.webChromeClient = WebChromeClient()
        webview_member.webViewClient = MemberWebViewClient()

        text_member_nexters.setOnClickListener {
            webview_member.loadUrl("https://www.facebook.com/Nexterspage/")
            webview_member.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            image_member_jk, text_member_jk -> {
                webview_member.loadUrl("https://www.instagram.com/bingocake/")
                webview_member.visibility = View.VISIBLE
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webview_member.canGoBack()) {
                webview_member.goBack()
            } else {
                if (webview_member.visibility == View.GONE) {
                    finish()
                } else {
                    webview_member.visibility = View.GONE
                }
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    class MemberWebViewClient: WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }
}
