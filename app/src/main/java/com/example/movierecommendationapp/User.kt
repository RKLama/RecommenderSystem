package com.example.movierecommendationapp

class User {
    var name: String? = null
    var phoneNo: String? = null
    var email: String? = null
    var password: String? = null
    var age = 0

    constructor() {}
    constructor(name: String?, phoneNo: String?, email: String?, password: String?, age: Int) {
        this.name = name
        this.phoneNo = phoneNo
        this.email = email
        this.password = password
        this.age = age
    }
}