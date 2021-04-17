package com.app.omdbdemo.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.omdbdemo.R
import com.app.omdbdemo.core.BaseActivity
import com.app.omdbdemo.databinding.ActivityMovieDetailsBinding
import com.app.omdbdemo.model.MovieDetailResponse
import com.app.omdbdemo.utils.Constants
import com.app.omdbdemo.utils.getFromIntent
import com.app.omdbdemo.utils.loadImageNoPlaceHolder
import com.app.omdbdemo.utils.showSnackBar

class MovieDetailsActivity : BaseActivity() {

    companion object {
        fun getStartIntent(context: Context, movieId: String) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra("movie_id", movieId)
            }
    }

    lateinit var binding: ActivityMovieDetailsBinding

    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    private val movieId by lazy {
        getFromIntent("movie_id", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        if (isNetworkConnected())
            getMovieDetail(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Call an api to get movie detail
     */
    private fun getMovieDetail(showProgress: Boolean) {
        viewModel.getMovieDetail(movieId).observe(this, {

            it?.let { resource ->
                when (resource.status) {

                    Constants.Status.SUCCESS -> {
                        showHideProgressDialog(false)
                        resource.data?.let { users -> showMovieDetail(users) }
                    }
                    Constants.Status.ERROR -> {
                        showHideProgressDialog(true)
                        it.message?.showSnackBar(this)
                    }
                    Constants.Status.LOADING -> {
                        showHideProgressDialog(showProgress)
                    }
                }

            }
        })

    }

    private fun showMovieDetail(movie: MovieDetailResponse) {
        binding.apply {
            mainCollapsing.isTitleEnabled = false
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.released
            ivPoster.loadImageNoPlaceHolder(movie.poster)
            tvDirector.text = movie.director
            tvWriter.text = movie.writer
            tvActors.text = movie.actors
            tvCategory.text = movie.type
            tvTime.text = movie.getMovieLength()
            tvRatting.text = movie.imdbRating
            tvSynopsis.text = movie.plot
            tvScore.text = movie.imdbRating
            tvPopularity.text = movie.imdbVotes
        }
    }
}