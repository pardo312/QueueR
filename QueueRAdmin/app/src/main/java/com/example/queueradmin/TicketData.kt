package com.example.queueradmin

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TicketData (
    val Horario: String = "",
    val ID: String = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Horario" to Horario,
            "ID" to ID
        )
    }
}