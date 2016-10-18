package code.android.ngocthai.mynote.Modules.Work;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import code.android.ngocthai.mynote.Common.Object.Work;
import code.android.ngocthai.mynote.Data.Client.DBController;
import code.android.ngocthai.mynote.MainActivity;
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class AddWorkActivity extends BaseActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private TextView txtResult;
    private Button btnChooseTime;
    private EditText editHeader, editTitle, editTimeEnd, editTimeStart, editDate, editImportant, editStatus;
    private TextInputLayout inputHeader, inputTitle, inputTimeStart, inputTimeEnd, inputDate, inputImportant, inputStatus;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_work;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fabAddWorkDone);

        editDate = (EditText) findViewById(R.id.edtDate);
        editImportant = (EditText) findViewById(R.id.edtImportant);
        editHeader = (EditText) findViewById(R.id.edtHeaderWork);
        editTitle = (EditText) findViewById(R.id.edtTitleWork);
        editStatus = (EditText) findViewById(R.id.edtStatus);

        btnChooseTime = (Button) findViewById(R.id.btnChooseTimeStart);
        txtResult = (TextView) findViewById(R.id.txtResultTime);

        inputDate = (TextInputLayout) findViewById(R.id.inputDate);
        inputHeader = (TextInputLayout) findViewById(R.id.inputHeaderWork);
        inputTitle = (TextInputLayout) findViewById(R.id.inputTitleWork);
        inputImportant = (TextInputLayout) findViewById(R.id.inputImportant);
        inputStatus = (TextInputLayout) findViewById(R.id.inputStatus);


    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        BaseActivity.hideKeyboard(findViewById(R.id.scrAddWork), this);
        mToolbar.setTitle(R.string.title_add_work);
        editStatus.setText("FALSE");
        setSupportActionBar(mToolbar);
        changeColor();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFab.setOnClickListener(this);

        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddWorkActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                tpd.enableMinutes(true);
                tpd.vibrate(true);
                tpd.enableMinutes(true);
                // color
                tpd.setAccentColor(Color.parseColor("#4CAF50"));
                // title
//                tpd.setTitle("TimePicker Title");

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeColor() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.string.color_work))));
        mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTabWork)));
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorTabWork));

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
                | !checkValues(editStatus, inputStatus, getString(R.string.msg_error_input_status_work))
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
            boolean insert = db.InsertWork(new Work(0, header, title, timeStart, timeEnd, date, important, "FALSE"));
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + "h:" + minuteString + "m:" + secondString + "s";
        txtResult.setText(time);
    }
}
