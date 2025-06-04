package com.example.photography.controller

import com.example.photography.model.Booking
import com.example.photography.model.Photographer
import com.example.photography.model.User
import com.example.photography.service.IBookingService
import com.example.photography.service.IPhotographerService
import com.example.photography.service.IUserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class HomeController(
    val req: HttpServletRequest,
    val userService: IUserService,
    val bookingService: IBookingService,
    val photographerService: IPhotographerService,
) {

    private val ALL_BOOKINGS: String = "admin/allBookings"
    private val ALL_PHOTOGRAPHERS: String = "admin/allPhotographers"
    private val ALL_USERS: String = "admin/allUsers"


    // It returns user index page

    @GetMapping("/")
    fun home() = "index.html"

    // It returns user login page

    @GetMapping("/userLogin")
    fun login() = "user/login.html"

    // It returns user register page

    @GetMapping("/userRegister")
    fun userReg() = "user/register"

    // It returns photographer login page

    @GetMapping("/photographerLogin")
    fun photoLogin() = "photographer/login.html"

    // It returns photographer register page

    @GetMapping("/photographerRegister")
    fun photoReg() = "photographer/register"

    // It returns test page

    @GetMapping("/test")
    fun test() = "test.html"

    // It returns admin login page

    @GetMapping("/adminLogin")
    fun adminLogin() = "admin/login"

    // It returns album page

    @GetMapping("/album")
    fun album() = "album"

    // It returns rentals page

    @GetMapping("/rentals")
    fun rentals() = "rentals"

    // It returns About us page

    @GetMapping("/about")
    fun about() = "about"

    // It returns allUsers  page

    @GetMapping("/allUsers")
    fun allUsers(model: Model): String {
        var user: User = req.session.getAttribute("user") as User
        model.addAttribute("users", userService.allUsers())
        return ALL_USERS
    }

    // It returns allPhotographers page

    @GetMapping("/allPhotographers")
    fun allPhotographers(model: Model): String {
        var photographer: Photographer = req.session.getAttribute("photographer") as Photographer
        model.addAttribute("photographer", photographerService.allPhotographers())
        return ALL_PHOTOGRAPHERS
    }


    // It returns allBookings page

    @GetMapping("/allBookings")
    fun allBookings(model: Model): String {
        var booking: Booking = req.session.getAttribute("booking") as Booking
        var user:User=req.session.getAttribute("user") as User
        model.addAttribute("booking", bookingService.allBookings())
        return ALL_BOOKINGS
    }
}