package com.example.notesapp.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class NoteRepository(
    val scope: CoroutineScope,
    private val dao: NoteDao
) {
    val notesAsc = dao.getAllNotesAsc()
    val notesDesc = dao.getAllNotesDesc()

    fun insertNote(text: String) {
        scope.launch {
            val now = Date()
            dao.insertNote(NoteEntity(now, text))
        }
    }

    fun updateNote(note: NoteEntity, text: String) {
        scope.launch {
            note.timestamp = Date()
            note.text = text
            dao.updateNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        scope.launch {
            dao.deleteNote(note)
        }
    }
}