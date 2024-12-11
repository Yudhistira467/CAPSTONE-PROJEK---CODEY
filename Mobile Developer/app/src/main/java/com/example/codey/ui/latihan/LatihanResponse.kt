package com.example.codey.ui.latihan

import com.google.gson.annotations.SerializedName

data class LatihanResponse(

	@field:SerializedName("quiz")
	val quiz: List<QuizItem?>? = null
)

data class QuizItem(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("materi")
	val materi: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("correct_answer")
	val correctAnswer: String? = null,

	@field:SerializedName("question_id")
	val questionId: String? = null,

	@field:SerializedName("attempts")
	val attempts: String? = null
)
