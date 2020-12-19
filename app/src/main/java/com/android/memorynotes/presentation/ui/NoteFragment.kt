package com.android.memorynotes.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.core.model.Note
import com.android.memorynotes.R
import com.android.data.framework.util.hideKeyboard
import com.android.data.framework.viewmodels.NoteViewModel
import com.android.memorynotes.presentation.common.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment() {
    private var noteId: Long = 0L
    lateinit var titleView: EditText
    lateinit var contentView: EditText
    lateinit var saveNoteBT: FloatingActionButton
    private val viewModel: NoteViewModel by viewModels()

    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView = view.findViewById(R.id.titleView)
        contentView = view.findViewById(R.id.contentView)
        saveNoteBT = view.findViewById(R.id.saveNoteBT)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if(noteId != 0L){
            viewModel.getNote(noteId)
        }

        saveNoteBT.setOnClickListener {
            if (titleView.text.toString().isNotEmpty() || contentView.text.toString().isNotEmpty()) {
                val time = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.backToPreviousScreen(it)
            }
        }

        observeViewModel(view)
    }

    private fun observeViewModel(view: View) {
        viewModel.dataSaved.observe(requireActivity(), {
            if(it) {
                Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                view.hideKeyboard()
                Navigation.backToPreviousScreen(view)
            }else {
                Toast.makeText(context, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNoteData.observe(requireActivity(), { note ->
            note?.run {
                currentNote = note
                titleView.setText(title)
                contentView.setText(content)
                Log.e("Test", "currentNote is :- $currentNote")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    showConfirmDialog(requireActivity(), currentNote)
                }
            }
        }
        return true
    }

    private fun showConfirmDialog(activity: FragmentActivity, note: Note) {
        AlertDialog.Builder(activity)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want delete this note?")
                .setPositiveButton("Yes") { _, _ -> viewModel.removeNote(note) }
                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
    }
}