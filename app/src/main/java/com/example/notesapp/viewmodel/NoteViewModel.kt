package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notesapp.model.NoteEntity
import com.example.notesapp.model.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * A ViewModel which manages the note repository and provides it for the View.
 *
 * @property repository the repository of the application.
 * @see NoteRepository
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val notesAsc: StateFlow<List<NoteEntity>> =
        repository.notesAsc.stateIn(
            scope = repository.scope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )

    val notesDesc: StateFlow<List<NoteEntity>> =
        repository.notesDesc.stateIn(
            scope = repository.scope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )

    /**
     * Passes a note to the repository.
     *
     * @property text the text of the note.
     */
    fun insertNote(text: String) {
        repository.insertNote(text)
    }

    /**
     * Passes a note to be updated to the repository.
     *
     * @property note the note to be updated.
     * @property text the updated text of the note.
     */
    fun updateNote(note: NoteEntity, text: String) {
        repository.updateNote(note, text)
    }

    /**
     * Passes a note to be deleted to the repository.
     *
     * @property note the note to be deleted.
     */
    fun deleteNote(note: NoteEntity) {
        repository.deleteNote(note)
    }
}

/**
 * Provides a ViewModel and maintains singleton design.
 */
object NoteViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NoteViewModel(
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        as NoteApplication).noteRepository
            )
        }
    }
}