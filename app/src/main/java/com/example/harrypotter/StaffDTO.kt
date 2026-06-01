package com.example.harrypotter

data class StaffDTO(
    val name: String = "",
    val alternateNames: List<String> = emptyList(),
    val species: String = "",
    val house: String = ""
)
