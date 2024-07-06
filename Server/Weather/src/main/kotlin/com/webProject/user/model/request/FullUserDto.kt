package com.webProject.user.model.request

import com.webProject.user.model.UserType
import java.util.Date

class FullUserDto() {
    var name: String? = null
    var active: Boolean? = null
    var type: UserType? = null
    var createdDate: Date? = null
}