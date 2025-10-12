package com.example.notesapp.viewmodel

import android.app.Application
import com.example.notesapp.model.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.room.Room
import com.example.notesapp.model.NoteRepository

/**
 * Creates the database and repository in singleton fashion
 * and provides a unified scope.
 */
class NoteApplication : Application() {
    val scope = CoroutineScope(SupervisorJob())

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    val noteRepository by lazy { NoteRepository(scope, db.noteDao()) }
}