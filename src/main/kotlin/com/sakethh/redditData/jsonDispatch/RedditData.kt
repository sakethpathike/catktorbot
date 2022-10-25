package com.sakethh.redditData.jsonDispatch

import kotlinx.serialization.Serializable

@Serializable
data class RedditData(
    val `data`: Data
)