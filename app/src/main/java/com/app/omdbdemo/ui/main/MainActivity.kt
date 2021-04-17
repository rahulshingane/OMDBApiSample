package com.app.omdbdemo.ui.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.omdbdemo.R
import com.app.omdbdemo.core.BaseActivity
import com.app.omdbdemo.databinding.MainActivityBinding
import com.app.omdbdemo.model.MovieResponse
import com.app.omdbdemo.ui.detail.MovieDetailsActivity
import com.app.omdbdemo.utils.Constants
import com.app.omdbdemo.utils.hideKeyboard
import com.app.omdbdemo.utils.showSnackBar

/**
 * This activity will show list of movies. User can search his movie and list will show to the user based on his search.
 */
class MainActivity : BaseActivity() {

    lateinit var binding: MainActivityBinding
    lateinit var adapter: MovieListAdapter


    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        initView()
    }

    private fun initView() {
        initListeners()
        initRecycleView()
        initPullToRefresh()
    }

    private fun initRecycleView() {
        adapter = MovieListAdapter()
        binding.rvMovies.adapter = adapter

        adapter.setRecyclerViewItemClick { itemView, model ->
            when (itemView.id) {
                R.id.clMain -> {
                    startActivity(MovieDetailsActivity.getStartIntent(this, model.imdbID ?: ""))
                }
            }
        }

        adapter.setOnLoadMoreListener(binding.rvMovies) {
            val isNextPage = viewModel.isPageRemaining(adapter.data.size)
            if (isNextPage) {
                viewModel.updatePageCount()
                // hbPagingUtils.settings?.hasNextPage = false
                searchMovie(false)
            }
            isNextPage
        }
    }

    private fun initListeners() {
        binding.inputSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(this)
                if (isNetworkConnected() && textView.text.toString().isNotBlank()) {
                    viewModel.searchKey = textView.text.toString()
                    viewModel.resetPageCount()
                    searchMovie(true)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun initPullToRefresh() {
        binding.srlMovieList.apply {
            setOnRefreshListener {
                if (isNetworkConnected()) {
                    viewModel.resetPageCount()
                    searchMovie(false)
                } else {
                    isRefreshing = false
                }
            }
        }
    }

    /**
     * Call an api to get movie list based on search key enter by user
     */
    private fun searchMovie(showProgress: Boolean) {
        viewModel.getMovies().observe(this, {
            binding.srlMovieList.isRefreshing = false
            it?.let { resource ->
                when (resource.status) {
                    Constants.Status.SUCCESS -> {
                        showHideProgressDialog(false)
                        resource.data?.let { users -> setMovieList(users) }
                    }
                    Constants.Status.ERROR -> {
                        showHideProgressDialog(true)
                        it.message?.showSnackBar(this)
                        showHideNoDataFound(true, it.message ?: "")
                    }
                    Constants.Status.LOADING -> {
                        showHideProgressDialog(showProgress)
                    }
                }

            }
        })

    }

    /**
     * Show Movie list response.
     */
    private fun setMovieList(movieResponse: MovieResponse) {

        binding.apply {
            if (movieResponse.isSuccess()) {
                showHideNoDataFound(false)
                viewModel.totalItems = movieResponse.getTotalItems()
                if (viewModel.isFirstPage()) {
                    adapter.clear(true)
                }
                val size = adapter.data.size
                adapter.addAll(movieResponse.search, true)
                adapter.notifyItemRangeInserted(size, movieResponse.search.size)
                adapter.setLoadMoreComplete()
            } else if (viewModel.isFirstPage()) {
                showHideNoDataFound(true, movieResponse.error ?: "")
            }

        }
    }

    /**
     * Show or Hide no data found message
     */
    private fun showHideNoDataFound(show: Boolean, message: String = "") {
        binding.apply {
            if (show) {
                rvMovies.visibility = View.GONE
                tvNoDataFound.visibility = View.VISIBLE
                tvNoDataFound.text = message
            } else {
                rvMovies.visibility = View.VISIBLE
                tvNoDataFound.visibility = View.GONE
                tvNoDataFound.text = message
            }
        }
    }

}