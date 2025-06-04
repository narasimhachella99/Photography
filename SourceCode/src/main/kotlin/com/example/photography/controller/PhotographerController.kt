package com.example.photography.controller

import com.example.photography.model.Booking
import com.example.photography.model.Photographer
import com.example.photography.model.User
import com.example.photography.service.IBookingService
import com.example.photography.service.IPhotographerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class PhotographerController(
    var photographerService: IPhotographerService,
    var bookingService: IBookingService,
    var req: HttpServletRequest

) {


    private val HOME: String = "photographer/home"
    private var USER_LOG: String = "admin/home"
    private var USER_REG: String = "photographer/register"


    /// User can register with own credentials
    /// Here user have some conditions to get registration
    /// By through Below conditions user can register


    @PostMapping("/photographerRegister")
    private fun photographerReg(
        model: Model,
        email: String,
        password: String,
        workspace: String,
        phoneNumber: Long,
        address: String,
        experience: Long,
        city: String,
        state: String,
        country: String
    ): String {
        if (email == "" || password == "") {
            model.addAttribute("error", "please fill out all the fields")
        } else {
            val photographer: Photographer = Photographer(
                email = email,
                password = password,
                workspace = workspace,
                phoneNumber = phoneNumber,
                address = address,
                experience = experience,
                city = city,
                state = state,
                country = country
            )
            if (photographerService.getByEmail(photographer.email) != null) {
                model.addAttribute("error", "user already exist");
                return USER_REG
            } else {
                photographerService.addPhotographer(photographer)
                model.addAttribute("success", "registration done successfully")
                req.session.setAttribute("photographer", photographer)
                return USER_REG
            }

        }
        return USER_REG;
    }


    /// User can login with registered credentials
    // By through below conditions

    @PostMapping("/photographerLogin")
    private fun photoLogin(model: Model, email: String, password: String): String {
        if (email == "" || password == "") {
            model.addAttribute("error", "please fil out all the fields")
        } else {
            val photographer: Photographer? = photographerService.getByEmailAndPassword(email, password)
            if (photographer != null) {
                req.session.setAttribute("photographer", photographer)
                return "redirect:/photographerHome"
            }
        }
        return USER_LOG;
    }


    /// It returns the home page of photographer
    // along with some condition


    @GetMapping("/photographerHome")
    private fun userHome(model: Model): String {
        var photographer: Photographer = req.session.getAttribute("photographer") as Photographer
        if (photographer != null) {
            model.addAttribute("photographer", photographer)
            return HOME
        } else {
            return "photographer/login"
        }
    }


    // It returns all the bookings for photographer

    @GetMapping("/photoBookings")
    fun photoBookings(model: Model,isApproved: Boolean): String {
        var booking: Booking = req.session.getAttribute("booking") as Booking
        var user: User = req.session.getAttribute("user") as User
        var photographer: Photographer = req.session.getAttribute("photographer") as Photographer
        model.addAttribute(
            "booking",
            bookingService.allBookings().stream()
                .filter { i -> i.pEmail == photographer.email && i.status == "Not accepted"})
        return "photographer/viewRequests"
    }

    //Here photographer can accept or reject by own choice

    @GetMapping("/bookings/{id}")
    fun updateBooking(isApproved: Boolean,@PathVariable id: Long, model: Model): String {
        var res: HashMap<String, String> = HashMap()
        val book: Booking = bookingService.getById(id)
        if (isApproved) {
            book.status = " Accepted"
        } else {
            book.status = "Rejected"
        }
        bookingService.updateBooking(book)
        model.addAttribute("success", "Booking updated successfully")
        return "redirect:/photoBookings"
    }
}