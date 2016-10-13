package code.android.ngocthai.mynote.Modules.Note;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Adapter.ListNoteAdapter;
import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNoteFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Note> listNote;
    private ListNoteAdapter adapter;
    private GridLayoutManager glm;
    private View view;

    public ListNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_note, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_list_note_public);
        recyclerView.setHasFixedSize(true);
        listNote = queryData();
        adapter = new ListNoteAdapter(listNote, getActivity());
        glm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

}
