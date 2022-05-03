package com.example.mycontactlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tel") val tel: String,
    @ColumnInfo(name = "can_be_checked_off") val canBeCheckedOff: Boolean,
    @ColumnInfo(name = "is_checked_off") val isCheckedOff: Boolean,
    @ColumnInfo(name = "tag_id") val tagId: Long,
    @ColumnInfo(name = "in_trash") val isInTrash: Boolean,
    @ColumnInfo(name = "is_verify") val isVerify: Boolean,




    ) {
    companion object {
        val DEFAULT_CONTACTS = listOf(
            ContactDbModel(1, "Prinkarn Ditsomboon", "0833607875", false, false, 1, false,false),
            ContactDbModel(2, "Weawmanee Ditsomboon", "0812630940", false, false, 2, false,false),
            ContactDbModel(3, "Prasent Ditsomboon", "0819321654", false, false, 3, false,false),
            ContactDbModel(
                4,
                "Weawmanee Ditsomboon",
                "0899999999",
                false,
                false,
                4,
                false,
                false
            ),
        )
    }
}
