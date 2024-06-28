package com.webProject.user.model.request

import com.webProject.user.model.UserType

class FullUserDto() {
    var name: String? = null
    var active: Boolean? = null
    var type: UserType? = null
}