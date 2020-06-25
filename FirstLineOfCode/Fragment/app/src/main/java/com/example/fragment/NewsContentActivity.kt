package com.example.fragment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragment.fragment.NewsContentFragment
import kotlinx.android.synthetic.main.activity_news_content.*

/**
 * Activity for showing information of our news
 */
class NewsContentActivity : AppCompatActivity() {
    companion object { // use companion object to tell the attrs more clearly
        fun actionStart(context: Context, title: String, content: String) {
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra("news_title", title)
                putExtra("news_content", content)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)

        // get the news information
        val title = intent.getStringExtra("news_title")
        val content = intent.getStringExtra("news_content")

        if (title != null && content != null) {
            val fragment = newsContentFrag as NewsContentFragment
            // refresh the news view
            fragment.refresh(title, content)
        }
    }
}
