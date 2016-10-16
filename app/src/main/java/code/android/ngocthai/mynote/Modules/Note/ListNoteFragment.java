package code.android.ngocthai.mynote.Modules.Note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Adapter.ListNoteAdapter;
import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Common.Utils.RecyclerTouchListener;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.Modules.Ui.BaseFragment;
import code.android.ngocthai.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNoteFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ArrayList<Note> listNote;
    private ListNoteAdapter adapter;
    private GridLayoutManager glm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list_note;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState, View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rcvListNote);

    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        recyclerView.setHasFixedSize(true);
        listNote = queryData();
        adapter = new ListNoteAdapter(listNote, getActivity());
        glm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = listNote.get(position);
                Toast.makeText(getActivity(), "" + note.getID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                intent.putExtra(Constraint.KEY_SEND_OBJECT_NOTE, note);
                intent.putExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_EDIT, Constraint.STATUS_SECRET_FALSE);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public ArrayList<Note> queryData() {
        ArrayList<Note> ls = new ArrayList<>();
        DBController db = new DBController(getActivity());
        ls = db.getAllNote(Constraint.VALUES_SECRET_SELECT_NOTE_FALSE);
        return ls;
    }

}
