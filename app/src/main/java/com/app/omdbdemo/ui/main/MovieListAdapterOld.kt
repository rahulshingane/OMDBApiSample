package com.app.omdbdemo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.omdbdemo.R
import com.app.omdbdemo.model.SearchItem
import com.app.omdbdemo.utils.loadImageNoPlaceHolder
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListAdapterOld(private val movieList:ArrayList<SearchItem>, private val listener :(SearchItem) -> Unit):
    RecyclerView.Adapter<MovieListAdapterOld.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_movie, parent, false
        )
    )

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], listener)
    }

    inner class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item:SearchItem, listener: (SearchItem) -> Unit) = with(itemView){
            ivPoster.loadImageNoPlaceHolder(item.poster)
            tvTitle.text = item.title
            setOnClickListener { listener(item) }
        }
    }
}