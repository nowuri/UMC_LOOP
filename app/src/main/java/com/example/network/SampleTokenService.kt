package com.example.network

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class SampleTokenService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    companion object {
        val ISSURE = "BEANBROKER"
        val SCRET = "1234"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun publish(id: Long, authType: String): String? {
        //60 * 60 * 24
        val now = LocalDateTime.now()
        val expiredAt = now.plusSeconds(86400)

        return JWT.create()
            .withClaim("id", id)
            .withClaim("authType", authType)
            .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .withIssuer(ISSURE)
            .withExpiresAt(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
            .sign(Algorithm.HMAC256(SCRET))
    }

    fun decodeToken(token : String) {

        val jwt = JWT.require(Algorithm.HMAC256(SCRET))
            .withIssuer(ISSURE)
            .build()
            .verify(token)
    }
}