package com.example.notesapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.model.NoteDao
import com.example.notesapp.model.NoteDatabase
import com.example.notesapp.model.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import java.io.IOException
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DAOTests {

    private lateinit var dao: NoteDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testEmptyDB() = runBlocking {
        assertTrue(dao.getAllNotesDesc().first().isEmpty())
        assertTrue(dao.getAllNotesAsc().first().isEmpty())
    }

    @Test
    fun testInsertOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))
        val expected = NoteEntity(now, "test note")
        assertEquals(expected, dao.getAllNotesDesc().first()[0])
        assertEquals(expected, dao.getAllNotesAsc().first()[0])
        assertEquals(1, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testUpdateOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))
        val updatedNote = dao.getAllNotesDesc().first()[0]
        updatedNote.text = "updated test note"
        dao.updateNote(updatedNote)
        val expected = NoteEntity(now, "updated test note")
        assertEquals(expected, dao.getAllNotesDesc().first()[0])
        assertEquals(expected, dao.getAllNotesAsc().first()[0])
        assertEquals(1, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testDeleteOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))
        val deletedNote = dao.getAllNotesDesc().first()[0]
        dao.deleteNote(deletedNote)
        assertEquals(0, dao.getAllNotesDesc().first().size)
        assertEquals(0, dao.getAllNotesAsc().first().size)
    }

    @Test
    fun testDeleteMissing() = runBlocking {
        val now = Date()
        dao.deleteNote(NoteEntity(now, "test note"))
        assertEquals(0, dao.getAllNotesDesc().first().size)
        assertEquals(0, dao.getAllNotesAsc().first().size)
    }

    @Test
    fun testDeleteTwice() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))
        val deletedNote = dao.getAllNotesDesc().first()[0]
        dao.deleteNote(deletedNote)
        dao.deleteNote(deletedNote)
        assertEquals(0, dao.getAllNotesDesc().first().size)
        assertEquals(0, dao.getAllNotesAsc().first().size)
    }
}