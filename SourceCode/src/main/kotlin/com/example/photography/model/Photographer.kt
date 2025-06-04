package com.example.photography.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Photographer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var email: String = "",
    var password: String = "",
    var workspace: String = "",
    var phoneNumber: Long = 0,
    var address: String = "",
    var experience: Long = 0,
    var city: String = "",
    var state: String = "",
    var country: String = ""

)
