package com.eyyuperdogan.moviesapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.eyyuperdogan.moviesapp.adapter.RegisterRecyclerViewAdapter
import com.eyyuperdogan.moviesapp.databinding.ActivityDetailsActivtyBinding
import com.eyyuperdogan.moviesapp.models.MovieListResponse
import com.eyyuperdogan.moviesapp.models.RegisterPost
import com.eyyuperdogan.moviesapp.services.ApiService
import com.eyyuperdogan.moviesapp.services.MovieApiService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register_row.*
import retrofit2.*

open class MovieDetailsActivtiy : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsActivtyBinding
    private lateinit var db: FirebaseFirestore
    val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    var imageRegister:String?=null




    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 1)


        db = Firebase.firestore

        binding.save.setOnClickListener {
            val build = AlertDialog.Builder(this@MovieDetailsActivtiy)
                .setMessage("save movie?")
                .setTitle("Recorded")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .create()
            build.show()



            build.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                //evet
                Toast.makeText(
                    this@MovieDetailsActivtiy,
                    "successfully registered",
                    Toast.LENGTH_SHORT
                ).show()
                val title=binding.tvMovieBudgetTitle.text.toString()

                var movieHashMap = hashMapOf<String, Any>()
                movieHashMap.put("image", imageRegister!!)
                movieHashMap.put("title", binding.tvMovieTitle.text.toString())
                movieHashMap.put("date", binding.tvMovieDateRelease.text.toString())
                movieHashMap.put("voteAverage", binding.tvMovieRuntime.text.toString())


                db.collection("movie").document(binding.tvMovieTitle.text.toString()).set(movieHashMap).addOnSuccessListener {


                }.addOnFailureListener {
                    Toast.makeText(
                        this@MovieDetailsActivtiy,
                        it.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                build.dismiss()
            }


            build.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                //hayır
                Toast.makeText(this@MovieDetailsActivtiy, "not registered", Toast.LENGTH_SHORT)
                    .show()
                build.dismiss()
            }
        }

    

        var apiService = MovieApiService.getIntance().create(ApiService::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        //görseli getirmek için
                        Glide.with(this@MovieDetailsActivtiy)
                            .load(IMAGE_BASE + it.movies.get(position).poster)
                            .into(binding.imgDetailsMovie)
                        imageRegister = IMAGE_BASE + it.movies.get(position).poster.toString()
                        binding.tvMovieTitle.setText(it.movies.get(position).title)
                        binding.tvMovieTagLine.setText(it.movies.get(position).tagline)
                        binding.tvMovieDateRelease.setText(it.movies.get(position).release)
                        binding.tvMovieRating.setText(it.movies.get(position).popularity)
                        binding.tvMovieRuntime.setText(it.movies.get(position).voteAverage.toString())
                        binding.tvMovieBudget.setText(it.movies.get(position).id.toString())
                        binding.tvMovieOverview.setText(it.movies.get(position).overview)
                    }
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })


    }

    }





