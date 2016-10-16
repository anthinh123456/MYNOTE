package code.android.ngocthai.mynote.Modules.Note;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import code.android.ngocthai.mynote.Common.Object.Note;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.MainActivity;
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class EditNoteActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private FloatingActionButton fabEdit, fabDelete;
    private EditText editHeader, editTitle;
    private TextInputLayout inputHeader, inputTitle;
    private Note note;
    private String statusSecret;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_edit_note;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbarEditNote);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEditNote);
        fabDelete = (FloatingActionButton) findViewById(R.id.fabDeleteNote);

        editHeader = (EditText) findViewById(R.id.edtHeaderEditNote);
        editTitle = (EditText) findViewById(R.id.edtTitleEditNote);
        inputHeader = (TextInputLayout) findViewById(R.id.inputHeaderEditNote);
        inputTitle = (TextInputLayout) findViewById(R.id.inputTitleEditNote);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        BaseActivity.hideKeyboard(findViewById(R.id.scrEditNote), EditNoteActivity.this);
        getData();
        changeColor();
        editHeader.setText(note.getHEADER());
        editTitle.setText(note.getTITLE());
        mToolbar.setTitle(R.string.title_edit_note_private);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeColor() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.string.color_note))));
        fabEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));
        fabDelete.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorTabNote));

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
            case R.id.fabEditNote: {
                String header = editHeader.getText().toString();
                String title = editTitle.getText().toString();
                submitEditNote(header, title);
                break;
            }
            case R.id.fabDeleteNote: {
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
            inputTitle.setError(getString(R.string.msg_error_input_header_note));
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
            inputHeader.setError(getString(R.string.msg_error_input_title_note));
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

    /**
     * Receive data from another activity sent
     */
    private void getData() {
        note = (Note) getIntent().getSerializableExtra(Constraint.KEY_SEND_OBJECT_NOTE);
        statusSecret = getIntent().getStringExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_EDIT);
    }
}
