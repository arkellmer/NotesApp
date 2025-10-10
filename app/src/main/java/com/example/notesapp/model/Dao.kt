package com.example.notesapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote (note: NoteEntity)

    @Query("select * from notes order by id desc")
    fun getAllNotesDesc(): Flow<List<NoteEntity>>
}