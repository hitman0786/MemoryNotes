package com.android.core.usecases

import com.android.core.base.BaseUseCase
import com.android.core.model.Note
import com.android.core.repositories.NoteRepository

class GetNote(private val noteRepository: NoteRepository): BaseUseCase<Long>() {
    override suspend operator fun invoke(data: Long): Note? = noteRepository.getNote(data)
}