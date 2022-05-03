package com.example.mycontentlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "hex") val hex: String,
    @ColumnInfo(name = "name") val name: String
) {
    companion object {
        val DEFAULT_TAGS = listOf(
            TagDbModel(1, "#EFA523","Friend"),
            TagDbModel(2, "#2C2F30","Work"),
            TagDbModel(3, "#76C4DD","School"),
            TagDbModel(4, "#ECDA24","Family"),
            TagDbModel(5, "#DA3847","Girlfriend"),
            )
        val DEFAULT_TAG = DEFAULT_TAGS[0]
    }
}