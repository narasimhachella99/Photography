package com.example.photography.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var userName: String = "",
    var pEmail: String = "",
    var pAddress: String = "",
    var pPhoneNumber: Long = 0,
    var pExperience: Long = 0,
    var status: String = "Not accepted"

)
