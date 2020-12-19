package com.android.core.usecases

import com.android.core.model.Note
import com.android.core.repositories.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}