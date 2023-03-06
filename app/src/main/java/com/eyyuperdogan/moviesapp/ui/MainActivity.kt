package com.eyyuperdogan.moviesapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyyuperdogan.moviesapp.R
import com.eyyuperdogan.moviesapp.adapter.RecyclerViewAdapter
import com.eyyuperdogan.moviesapp.databinding.ActivityMainBinding
import com.eyyuperdogan.moviesapp.models.MovieListResponse
import com.eyyuperdogan.moviesapp.services.ApiService
import com.eyyuperdogan.moviesapp.services.MovieApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_movies_list.layoutManager=LinearLayoutManager(this)
        rv_movies_list.setHasTransientState(true)




        var apiService= MovieApiService.getIntance().create(ApiService::class.java)
        apiService.getMovieList().enqueue(object :Callback<MovieListResponse>{
            override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                prgBarMovies.visibility = View.GONE
                if (response.isSuccessful){
                    response.body()?.let {
                        prgBarMovies.visibility=View.GONE
                        rv_movies_list.adapter= RecyclerViewAdapter(it.movies)
                    }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })






    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.menu_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.recoded_menu) {
          val intent=Intent(this, RecordedActitiy::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}
/*
      rv_movies_list.layoutManager=LinearLayoutManager(this)
      rv_movies_list.setHasTransientState(true)
      getMovieData {
          prgBarMovies.visibility=View.GONE
          rv_movies_list.adapter=RecyclerViewAdapter(it)
      }

    }
    fun getMovieData(callback:(List<Movie>) -> Unit) {
     val  apiService=MovieApiService.getIntance().create(ApiService::class.java)
     apiService.getMovieList().enqueue(object :Callback<MovieListResponse>{
         override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
            return callback(response.body()?.movies!!)
         }

         override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
           t.printStackTrace()
         }

     })

 */

