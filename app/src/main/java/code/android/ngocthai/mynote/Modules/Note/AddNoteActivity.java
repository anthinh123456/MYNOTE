package code.android.ngocthai.mynote.Modules.Note;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
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
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class AddNoteActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private EditText editHeader, editTitle;
    private TextInputLayout inputHeader, inputTitle;
    private String statusSecret;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_note;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add_note_public);
        mFab = (FloatingActionButton) findViewById(R.id.fab_add_done);
        editHeader = (EditText) findViewById(R.id.edt_header_note_public);
        editTitle = (EditText) findViewById(R.id.edt_title_note_public);
        inputHeader = (TextInputLayout) findViewById(R.id.input_layout_header_note_public);
        inputTitle = (TextInputLayout) findViewById(R.id.input_layout_title_note_public);
    }


    @Override
    protected void initData(Bundle saveInstanceState) {
        BaseActivity.hideKeyboard(findViewById(R.id.scrAddNote), AddNoteActivity.this);
        mToolbar.setTitle(R.string.title_add_note);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFab.setOnClickListener(this);
        mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabNote)));
        statusSecret = getIntent().getStringExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_ADD);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (statusSecret.equalsIgnoreCase(Constraint.STATUS_SECRET_FALSE)) {
                    Intent in = new Intent(this, MainActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                } else {
                    finish();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_done: {
                String header = editHeader.getText().toString();
                String title = editTitle.getText().toString();
                String statusSecret = getIntent().getStringExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_ADD);
                submitForm(header, title, statusSecret);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (statusSecret.equalsIgnoreCase(Constraint.STATUS_SECRET_TRUE)) {
            super.onBackPressed();
        } else {
            Intent in = new Intent(this, MainActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        }
    }

    /**
     * Form submit check values
     *
     * @param header header of note
     * @param title  title of note
     * @param status status of note
     */
    private void submitForm(String header, String title, String status) {
        if (!checkValues(editHeader, inputHeader, getString(R.string.msg_error_input_header_note)) |
                !checkValues(editTitle, inputTitle, getString(R.string.msg_error_input_title_note))) {
            return;
        } else {
            if (status.equalsIgnoreCase(Constraint.STATUS_SECRET_FALSE)) {
                DBController db = new DBController(this);
                boolean result = db.InsertNote(new Note(0, header, title, status));
                if (result) {
                    Toast.makeText(AddNoteActivity.this, R.string.notification_add_note_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            } else {
                DBController db = new DBController(this);
                boolean result = db.InsertNote(new Note(0, header, title, status));
                if (result) {
                    Intent intent = new Intent();
                    Toast.makeText(AddNoteActivity.this, R.string.notification_add_note_success, Toast.LENGTH_SHORT).show();
                    intent.putExtra(Constraint.KEY_RESULT_ADD_NOTE, true);
                    setResult(Constraint.RESULT_CODE_ADD_NOTE, intent);
                    finish();
                }
            }
        }
    }

    private boolean checkValues(EditText editText, TextInputLayout textInputLayout, String messageError) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(messageError);
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * Call request focus to editext
     *
     * @param v view of edit text
     */
    private void requestFocus(View v) {
        if (v.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
