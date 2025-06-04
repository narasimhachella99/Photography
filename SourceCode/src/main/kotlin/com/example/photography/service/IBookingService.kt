package com.example.photography.service

import com.example.photography.model.Booking

interface IBookingService {

    fun allBookings(): List<Booking>
    fun getById(id: Long): Booking
    fun addBooking(booking: Booking): Booking
    fun updateBooking(booking: Booking): Booking
    fun deleteBooking(id: Long)

}