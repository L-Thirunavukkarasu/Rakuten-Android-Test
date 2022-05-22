package com.example.github.repositories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainFragment : Fragment() {

    private val viewModel = MainViewModel()

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerview: RecyclerView? = null
    private var loadingProgress: ProgressBar? = null
    private var adapter: RepositoryAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewModel.fetchItems()

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh!!.setOnRefreshListener { viewModel.refresh() }

        recyclerview = view.findViewById(R.id.news_list)
        recyclerview!!.layoutManager = LinearLayoutManager(context)

        //init loader
        loadingProgress = view.findViewById(R.id.progress_bar);
        //set loader visibility as visible
        loadingProgress!!.setVisibility(View.VISIBLE);

        viewModel.repositories.observeForever {
            viewModel.repositories.observeForever {
                adapter = RepositoryAdapter(it.take(20).toMutableList(), requireActivity())
                recyclerview!!.adapter = adapter

                //set loader visibility as gone
                loadingProgress!!.setVisibility(View.GONE);

                //fixed : swipe refresh animation not stop after getting data issue
                if (swipeRefresh!!.isRefreshing()) {
                    swipeRefresh!!.setRefreshing(false);
                }
            }
        }
        return view
    }

}