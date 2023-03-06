package com.eyyuperdogan.moviesapp.adapter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyyuperdogan.moviesapp.databinding.ActivityDetailsActivtyBinding
import com.eyyuperdogan.moviesapp.databinding.ItemRowBinding
import com.eyyuperdogan.moviesapp.databinding.RegisterRowBinding
import com.eyyuperdogan.moviesapp.models.Movie
import com.eyyuperdogan.moviesapp.models.RegisterPost
import com.eyyuperdogan.moviesapp.ui.MainActivity
import com.eyyuperdogan.moviesapp.ui.MovieDetailsActivtiy
import com.eyyuperdogan.moviesapp.ui.RecordedActitiy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_activty.*
import kotlinx.android.synthetic.main.activity_recorded_actitiy.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.movie_poster
import kotlinx.android.synthetic.main.item_row.view.movie_release_date
import kotlinx.android.synthetic.main.item_row.view.movie_title
import kotlinx.android.synthetic.main.item_row.view.text_popularty
import kotlinx.android.synthetic.main.register_row.view.*

class RegisterRecyclerViewAdapter(var registerList :ArrayList<RegisterPost>) : RecyclerView.Adapter<RegisterRecyclerViewAdapter.ViewHolder>() {
    private lateinit var db: FirebaseFirestore
    private lateinit var movieAdapter: RegisterRecyclerViewAdapter
    private lateinit var movieList: ArrayList<RegisterPost>
    inner class ViewHolder(binding: RegisterRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = RegisterRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.movie_title.setText(registerList.get(position).title)
        holder.itemView.movie_release_date.setText(registerList.get(position).date)
        holder.itemView.text_popularty.setText(registerList.get(position).voteAverage)
        //görseli göstermek için
        Picasso.get().load(registerList.get(position).image).into(holder.itemView.movie_poster)

        movieList= ArrayList()
        movieAdapter= RegisterRecyclerViewAdapter(movieList)





        //kaydı silmek
        holder.itemView.butonDelete.setOnClickListener {
            val build = AlertDialog.Builder(holder.itemView.context)
                .setMessage("Are you sure you want to delete the record?")
                .setTitle("Delete")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .create()
            build.show()

            build.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                db = Firebase.firestore

                db.collection("movie").document(registerList.get(position).title).delete()
                    .addOnSuccessListener {
                        registerList.removeAt(position)
                        notifyItemRemoved(position)

                        Toast.makeText(
                            holder.itemView.context,
                            "record deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        build.dismiss()


                    }.addOnFailureListener {

                    Toast.makeText(holder.itemView.context, it.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            build.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {

                Toast.makeText(holder.itemView.context, "record not deleted", Toast.LENGTH_SHORT)
                    .show()
                build.dismiss()
            }
        }
    }


    override fun getItemCount(): Int {
        return registerList.size
    }



}