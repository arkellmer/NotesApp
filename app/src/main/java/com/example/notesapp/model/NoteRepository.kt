package com.example.notesapp.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

/**
 * A note repository which exposes the functionality of a note database.
 *
 * @property scope the scope of the application.
 * @property dao the data access object of the database.
 * @see NoteDatabase
 */
class NoteRepository(
    val scope: CoroutineScope,
    private val dao: NoteDao
) {
    val notesAsc = dao.getAllNotesAsc()
    val notesDesc = dao.getAllNotesDesc()

    /**
     * Inserts a note into the database.
     *
     * @property text the text of the note.
     */
    fun insertNote(text: String) {
        scope.launch {
            val now = Date()
            dao.insertNote(NoteEntity(now, text))
        }
    }

    /**
     * Updates a note in the database.
     *
     * @property note the note to be updated.
     * @property text the new text of the note.
     */
    fun updateNote(note: NoteEntity, text: String) {
        scope.launch {
            note.timestamp = Date()
            note.text = text
            dao.updateNote(note)
        }
    }

    /**
     * Deletes a note in the database.
     *
     * @property note the note to be deleted.
     */
    fun deleteNote(note: NoteEntity) {
        scope.launch {
            dao.deleteNote(note)
        }
    }
}