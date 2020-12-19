package com.android.data.framework.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = NoteRepository(RoomNoteDataSource(application))

    private val useCases = UseCases(
            AddNote(repository),
            GetNote(repository),
            GetAllNotes(repository),
            RemoveNote(repository)
    )

    private val saved = MutableLiveData<Boolean>()
    val dataSaved: LiveData<Boolean> = saved

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    private val currentNote = MutableLiveData<Note>()
    val currentNoteData: LiveData<Note?> = currentNote

    fun getNote(id: Long){
        viewModelScope.launch {
            val note = useCases.getNote(id)
            currentNote.postValue(note)
        }
    }

    fun removeNote(note: Note){
        viewModelScope.launch {
            Log.e("Test", "note is :- $note")
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }
}