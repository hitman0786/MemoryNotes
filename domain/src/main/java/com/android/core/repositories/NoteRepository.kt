package com.android.core.repositories

import com.android.core.model.Note

class NoteRepository(private val noteDataSource: NoteDataSource) {
    suspend fun addNote(note: Note) = noteDataSource.add(note)

    suspend fun getNote(id: Long) = noteDataSource.get(id)

    suspend fun getAllNote() = noteDataSource.getAll()

    suspend fun removeNote(note: Note) = noteDataSource.remove(note)
}