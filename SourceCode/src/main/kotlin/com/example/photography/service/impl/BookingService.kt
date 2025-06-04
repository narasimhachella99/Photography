package com.example.photography.service.impl

import com.example.photography.model.Booking
import com.example.photography.repository.IBookingRepository
import com.example.photography.service.IBookingService
import org.springframework.stereotype.Service

@Service
class BookingService(var bookingRepository: IBookingRepository) : IBookingService {
    override fun allBookings(): List<Booking> {
        return bookingRepository.findAll()
    }

    override fun getById(id: Long): Booking {
        return bookingRepository.findById(id).get()
    }

    override fun addBooking(booking: Booking): Booking {
        return bookingRepository.save(booking)
    }

    override fun updateBooking(booking: Booking): Booking {
        return bookingRepository.save(booking)
    }

    override fun deleteBooking(id: Long) {
        bookingRepository.deleteById(id)
    }
}