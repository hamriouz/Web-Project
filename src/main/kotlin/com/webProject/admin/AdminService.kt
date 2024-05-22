package com.webProject.admin

import com.webProject.user.UserRepository
import com.webProject.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val userRepository: UserRepository,
) {
//    fun getUsers(page: Int, pageSize: Int): List<User> {
    fun getUsers(): List<User> {
        // todo add pagination
//        val pageRequest = PageRequest.of(page, pageSize)
        return userRepository.findAll().toList()
    }

}