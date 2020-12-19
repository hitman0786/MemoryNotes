package com.android.core.usecases

import com.android.core.model.Note
import com.android.core.repositories.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}