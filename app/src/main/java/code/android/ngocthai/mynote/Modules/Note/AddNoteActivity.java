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
        mToolbar = (Toolbar) findViewById(R.id.toolbarAddNote);
        mFab = (FloatingActionButton) findViewById(R.id.fabAddNote);
        editHeader = (EditText) findViewById(R.id.edtHeaderAddNote);
        editTitle = (EditText) findViewById(R.id.edtTitleAddNote);
        inputHeader = (TextInputLayout) findViewById(R.id.inputHeaderAddNote);
        inputTitle = (TextInputLayout) findViewById(R.id.inputTitleAddNote);
    }


    @Override
    protected void initData(Bundle saveInstanceState) {
        BaseActivity.hideKeyboard(findViewById(R.id.scrAddNote), AddNoteActivity.this);
        mToolbar.setTitle(R.string.title_add_note);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeColor();
        mFab.setOnClickListener(this);
        statusSecret = getIntent().getStringExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_ADD);

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
            case R.id.fabAddNote: {
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
