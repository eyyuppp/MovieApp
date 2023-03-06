package com.eyyuperdogan.moviesapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyyuperdogan.moviesapp.adapter.RegisterRecyclerViewAdapter
import com.eyyuperdogan.moviesapp.databinding.ActivityRecordedActitiyBinding
import com.eyyuperdogan.moviesapp.models.RegisterPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_row.*
import org.w3c.dom.Document

class RecordedActitiy : AppCompatActivity() {
    private lateinit var binding: ActivityRecordedActitiyBinding
    private lateinit var movieAdapter: RegisterRecyclerViewAdapter
    private lateinit var movieList: ArrayList<RegisterPost>
    private lateinit var db:FirebaseFirestore

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecordedActitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=Firebase.firestore

        movieList=ArrayList()
        binding.rvMoviesList.layoutManager=LinearLayoutManager(this)
        movieAdapter= RegisterRecyclerViewAdapter(movieList)
        binding.rvMoviesList.adapter=movieAdapter

        getAll()


    }

    @SuppressLint("NotifyDataSetChanged")
    fun getAll(){
        db.collection("movie").orderBy("date",Query.Direction.DESCENDING)
            .addSnapshotListener{value,error->
            val documents=value!!.documents
            for (document in documents){
                val title=document.get("title") as String
                val date=document.get("date") as String
                val voteavarege=document.get("voteAverage") as String
                val image=document.get("image") as String
                    val movie = RegisterPost(title, voteavarege, date, image)
                    movieList.add(movie)
                

            }
            movieAdapter.notifyDataSetChanged()

        }
    }
}