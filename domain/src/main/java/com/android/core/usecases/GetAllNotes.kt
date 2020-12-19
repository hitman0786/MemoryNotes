package com.android.core.usecases

import com.android.core.repositories.NoteRepository

class GetAllNotes(private val noteRepository: NoteRepository) {
    suspend operator fun invoke() = noteRepository.getAllNote()
}