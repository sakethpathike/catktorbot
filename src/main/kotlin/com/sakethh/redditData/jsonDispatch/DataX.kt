package com.sakethh.redditData.jsonDispatch

import kotlinx.serialization.Serializable

@Serializable
data class DataX(
    val author: String,
    val is_video: Boolean,
    val over_18: Boolean,
    val permalink: String,
    val subreddit: String,
    val title: String,
    val url: String
)