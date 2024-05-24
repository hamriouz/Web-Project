package com.webProject.user.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.MessageDigest


@Entity
@Table(name = "users")
class User: UserDetails {
    @Id
    @GeneratedValue
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var name: String? = null

    @Column(nullable = false)
    private var password: String? = null

    @Column
    var encryptedPassword: String? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType? = UserType.USER

    @Column(nullable = false)
    var active: Boolean? = false
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String {
        return this.password!!
    }

    override fun getUsername(): String {
        return this.name!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        return this.active!!
    }

    fun setPassword(password: String) {
        this.password = hashPassword(password)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hashPassword(password: String): String{
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(password.toByteArray())
        return digest.toHexString()
    }
}