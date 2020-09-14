package com.example.madlevel2task2.model

data class QuestionModel(
    var questionText: String,
    var answer: Boolean
) {
    companion object {
        val QUESTIONS = arrayOf(
            "5 + 5 = 10",
            "mixing blue and yellow results into red",
            "amsterdam has more habitants then rotterdam"
        )

        val ANSWERS = arrayOf(
            true,
            false,
            true
        )
    }
}