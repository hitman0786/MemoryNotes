package com.android.data.framework

import com.android.core.usecases.AddNote
import com.android.core.usecases.GetAllNotes
import com.android.core.usecases.GetNote
import com.android.core.usecases.RemoveNote

data class UseCases(
        val addNote: AddNote,
        val getNote: GetNote,
        val getAllNotes: GetAllNotes,
        val removeNote: RemoveNote
)