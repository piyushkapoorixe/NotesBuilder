package com.piyush.notes;

import androidx.room.Dao;
import androidx.room.Query;

import java.sql.Blob;
import java.util.List;

// interacting with the Table (Note.java)

// Dao is data access object
// making it interface since compiler would write some code so we dont have to
@Dao
public interface NoteDao {

    @Query("INSERT INTO notes (contents) VALUES ('')")
    void create();

    @Query("SELECT * from notes")
    List<Note> getAllNotes();

    // Parameter Binding -> :contents will replace it with the value of the parameter that is inside the method declaration
    @Query("UPDATE notes SET contents = :contents WHERE id = :id")
    void save(String contents, int id);

    @Query("DELETE from notes WHERE id = :id")
    void delete(int id);
}
