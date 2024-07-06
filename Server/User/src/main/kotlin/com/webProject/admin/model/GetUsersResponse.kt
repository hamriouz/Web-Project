package com.webProject.admin.model

import com.webProject.user.model.request.FullUserDto

class GetUsersResponse {
    var users: List<FullUserDto>? = null
    var count: Int? = null
    var totalPageSize: Int? = null

}