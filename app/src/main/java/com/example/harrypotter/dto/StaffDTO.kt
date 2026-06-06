package com.example.harrypotter.dto

import com.google.gson.annotations.SerializedName

data class StaffDTO(
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("alternate_names")
    val alternateNames: List<String> = emptyList(),
    
    @SerializedName("species")
    val species: String = "",
    
    @SerializedName("house")
    val house: String = "",
    
    @SerializedName("image")
    val image: String = ""
)
