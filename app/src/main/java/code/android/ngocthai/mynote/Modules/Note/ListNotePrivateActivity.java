package code.android.ngocthai.mynote.Modules.Note;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import code.android.ngocthai.mynote.Common.Adapter.ListNoteAdapter;
import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.MainActivity;
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class ListNotePrivateActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView recyclerView;
    private ArrayList<Note> listNote;
    private ListNoteAdapter adapter;
    private GridLayoutManager glm;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_list_note_private;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mFab = (FloatingActionButton) findViewById(R.id.fab_list_note_private);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_list_note_private);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        mToolbar.setTitle(R.string.title_list_note_private);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeColor();
        mFab.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        listNote = queryData();
        adapter = new ListNoteAdapter(listNote, this);
        glm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = listNote.get(position);
                Toast.makeText(ListNotePrivateActivity.this, "" + note.getID(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListNotePrivateActivity.this, EditNoteActivity.class);
                intent.putExtra(Constraint.KEY_SEND_OBJECT_NOTE, note);
                intent.putExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_EDIT, Constraint.STATUS_SECRET_TRUE);
                startActivityForResult(intent, Constraint.REQUEST_CODE_EDIT_NOTE_PRIVATE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeColor() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.string.color_note))));
        mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorTabNote));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Constraint.RESULT_CODE_ADD_NOTE && requestCode == Constraint.REQUEST_CODE_ADD_NOTE_PRIVATE) {
                boolean result = data.getBooleanExtra(Constraint.KEY_RESULT_ADD_NOTE, false);
                if (result) {
                    ArrayList<Note> ls = queryData();
                    adapter.setListNote(ls);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == Constraint.REQUEST_CODE_EDIT_NOTE_PRIVATE) {
                boolean result = data.getBooleanExtra(Constraint.KEY_RESULT_EDIT_NOTE, false);
                if (result) {
                    ArrayList<Note> ls = queryData();
                    adapter.setListNote(ls);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_list_note_private: {
                Intent intent = new Intent(ListNotePrivateActivity.this, AddNoteActivity.class);
                intent.putExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_ADD, Constraint.STATUS_SECRET_TRUE);
                startActivityForResult(intent, Constraint.REQUEST_CODE_ADD_NOTE_PRIVATE);
                break;
            }
            default: {
                break;
            }
        }
    }

    public ArrayList<Note> queryData() {
        ArrayList<Note> ls = new ArrayList<>();
        DBController db = new DBController(this);
        ls = db.getAllNote(Constraint.VALUES_SECRET_SELECT_NOTE_TRUE);
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
