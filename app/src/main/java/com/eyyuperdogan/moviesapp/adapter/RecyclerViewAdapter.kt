package com.eyyuperdogan.moviesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyyuperdogan.moviesapp.databinding.ItemRowBinding
import com.eyyuperdogan.moviesapp.models.Movie
import com.eyyuperdogan.moviesapp.ui.MovieDetailsActivtiy
import kotlinx.android.synthetic.main.activity_details_activty.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val movieList:List<Movie>): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    class RowHolder( binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root){

        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bind(movies: Movie){
            itemView.movie_title.setText(movies.title)
            itemView.movie_release_date.setText(movies.release)
            itemView.text_popularty.setText(movies.voteAverage.toString())
            Glide.with(itemView).load(IMAGE_BASE+movies.poster).into(itemView.movie_poster)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(movieList.get(position))
        holder.itemView.setOnClickListener(){
            val intent=Intent(holder.itemView.context, MovieDetailsActivtiy::class.java)
            intent.putExtra("position",position)
            holder.itemView.context.startActivity(intent)
         }
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }


}