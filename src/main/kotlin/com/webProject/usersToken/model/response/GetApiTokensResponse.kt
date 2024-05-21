package com.webProject.usersToken.model.response

import com.webProject.usersToken.model.ApiTokenDto

class GetApiTokensResponse {
    var tokens: List<ApiTokenDto>? = null
    var count: Int? = null
}