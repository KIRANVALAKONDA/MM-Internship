package com.example.studysnap.model

/**
 * Represents a single flashcard with a question and answer.
 */
data class Flashcard(
    val id: Int = 0,
    val question: String,
    val answer: String
)
