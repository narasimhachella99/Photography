package com.example.photography.service.impl

import com.example.photography.model.Booking
import com.example.photography.model.User
import com.example.photography.repository.IUserRepository
import com.example.photography.service.IUserService
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: IUserRepository) : IUserService {

    override fun allUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).get()
    }

    override fun addUser(user: User): User {
        return userRepository.save(user)
    }

    override fun updateUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    override fun getByEmailAndPassword(email: String, password: String): User? {
        return userRepository.findByEmailAndPassword(email, password)
    }

    override fun getByEmail(email: String?): User? {
        return userRepository.findByEmail(email)
    }

    override fun getByName(name: String?): User? {
        return userRepository.findByName(name)
    }

//    override fun checkStatus(id: Long, booking: Booking): User {
//            val user:User=userRepository.findById(id).get()
//        return userRepository.save(booking)
//
//    }
}