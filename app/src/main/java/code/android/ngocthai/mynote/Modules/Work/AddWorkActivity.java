package code.android.ngocthai.mynote.Modules.Work;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.R;

public class AddWorkActivity extends BaseActivity {

    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private EditText editHeader, editTitle, editTimeEnd, editTimeStart, editDate;
    private TextInputLayout inputHeader, inputTitle, inputTimeStart, inputTimeEnd, inputDate;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_work;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        editDate = (EditText) findViewById(R.id.edtDate);
        editTimeEnd = (EditText) findViewById(R.id.edtTimeEnd);
        editTimeStart = (EditText) findViewById(R.id.edtTimeStart);
        editHeader = (EditText) findViewById(R.id.edtHeaderWork);
        editTitle = (EditText) findViewById(R.id.edtTitleWork);

        inputDate= (TextInputLayout) findViewById(R.id.inputDate);
        inputHeader= (TextInputLayout) findViewById(R.id.inputHeaderWork);
        inputTitle= (TextInputLayout) findViewById(R.id.inputTitleWork);
        inputTimeEnd= (TextInputLayout) findViewById(R.id.inputTimeEnd);
        inputTimeStart= (TextInputLayout) findViewById(R.id.inputTimeStart);


    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        mToolbar.setTitle(R.string.title_add_work);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
