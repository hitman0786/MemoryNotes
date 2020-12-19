package com.android.data.framework.db

import android.content.Context
import com.android.core.model.Note
import com.android.core.repositories.NoteDataSource

class RoomNoteDataSource(val context: Context): NoteDataSource {
    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntry(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntry(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntry().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.removeNoteEntry(NoteEntity.fromNote(note))
}