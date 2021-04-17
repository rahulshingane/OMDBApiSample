package com.app.omdbdemo.ui.main

import android.annotation.SuppressLint
import com.app.omdbdemo.R
import com.app.omdbdemo.databinding.ListItemMovieBinding
import com.app.omdbdemo.model.SearchItem
import com.app.omdbdemo.utils.loadImageNoPlaceHolder
import easyadapter.dc.com.library.EasyAdapter

class MovieListAdapter: EasyAdapter<SearchItem, ListItemMovieBinding>(R.layout.list_item_movie){

    override fun onCreatingHolder(binding: ListItemMovieBinding, holder: EasyHolder) {
        super.onCreatingHolder(binding, holder)
        binding.clMain.setOnClickListener(holder.clickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBind(binding: ListItemMovieBinding, movie: SearchItem) {
        binding.apply {
            ivPoster.loadImageNoPlaceHolder(movie.poster)
            tvTitle.text = movie.title
            tvYear.text = movie.year
        }
    }
}