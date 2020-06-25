package com.example.fragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.NewsContentActivity
import com.example.fragment.R
import com.example.fragment.entity.News
import kotlinx.android.synthetic.main.activity_news_content.*
import kotlinx.android.synthetic.main.news_title_frag.*
import java.lang.StringBuilder

class NewsTitleFragment : Fragment() {
    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag, container, false)
    }

    /**
     * Set the pane mode after the activity is created
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isTwoPane = activity?.findViewById<View>(R.id.newsContentLayout) != null

        // set the layout manager
        val layoutManager = LinearLayoutManager(activity)
        newsTitleRecyclerView.layoutManager = layoutManager
        val adapter = NewsAdapter(generateNews())
        newsTitleRecyclerView.adapter = adapter
    }

    /**
     * Generate all the random news
     *
     * @return newsList the list of news
     */
    private fun generateNews(): List<News> {
        val newsList = ArrayList<News>()

        for (i in 1..50) {
            newsList.add(
                News(
                    "The news title is $i",
                    generateRandomNewsContent("The news content is $i")
                )
            )
        }
        return newsList
    }

    /**
     * Generate random news content
     *
     * @return res the generated random content
     */
    private fun generateRandomNewsContent(title: String): String {
        val n = (1..20).random()
        val res = StringBuilder()
        repeat(n) {
            res.append(title)
        }
        return res.toString()
    }

    /**
     * RecyclerView adapter for the news list
     *
     * @param newsList list of news
     */
    inner class NewsAdapter(val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitle: TextView = view.findViewById(R.id.newsTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
            val holder = ViewHolder(view)
            // item click listener
            holder.itemView.setOnClickListener {
                val news = newsList[holder.adapterPosition]
                if (isTwoPane) { // 2 pages
                    val fragment = newsContentFrag as NewsContentFragment
                    fragment.refresh(news.title, news.content)
                } else { // 1 page
                    NewsContentActivity.actionStart(parent.context, news.title, news.content)
                }
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitle.text = news.title
        }

        override fun getItemCount() = newsList.size
    }


}