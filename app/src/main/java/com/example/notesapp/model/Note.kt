package com.example.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
                val noteTitleX: String,
                val noteDescX: String):Parcelable

//Parcelization is a mechanism that converts complex data objects
//into simple format that can be transfer b/w activities or fragments
