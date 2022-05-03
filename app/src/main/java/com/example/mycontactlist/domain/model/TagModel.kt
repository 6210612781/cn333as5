package com.example.mycontactlist.domain.model

import com.example.mycontentlist.database.TagDbModel

data class TagModel(
    val id: Long,
    val name: String,
    val hex: String
) {
    companion object {
        val DEFAULT = with(TagDbModel.DEFAULT_TAG) { TagModel(id, name, hex) }
    }
}
