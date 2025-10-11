package com.example.notesapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.model.NoteDao
import com.example.notesapp.model.NoteDatabase
import com.example.notesapp.model.NoteEntity
import com.example.notesapp.model.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTests {

    private lateinit var repository: NoteRepository
    private lateinit var dao: NoteDao
    private lateinit var db: NoteDatabase
    private val scope = CoroutineScope(Job())

    @Before
    fun createRepository() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.noteDao()
        repository = NoteRepository(scope, dao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGetNotesEmpty() {
        scope.launch {
            assertEquals(0, repository.notesAsc.first().size)
            assertEquals(0, repository.notesDesc.first().size)
        }
    }

    @Test
    fun testInsertNote() {
        repository.insertNote("test note")
        val expected = NoteEntity(Date(), "test note")
        scope.launch {
            assertEquals(expected, repository.notesAsc.first()[0])
            assertEquals(expected, repository.notesDesc.first()[0])
            assertEquals(1, repository.notesAsc.first()[0].id)
            assertEquals(1, repository.notesAsc.first()[0].id)
            assertEquals(1, repository.notesAsc.first().size)
            assertEquals(1, repository.notesDesc.first().size)
        }
    }

    @Test
    fun testUpdateNote() {
        repository.insertNote("test note")

        scope.launch {
            val updatedNote = repository.notesAsc.first()[0]
            repository.updateNote(updatedNote, "updated test note")
            val expected = NoteEntity(Date(), "updated test note")

            assertEquals(expected, repository.notesAsc.first()[0])
            assertEquals(expected, repository.notesDesc.first()[0])
            assertEquals(1, repository.notesAsc.first()[0].id)
            assertEquals(1, repository.notesAsc.first()[0].id)
            assertEquals(1, repository.notesAsc.first().size)
            assertEquals(1, repository.notesDesc.first().size)
        }
    }

    @Test
    fun testDeleteNote() {
        repository.insertNote("test note")

        scope.launch {
            val deletedNote = repository.notesAsc.first()[0]
            repository.deleteNote(deletedNote)

            assertEquals(0, repository.notesAsc.first().size)
            assertEquals(0, repository.notesDesc.first().size)
        }
    }
}