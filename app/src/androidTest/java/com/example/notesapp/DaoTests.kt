package com.example.notesapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notesapp.model.NoteDao
import com.example.notesapp.model.NoteDatabase
import com.example.notesapp.model.NoteEntity
import kotlinx.coroutines.delay
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
        assertTrue(dao.getAllNotesAsc().first().isEmpty())
        assertTrue(dao.getAllNotesDesc().first().isEmpty())
    }

    @Test
    fun testInsertOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))
        val expected = NoteEntity(now, "test note")

        assertEquals(expected, dao.getAllNotesAsc().first()[0])
        assertEquals(expected, dao.getAllNotesDesc().first()[0])
        assertEquals(1, dao.getAllNotesAsc().first()[0].id)
        assertEquals(1, dao.getAllNotesDesc().first()[0].id)
        assertEquals(1, dao.getAllNotesAsc().first().size)
        assertEquals(1, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testInsertMany() = runBlocking {
        val notes = mutableListOf<NoteEntity>()
        for (i in 0..99) {
            val now = Date()
            dao.insertNote(NoteEntity(now, "$i"))
            val expected = NoteEntity(now, "$i")
            notes.add(expected)
        }

        for (i in 0..99) {
            assertEquals(notes[i], dao.getAllNotesAsc().first()[i])
            assertEquals(notes[99 - i], dao.getAllNotesDesc().first()[i])
            assertEquals(i + 1, dao.getAllNotesAsc().first()[i].id)
            assertEquals(100 - i, dao.getAllNotesDesc().first()[i].id)
        }
        assertEquals(100, dao.getAllNotesAsc().first().size)
        assertEquals(100, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testUpdateOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))

        delay(1000)
        val updatedNote = dao.getAllNotesAsc().first()[0]
        val later = Date()
        updatedNote.timestamp = later
        updatedNote.text = "updated test note"
        dao.updateNote(updatedNote)
        val expected = NoteEntity(later, "updated test note")

        assertEquals(expected, dao.getAllNotesAsc().first()[0])
        assertEquals(expected, dao.getAllNotesDesc().first()[0])
        assertEquals(1, dao.getAllNotesAsc().first()[0].id)
        assertEquals(1, dao.getAllNotesDesc().first()[0].id)
        assertEquals(1, dao.getAllNotesAsc().first().size)
        assertEquals(1, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testUpdateMiddle() = runBlocking {
        for (i in 0..99) {
            val now = Date()
            dao.insertNote(NoteEntity(now, "$i"))
        }

        val updatedNote = dao.getAllNotesAsc().first()[49]
        val now = Date()
        updatedNote.timestamp = now
        updatedNote.text = "updated note"
        dao.updateNote(updatedNote)
        val expected = NoteEntity(now, "updated note")

        assertEquals(expected, dao.getAllNotesAsc().first()[49])
        assertEquals(expected, dao.getAllNotesDesc().first()[50])
        assertEquals(50, dao.getAllNotesAsc().first()[49].id)
        assertEquals(50, dao.getAllNotesDesc().first()[50].id)
        assertEquals(100, dao.getAllNotesAsc().first().size)
        assertEquals(100, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testUpdateMissing() = runBlocking {
        val now = Date()
        delay(1000)
        val wrongTime = Date()
        dao.insertNote(NoteEntity(now, "text"))
        dao.updateNote(NoteEntity(now, "wrong text"))
        dao.updateNote(NoteEntity(wrongTime, "text"))
        dao.updateNote(NoteEntity(wrongTime, "wrong text"))
        val expected = NoteEntity(now, "text")

        assertEquals(expected, dao.getAllNotesAsc().first()[0])
        assertEquals(expected, dao.getAllNotesDesc().first()[0])
        assertEquals(1, dao.getAllNotesAsc().first()[0].id)
        assertEquals(1, dao.getAllNotesDesc().first()[0].id)
        assertEquals(1, dao.getAllNotesAsc().first().size)
        assertEquals(1, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testDeleteOne() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))

        val deletedNote = dao.getAllNotesAsc().first()[0]
        dao.deleteNote(deletedNote)

        assertEquals(0, dao.getAllNotesAsc().first().size)
        assertEquals(0, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testDeleteMany() = runBlocking {
        for (i in 0..99) {
            val now = Date()
            dao.insertNote(NoteEntity(now, "$i"))
        }

        for (i in 0..99) {
            val deletedNote = dao.getAllNotesAsc().first()[0]
            dao.deleteNote(deletedNote)
        }

        assertEquals(0, dao.getAllNotesAsc().first().size)
        assertEquals(0, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testDeleteMiddle() = runBlocking {
        for (i in 0..99) {
            val now = Date()
            dao.insertNote(NoteEntity(now, "$i"))
        }

        val deletedNote = dao.getAllNotesAsc().first()[49]
        dao.deleteNote(deletedNote)

        for (note in dao.getAllNotesAsc().first()) {
            assertNotEquals(deletedNote, note)
        }
        for(note in dao.getAllNotesDesc().first()) {
            assertNotEquals(deletedNote, note)
        }
        assertEquals(99, dao.getAllNotesAsc().first().size)
        assertEquals(99, dao.getAllNotesAsc().first().size)
    }

    @Test
    fun testDeleteMissing() = runBlocking {
        val now = Date()
        dao.deleteNote(NoteEntity(now, "test note"))

        assertEquals(0, dao.getAllNotesAsc().first().size)
        assertEquals(0, dao.getAllNotesDesc().first().size)
    }

    @Test
    fun testDeleteTwice() = runBlocking {
        val now = Date()
        dao.insertNote(NoteEntity(now, "test note"))

        val deletedNote = dao.getAllNotesAsc().first()[0]
        dao.deleteNote(deletedNote)
        dao.deleteNote(deletedNote)

        assertEquals(0, dao.getAllNotesAsc().first().size)
        assertEquals(0, dao.getAllNotesDesc().first().size)
    }
}