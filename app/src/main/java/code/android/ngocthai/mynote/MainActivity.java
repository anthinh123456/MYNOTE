package code.android.ngocthai.mynote;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import code.android.ngocthai.mynote.Common.Adapter.MainAdapter;
import code.android.ngocthai.mynote.Common.Utils.Constraint;
import code.android.ngocthai.mynote.Modules.Another.ThemeActivity;
import code.android.ngocthai.mynote.Modules.Note.ListNotePrivateActivity;
import code.android.ngocthai.mynote.Modules.Note.AddNoteActivity;
import code.android.ngocthai.mynote.Modules.Note.ListNoteFragment;
import code.android.ngocthai.mynote.Modules.Ui.BaseActivity;
import code.android.ngocthai.mynote.Modules.Work.AddWorkActivity;
import code.android.ngocthai.mynote.Modules.Work.WorkTodayFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private int mTabSelected = 1;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LinearLayout linearLayout;
    private String[] strColor = {"#F44336", "#4CAF50", "#00BCD4"};
    private int[] intColor = {R.color.colorTabNote, R.color.colorTabWork, R.color.colorTabSchedule};


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState) {
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabsMain);
        mFab = (FloatingActionButton) findViewById(R.id.fabMain);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        linearLayout = (LinearLayout) navigationView.inflateHeaderView(R.layout.nav_header_main).findViewById(R.id.linear_layout_header);

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        changeColorWindow(intColor[1], strColor[1]);

        setupPager(mViewPager);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabSelected(position);
                changeColorWindow(intColor[position], strColor[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    /**
     * Change color of UI when change selected of viewpager
     *
     * @param idColor
     * @param color
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeColorWindow(int idColor, String color) {
        mTabLayout.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        linearLayout.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(idColor)));
        mToolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(idColor));
    }

    private void setupPager(ViewPager vp) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListNoteFragment(), getString(R.string.tabs_note));
        adapter.addFragment(new WorkTodayFragment(), getString(R.string.today));
        adapter.addFragment(new ListWorkFragment(), getString(R.string.tab_list_work));
        vp.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.navSecret: {
                Intent intent = new Intent(this, ListNotePrivateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.navContacts: {

                break;
            }
            case R.id.navColor: {
                Intent intent = new Intent(this, ThemeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.navShutdown: {
                finish();
                break;
            }
            default: {
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Click of layout
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabMain: {
                if (getTabSelected() == 0) {
                    Intent intent = new Intent(this, AddNoteActivity.class);
                    intent.putExtra(Constraint.KEY_SEND_STATUS_SECRET_TO_ADD, Constraint.STATUS_SECRET_FALSE);
                    startActivity(intent);
                } else if (getTabSelected() == 1) {
                    Intent intent = new Intent(this, AddWorkActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public void setTabSelected(int current_tabs) {
        this.mTabSelected = current_tabs;
    }

    public int getTabSelected() {
        return mTabSelected;
    }
}
