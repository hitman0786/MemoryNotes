package com.android.data.framework.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.core.model.Note
import com.android.core.repositories.NoteRepository
import com.android.core.usecases.AddNote
import com.android.core.usecases.GetAllNotes
import com.android.core.usecases.GetNote
import com.android.core.usecases.RemoveNote
import com.android.data.framework.db.RoomNoteDataSource
import com.android.data.framework.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val useCases = UseCases(
            AddNote(repository),
            GetNote(repository),
            GetAllNotes(repository),
            RemoveNote(repository)
    )

    private val notes = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> = notes

    fun getNotes() {
        coroutineScope.launch {
            val data = useCases.getAllNotes()
            notes.postValue(data)
        }
    }


}