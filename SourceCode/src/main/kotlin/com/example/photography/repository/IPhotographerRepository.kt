package com.example.photography.repository

import com.example.photography.model.Photographer
import com.example.photography.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPhotographerRepository : JpaRepository<Photographer, Long> {

    fun findByEmail(email: String?): Photographer?
    fun findByEmailAndPassword(email: String, password: String): Photographer?

}