package com.github.ratrofitwithmvc.model.data

data class UserData(
    var login: String? = null,
    var id: Int? = 0,
    var following: Int? = 0,
    var followers: Int? = 0,
    var created_at: String? = null,
    var location: String? = null,
    var node_id: String? = null,
    var gravatar_id: String? = null,
    var url: String? = null,
    var email: String? = null,
    var repos_url: String? = null,
    var avatar_url: String? = null
)