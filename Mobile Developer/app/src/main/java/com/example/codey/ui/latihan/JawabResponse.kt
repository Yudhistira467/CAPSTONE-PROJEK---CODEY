package com.example.codey.ui.latihan

import com.google.gson.annotations.SerializedName

data class JawabResponse(

	@field:SerializedName("answers")
	val answers: List<AnswersItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class AnswersItem(

	@field:SerializedName("materi")
	val materi: String? = null,

	@field:SerializedName("questionId")
	val questionId: String? = null,

	@field:SerializedName("userAnswer")
	val userAnswer: String? = null
)
