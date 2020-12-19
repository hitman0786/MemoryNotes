package com.android.data.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntry(noteEntity: NoteEntity)

    @Query("Select * from note where id = :id")
    suspend fun getNoteEntry(id: Long): NoteEntity?

    @Query("Select * from note")
    suspend fun getAllNoteEntry(): List<NoteEntity>

    @Delete
    suspend fun removeNoteEntry(noteEntity: NoteEntity)
}