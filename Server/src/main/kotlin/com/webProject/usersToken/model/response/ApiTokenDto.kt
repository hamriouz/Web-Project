package com.webProject.usersToken.model.response

import java.util.Date
import java.util.UUID

class ApiTokenDto {
    var name: String? = null
    var expireDate: Date? = null
    var token: String? = null
}