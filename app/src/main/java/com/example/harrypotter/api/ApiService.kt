package com.example.harrypotter.api

import com.example.harrypotter.dto.CharacterDTO
import com.example.harrypotter.dto.SpellDTO
import com.example.harrypotter.dto.StaffDTO
import com.example.harrypotter.dto.StudentDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/spells")
    suspend fun getSpells(): List<SpellDTO>

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): List<CharacterDTO>

    @GET("api/characters/staff")
    suspend fun getStaff(): List<StaffDTO>

    @GET("api/characters/house/{house}")
    suspend fun getStudentsByHouse(@Path("house") house: String): List<StudentDTO>
}
