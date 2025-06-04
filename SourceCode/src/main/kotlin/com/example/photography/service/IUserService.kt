package com.example.photography.service

import com.example.photography.model.Booking
import com.example.photography.model.User


interface IUserService {

    fun allUsers(): List<User>

    fun getById(id: Long): User

    fun addUser(user: User): User

    fun updateUser(user: User): User

    fun deleteUser(id: Long)

    fun getByEmailAndPassword(email: String, password: String): User?

    fun getByEmail(email: String?): User?

    fun getByName(name: String?): User?

//    fun  checkStatus(id:Long,booking: Booking):User
}