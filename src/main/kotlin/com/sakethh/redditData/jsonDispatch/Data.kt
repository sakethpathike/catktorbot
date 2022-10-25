package com.sakethh.redditData.jsonDispatch

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val children: List<Children>
)