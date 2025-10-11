package com.example.notesapp.model

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase

/**
 * A note database using a NoteDao and built on NoteEntities.
 *
 * @see NoteDao
 * @see NoteEntity
 */
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}