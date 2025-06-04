package com.example.photography.service

import com.example.photography.model.Photographer
import com.example.photography.model.User

interface IPhotographerService {

    fun allPhotographers(): List<Photographer>
    fun getById(id: Long): Photographer
    fun addPhotographer(p: Photographer): Photographer
    fun updatePhotographer(p: Photographer): Photographer
    fun deletePhotographer(id: Long)
    fun getByEmail(email: String?): Photographer?
    fun getByEmailAndPassword(email: String, password: String): Photographer?
}