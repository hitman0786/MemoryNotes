package com.android.core.usecases

import com.android.core.base.BaseUseCase
import com.android.core.model.Note
import com.android.core.repositories.NoteRepository

class AddNote(private val noteRepository: NoteRepository): BaseUseCase<Note>() {
    override suspend operator fun invoke(data: Note): Unit = noteRepository.addNote(data)
}