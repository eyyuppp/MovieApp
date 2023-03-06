package com.eyyuperdogan.moviesapp.models

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieListResponse(
    @SerializedName("results")
    val movies : List<Movie>

) : Parcelable {
    constructor() : this(mutableListOf())
}
