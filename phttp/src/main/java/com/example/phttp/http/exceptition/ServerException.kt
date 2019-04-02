package com.example.phttp.http.exceptition

class ServerException() : RuntimeException() {

    var code: Int = 0
    var msg = ""

    constructor(code: Int, msg: String) : this() {
        this.code = code
        this.msg = msg
    }

}