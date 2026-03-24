package com.example.studysnap.model

/**
 * Represents a multiple-choice quiz question.
 */
data class QuizQuestion(
    val id: Int = 0,
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)
