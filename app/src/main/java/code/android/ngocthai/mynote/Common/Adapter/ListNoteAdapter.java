package code.android.ngocthai.mynote.Common.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.R;

/**
 * Created by Thaihn on 06/10/2016.
 */
public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.ViewHolder> {

    private ArrayList<Note> listNote;
    private Activity activity;

    public ArrayList<Note> getListNote() {
        return listNote;
    }

    public void setListNote(ArrayList<Note> listNote) {
        this.listNote = listNote;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textHeader, textTitle;

        public ViewHolder(View v) {
            super(v);
            textHeader = (TextView) v.findViewById(R.id.txt_header_note);
            textTitle = (TextView) v.findViewById(R.id.txt_title_note);
        }
    }

    /**
     * Constructor default
     *
     * @param ls
     */
    public ListNoteAdapter(ArrayList<Note> ls, Activity activity) {
        this.listNote = ls;
        this.activity = activity;
    }

    @Override
    public ListNoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_note, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListNoteAdapter.ViewHolder holder, int position) {
        final Note note = listNote.get(position);
        Typeface fontHeader = Typeface.createFromAsset(activity.getAssets(), "fonts/fontHeaderNote.ttf");
        Typeface fontTitle = Typeface.createFromAsset(activity.getAssets(), "fonts/fontTitleNote.ttf");
        holder.textHeader.setTypeface(fontHeader);
        holder.textTitle.setTypeface(fontTitle);
        holder.textHeader.setText(note.getHEADER());
        holder.textTitle.setText(note.getTITLE());
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }
}
