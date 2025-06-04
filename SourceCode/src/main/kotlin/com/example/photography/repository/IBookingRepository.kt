package com.example.photography.repository

import com.example.photography.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IBookingRepository : JpaRepository<Booking, Long> {


}