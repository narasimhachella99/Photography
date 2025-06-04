package com.example.photography.controller

import com.example.photography.model.Booking
import com.example.photography.model.Photographer
import com.example.photography.model.User
import com.example.photography.service.IBookingService
import com.example.photography.service.IPhotographerService
import com.example.photography.service.IUserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController(
    var userService: IUserService,
    var bookingService: IBookingService,
    var photographerService: IPhotographerService,
    var req: HttpServletRequest,
) {

    // Here we  are creating shortcut for pages


    private val USER_BOOKINGS: String = "user/viewBookings"
    private val BOOK: String = "user/viewPhotographers"
    private val USER_HOME: String = "user/home"
    private val ADMIN_HOME: String = "admin/home"
    private var USER_LOG: String = "user/login"
    private var USER_REG: String = "user/register"


    /// User can register with own credentials
    /// Here user have some conditions to get registration
    /// By through Below conditions user can register


    @PostMapping("/userRegister")
    private fun userReg(
        model: Model, name: String, email: String, password: String, phoneNumber: Long, address: String,
        city: String, state: String, country: String
    ): String {
        if (email == "" || password == "" || name == "") {
            model.addAttribute("error", "please fill out all the fields")
        } else {
            val user: User = User(
                name = name,
                email = email,
                password = password,
                phoneNumber = phoneNumber,
                address = address,
                city = city,
                state = state,
                country = country
            )
            if (userService.getByEmail(user.email) != null) {
                model.addAttribute("error", "user already exist");
                return USER_REG
            } else {
                userService.addUser(user)
                model.addAttribute("success", "registration done successfully")
                req.session.setAttribute("user", user)
                return USER_REG
            }

        }
        return USER_REG;
    }


    /// User can login with registered credentials
    // By through below conditions

    @PostMapping("/userLogin")
    private fun useLogin(model: Model, email: String, password: String): String {
        if (email == "admin@gmail.com" && password == "admin") {
//            val user: User = req.session.getAttribute("user") as User
            model.addAttribute("success", "admin login successfully")
            return ADMIN_HOME
        }
        if (email == "" || password == "") {
            model.addAttribute("error", "please fil out all the fields")
            return USER_LOG
        } else {
            val user: User? = userService.getByEmailAndPassword(email, password)
            if (user != null) {
                req.session.setAttribute("user", user)
                return "redirect:/userHome"
            }
        }
        return USER_LOG;
    }

    /// User can get his home page after login

    @GetMapping("/userHome")
    private fun userHome(model: Model): String {
        var user: User = req.session.getAttribute("user") as User
        if (user != null) {
            model.addAttribute("user", user)
            return USER_HOME
        } else {
            return "user/login"
        }
        return USER_HOME
    }


    ///here user can book the photographer by using this endpoint

    @PostMapping("/book")
    fun bookPhotographer(
        model: Model,
        pEmail: String,
        pAddress: String,
        pPhoneNumber: Long,
        pExperience: Long
    ): String {
        try {
            val user: User = req.session.getAttribute("user") as User
            val user1:User=userService.getById(user.id)
            val booking: Booking =
                Booking(
                    userName = user1.name,
                    pEmail = pEmail,
                    pAddress = pAddress,
                    pPhoneNumber = pPhoneNumber,
                    pExperience = pExperience
                )
            bookingService.addBooking(booking)
            req.session.setAttribute("booking", booking)
            model.addAttribute("success", "Photographer booked successfully")
            return "user/viewPhotographers"
        } catch (e: Exception) {
            model.addAttribute("error", "Not Booked !!!!")
            return "user/viewPhotographers"
        }


    }


    // It returns all the photographers for user

    @GetMapping("/userPhotographers")
    fun userPhoto(model: Model): String {
        var photographer: Photographer = req.session.getAttribute("photographer") as Photographer
        model.addAttribute("photographer", photographerService.allPhotographers())
        return BOOK
    }


    ///// It returns all the bookings for user

    @GetMapping("/userBookings")
    fun allBookings(model: Model): String {
        var user:User=req.session.getAttribute("user") as User
        model.addAttribute("booking", bookingService.allBookings().stream().filter { i -> i.userName == user.name });
        return USER_BOOKINGS
    }

    /// Here this endpoint gives the status of photographer
    // whether photographer is accepted or not

    @PostMapping("/status")
    fun status(model: Model , isAccepted:Boolean): String {
        var  booking:Booking=req.session.getAttribute("booking") as Booking
        var book:Booking=bookingService.getById(booking.id)
        booking.status = "Accepted"
        model.addAttribute("success", "booking accepted successfully")
        bookingService.addBooking(booking)
        return "photographer/viewRequests"

    }


    // we are getting all users

    @GetMapping("/users")
    fun allUsers(): ResponseEntity<*> {
        try {
            return ResponseEntity(userService.allUsers(), HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    // we are getting selected user

    @GetMapping("/users/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> {
        try {
            return ResponseEntity(userService.getById(id), HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    // we are adding user with required details
    @PostMapping("/users")
    fun addUsers(@RequestBody user: User): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        return try {
            userService.addUser(user)
            res["success"] = "Details added successfully"
            ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res["error"] = "Details not added"
            ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    // we are updating the details


    @PatchMapping("/users/{id}")
    fun updateUser(@RequestBody user: User, @PathVariable id: Long): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        try {
            val u: User = userService.getById(id)

            val updatedUser = userService.updateUser(
                u.copy(
                    name = user.name ?: u.name,
                    email = user.email ?: u.email,
                    password = user.password ?: u.password,
                    phoneNumber = user.phoneNumber ?: u.phoneNumber
                )
            )
            userService.updateUser(updatedUser)
            res.put("success", "user updated successfully")
            return ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res.put("error", "user not updated")
            return ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // we are deleting selected user


    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<*> {
        val res: HashMap<String, String> = HashMap()
        try {
            userService.deleteUser(id)
            res.put("success", "user deleted successfully")
            return ResponseEntity(res, HttpStatus.OK)
        } catch (e: Exception) {
            res.put("error", "User not deleted")
            return ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}