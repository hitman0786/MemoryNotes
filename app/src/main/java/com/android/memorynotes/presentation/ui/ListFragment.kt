package com.android.memorynotes.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.memorynotes.R
import com.android.data.framework.viewmodels.NoteListViewModel
import com.android.memorynotes.presentation.adapter.NoteListAdapter
import com.android.memorynotes.presentation.common.ListActionListener
import com.android.memorynotes.presentation.common.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), ListActionListener {
    lateinit var noteListView: RecyclerView
    lateinit var addNoteBT: FloatingActionButton
    lateinit var loadingView: ProgressBar
    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter(arrayListOf(), this)
    }

    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNoteBT = view.findViewById(R.id.addNoteBT)
        loadingView = view.findViewById(R.id.loadingView)
        noteListView = view.findViewById(R.id.noteListView)

        noteListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }

        addNoteBT.setOnClickListener { v ->  Navigation.gotoNoteDetails(view = v)}

        viewModel.noteList.observe(requireActivity(), { notes ->
            loadingView.visibility = View.GONE
            noteListView.visibility = View.VISIBLE
            noteListAdapter.updateNoteList(notes.sortedByDescending { it.updateTime })
        })
    }

    override fun onResume() {
        super.onResume()
        //Get all notes
        viewModel.getNotes()
    }


    override fun onClickListItem(id: Long, view: View) {
        Navigation.gotoNoteDetails(id, view)
    }
}