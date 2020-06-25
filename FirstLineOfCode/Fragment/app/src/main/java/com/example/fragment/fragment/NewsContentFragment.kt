package com.example.fragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragment.R
import kotlinx.android.synthetic.main.news_content_frag.*

/**
 * Fragment class for news content
 */
class NewsContentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content_frag, container, false)
    }

    /**
     * Set the content view to visible and refresh the current info
     *
     * @param title news title
     * @param content new content
     */
    fun refresh(title: String, content: String) {
        contentLayout.visibility = View.VISIBLE
        newsTitle.text = title
        newsContent.text = content
    }
}