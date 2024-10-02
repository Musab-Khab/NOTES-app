package com.example.note

import java.io.Serializable

data class Note(
    val id: Int,
    val title: String,
    val content: String
) : Serializable
