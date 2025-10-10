package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class NoteEntity(
    var date: Date,
    var text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
