package com.example.photography.service.impl

import com.example.photography.model.Photographer
import com.example.photography.repository.IPhotographerRepository
import com.example.photography.service.IPhotographerService
import org.springframework.stereotype.Service

@Service
class PhotographerService(val photographerRepository: IPhotographerRepository) : IPhotographerService {
    override fun allPhotographers(): List<Photographer> {
        return photographerRepository.findAll();
    }

    override fun getById(id: Long): Photographer {
        return photographerRepository.findById(id).get()
    }

    override fun addPhotographer(p: Photographer): Photographer {
        return photographerRepository.save(p)
    }

    override fun updatePhotographer(p: Photographer): Photographer {
        return photographerRepository.save(p)
    }

    override fun deletePhotographer(id: Long) {
        photographerRepository.deleteById(id)
    }

    override fun getByEmail(email: String?): Photographer? {
        return photographerRepository.findByEmail(email)
    }

    override fun getByEmailAndPassword(email: String, password: String): Photographer? {
        return photographerRepository.findByEmailAndPassword(email, password)
    }


}