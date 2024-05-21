package com.webProject.usersToken.model.request

import java.util.Date

class CreateApiTokenRequest {
    var name: String? = null
    var expireDate: Date? = null
}