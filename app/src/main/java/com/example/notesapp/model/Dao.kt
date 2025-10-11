package com.example.notesapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * A note data access object for a note database.
 *
 * @see NoteDatabase
 * @see NoteEntity
 */
@Dao
interface NoteDao {
    /**
     * Inserts a note into the database.
     *
     * @property note the note entity to be inserted.
     */
    @Insert
    suspend fun insertNote (note: NoteEntity)

    /**
     * Updates a note in the database.
     *
     * @property note the note entity to be updated.
     */
    @Update
    suspend fun updateNote(note: NoteEntity)

    /**
     * Gets all notes from the database, ascending order.
     */
    @Query("select * from notes order by id asc")
    fun getAllNotesAsc(): Flow<List<NoteEntity>>

    /**
     * Gets all notes from the database, descending order.
     */
    @Query("select * from notes order by id desc")
    fun getAllNotesDesc(): Flow<List<NoteEntity>>

    /**
     * Deletes a note from the database.
     *
     * @property note the note to be deleted.
     */
    @Delete
    fun deleteNote(note: NoteEntity)
}