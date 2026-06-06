package com.example.harrypotter.dto

import com.google.gson.annotations.SerializedName

data class StudentDTO(
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("house")
    val house: String = "",
    
    @SerializedName("image")
    val image: String = ""
)
