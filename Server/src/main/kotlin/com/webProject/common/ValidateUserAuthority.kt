package com.webProject.common

import org.springframework.security.core.GrantedAuthority

class ValidateUserAuthority(
    private val authority: String,
) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }

}