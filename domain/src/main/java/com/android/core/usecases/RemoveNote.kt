package com.android.core.usecases

import com.android.core.base.BaseUseCase
import com.android.core.model.Note
import com.android.core.repositories.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository): BaseUseCase<Note>() {
    override suspend operator fun invoke(data: Note) = noteRepository.removeNote(data)
}