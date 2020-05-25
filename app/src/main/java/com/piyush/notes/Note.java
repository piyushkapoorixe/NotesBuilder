package com.piyush.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "contents")
    public String contents;

    //@ColumnInfo(name = "columnname",typeAffinity = ColumnInfo.BLOB)
    //private byte[] yourfield;

    //@ColumnInfo(name = "path")
    public String path;
}
