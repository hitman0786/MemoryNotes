package com.android.memorynotes.presentation.common

import android.view.View
import androidx.navigation.findNavController
import com.android.memorynotes.presentation.ui.ListFragmentDirections

object Navigation {

    /**
     * navigate to note details
     */
    fun gotoNoteDetails(id: Long = 0L, view: View) {
        val action = ListFragmentDirections.actionListFragmentToNoteFragment()
        action.noteId = id
        view.findNavController().navigate(action)
    }

    fun backToPreviousScreen(view: View) {
        view.findNavController().popBackStack()
    }
}