package com.example.photography.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var phoneNumber: Long = 0,
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = ""
)
