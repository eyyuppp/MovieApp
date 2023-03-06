package com.eyyuperdogan.moviesapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id : Int ?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("release_date")
    val release : String?,

    @SerializedName("backdrop_path")
    val backdrop_path: String,

    @SerializedName("original_language")
    val original_language: String,

    @SerializedName("original_title")
    val original_title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: String,

    @SerializedName("runtime")
    val runtime: Double,

    @SerializedName("budget")
    val budget: Double, // 63000000

    @SerializedName("vote_average")
    val voteAverage: Double, // 7.5
    @SerializedName("tagline")
    val tagline: String, // Mischief. Mayhem. Soap.
    @SerializedName("revenue")
    val revenue: Int, // 100853753

) : Parcelable{
    constructor() : this(0, "", "", "","",""
    ,"","","",0.0,0.0,0.0,"",0)
}