package com.app.omdbdemo.model

import com.google.gson.annotations.SerializedName

data class RattingResponse(
    @field:SerializedName("Source")
    val source: String? = null,

    @field:SerializedName("Value")
    val value: String? = null,
)
