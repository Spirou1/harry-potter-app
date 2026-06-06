package com.example.harrypotter.dto

import com.google.gson.annotations.SerializedName

data class SpellDTO(
    @SerializedName("id")
    val id: String = "",
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("description")
    val description: String = ""
)
