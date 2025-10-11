package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Entity(tableName = "notes")
data class NoteEntity(
    var timestamp: Date,
    var text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
