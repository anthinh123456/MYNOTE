package code.android.ngocthai.mynote.Modules.Note;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.MainActivity;
import code.android.ngocthai.mynote.R;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private FloatingActionButton fabEdit, fabDelete;
    private EditText editHeader, editTitle;
    private TextInputLayout inputHeader, inputTitle;
    private Note note;
    private String statusSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initView();

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit_note_private);
        fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete_note_private);

        editHeader = (EditText) findViewById(R.id.edt_header_edit_note_private);
        editTitle = (EditText) findViewById(R.id.edt_title_edit_note_private);
        inputHeader = (TextInputLayout) findViewById(R.id.input_layout_header_edit_note);
        inputTitle = (TextInputLayout) findViewById(R.id.input_layout_title_edit_note);

        getData();
        fabDelete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));
        fabEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));
        editHeader.setText(note.getHEADER());
        editTitle.setText(note.getTITLE());

        toolbar.setTitle(R.string.title_edit_note_private);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (statusSecret.equalsIgnoreCase(Constraint.STATUS_SECRET_FALSE)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    finish();
                }
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit_note_private: {
                String header = editHeader.getText().toString();
                String title = editTitle.getText().toString();
                submitEditNote(header, title);
                break;
            }
            case R.id.fab_delete_note_private: {
                submitDeleteNote();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void submitDeleteNote() {
        DBController db = new DBController(this);
        boolean result = db.DeleteNote(note.getID());
        if (result) {
            Toast.makeText(EditNoteActivity.this, R.string.notification_delete_note_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListNotePrivateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void submitEditNote(String header, String title) {
        if (!checkHeader()) {
            return;
        } else if (!checkTitle()) {
            return;
        } else {
            DBController db = new DBController(this);
            note.setHEADER(header);
            note.setTITLE(title);
            note.setSECRET(statusSecret);
            boolean result = db.EditNote(note);
            if (result) {
                Toast.makeText(EditNoteActivity.this, R.string.notification_edit_note_success, Toast.LENGTH_SHORT).show();
                if (statusSecret.equalsIgnoreCase(Constraint.STATUS_SECRET_FALSE)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Constraint.KEY_RESULT_EDIT_NOTE, true);
                    setResult(Constraint.RESULT_CODE_EDIT_NOTE, intent);
                    finish();
                }
            }

            if (result) {
                Toast.makeText(EditNoteActivity.this, R.string.notification_edit_note_success, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(EditNoteActivity.this, R.string.notification_edit_note_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Check not null of edit text header
     */
    private boolean checkTitle() {
        if (editTitle.getText().toString().trim().isEmpty()) {
            inputTitle.setError(getString(R.string.input_layout_error_header));
            requestFocus(editTitle);
            return false;
        } else {
            inputTitle.setErrorEnabled(false);
        }
        return true;
    }


    /**
     * Check not null of edit text header
     */
    private boolean checkHeader() {
        if (editHeader.getText().toString().trim().isEmpty()) {
            inputHeader.setError(getString(R.string.input_layout_error_title));
            requestFocus(editHeader);
            return false;
        } else {
            inputHeader.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Call request focus to editext
     *
     * @param v
     */
    private void requestFocus(View v) {
        if (v.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void getData() {
        note = (Note) getIntent().getSerializableExtra(Constraint.KEY_SEND_OBJECT_NOTE);
        statusSecret = getIntent().getStringExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_EDIT);
    }
}
