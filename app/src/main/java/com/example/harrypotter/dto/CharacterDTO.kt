package com.example.harrypotter.dto

import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    @SerializedName("id")
    val id: String = "",
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("species")
    val species: String = "",
    
    @SerializedName("house")
    val house: String = "",
    
    @SerializedName("image")
    val image: String = ""
)
