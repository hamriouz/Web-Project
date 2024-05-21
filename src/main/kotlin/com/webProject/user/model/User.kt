package com.webProject.user.model

import jakarta.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType? = UserType.USER

    @Column(nullable = false)
    var active: Boolean? = false

}