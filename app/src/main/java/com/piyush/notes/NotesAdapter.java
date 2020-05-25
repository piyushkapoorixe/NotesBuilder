package com.piyush.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;
        TextView textView;
        //ImageView newImageView;
        NoteViewHolder(View view) {
            super(view);

            containerView = view.findViewById(R.id.note_row);
            textView = view.findViewById(R.id.note_row_text);
            //newImageView = view.findViewById(R.id.image_view_new);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);
                    intent.putExtra("path", current.path);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    // three methods are there which we have to implement (binding, creating, get number of items view holders)

    // empty list, to be used in onBindViewHolder method
    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // getting current note using position
        Note current = notes.get(position);

        // setting the text
        holder.textView.setText(current.contents);

        //this view holder has reference to the note
        holder.containerView.setTag(current);

        /*Uri uri = Uri.parse(current.path);
        Glide.with(holder.newImageView.getContext())
                .load(new File(uri.getPath()))
                .into(holder.newImageView);*/
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // loading everything from database
    public void reload() {
        notes = MainActivity.database.noteDao().getAllNotes();
        notifyDataSetChanged();
    }
}
