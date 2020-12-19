package com.android.memorynotes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.core.model.Note
import com.android.memorynotes.R
import com.android.memorynotes.presentation.common.ListActionListener
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteListAdapter(private val noteList: ArrayList<Note>?, val listActionListener: ListActionListener): RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        return NoteListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        noteList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return noteList?.size ?: 0
    }

    fun updateNoteList(newNote: List<Note>){
        noteList?.clear()
        noteList?.addAll(newNote)
        notifyDataSetChanged()
    }

    inner class NoteListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val noteLayout: MaterialCardView = view.findViewById(R.id.noteLayout)
        private val titleView: TextView = view.findViewById(R.id.titleView)
        private val contentView: TextView = view.findViewById(R.id.contentView)
        private val dateView: TextView = view.findViewById(R.id.dateView)

        fun bind(note: Note){
            titleView.text = note.title
            contentView.text = note.content
            val sdf = SimpleDateFormat("MMM dd, HH:MM:SS")
            val resultDate = Date(note.updateTime)
            dateView.text = "Last updated: ${sdf.format(resultDate)}"

            noteLayout.setOnClickListener {
                v -> listActionListener.onClickListItem(note.id, v)
            }
        }
    }
}