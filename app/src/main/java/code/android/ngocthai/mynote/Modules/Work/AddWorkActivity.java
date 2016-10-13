package code.android.ngocthai.mynote.Modules.Work;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import code.android.ngocthai.mynote.Common.Object.Work;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.MainActivity;
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class AddWorkActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private EditText editHeader, editTitle, editTimeEnd, editTimeStart, editDate, editImportant;
    private TextInputLayout inputHeader, inputTitle, inputTimeStart, inputTimeEnd, inputDate, inputImportant;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_work;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fabAddWorkDone);

        editDate = (EditText) findViewById(R.id.edtDate);
        editImportant = (EditText) findViewById(R.id.edtImportant);
        editTimeEnd = (EditText) findViewById(R.id.edtTimeEnd);
        editTimeStart = (EditText) findViewById(R.id.edtTimeStart);
        editHeader = (EditText) findViewById(R.id.edtHeaderWork);
        editTitle = (EditText) findViewById(R.id.edtTitleWork);

        inputDate = (TextInputLayout) findViewById(R.id.inputDate);
        inputHeader = (TextInputLayout) findViewById(R.id.inputHeaderWork);
        inputTitle = (TextInputLayout) findViewById(R.id.inputTitleWork);
        inputImportant = (TextInputLayout) findViewById(R.id.inputImportant);
        inputTimeEnd = (TextInputLayout) findViewById(R.id.inputTimeEnd);
        inputTimeStart = (TextInputLayout) findViewById(R.id.inputTimeStart);


    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        mToolbar.setTitle(R.string.title_add_work);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent in = new Intent(this, MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddWorkDone: {
                submitForm();
                break;
            }
        }
    }

    private void submitForm() {
        if (!checkValues(editHeader, inputHeader, getString(R.string.msg_error_input_header_work))
                | !checkValues(editTitle, inputTitle, getString(R.string.msg_error_input_title_work))
                | !checkValues(editDate, inputDate, getString(R.string.msg_error_input_date_work))
                | !checkValues(editTimeEnd, inputTimeEnd, getString(R.string.msg_error_input_time_end_work))
                | !checkValues(editTimeStart, inputTimeStart, getString(R.string.msg_error_input_time_start_work))
                | !checkValues(editImportant, inputImportant, getString(R.string.msg_error_input_important_work))) {
            return;
        } else {
            DBController db = new DBController(this);
            String header = editHeader.getText().toString();
            String title = editTitle.getText().toString();
            String date = editDate.getText().toString();
            String important = editImportant.getText().toString();
            String timeEnd = editTimeEnd.getText().toString();
            String timeStart = editTimeStart.getText().toString();
            boolean insert = db.InsertWork(new Work(0, header, title, important, timeStart, timeEnd, date));
            if (insert) {
                Toast.makeText(AddWorkActivity.this, R.string.notification_add_work_success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(AddWorkActivity.this, R.string.notification_add_work_fail, Toast.LENGTH_SHORT).show();
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

    private void requestFocus(View v) {
        if (v.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
