package id.odojadmin.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odojadmin.R;
import id.odojadmin.controller.AdminController;
import id.odojadmin.event.GetDetailUserEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.view.fragment.GroupFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textViewName, textViewEmail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private AdminController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        eventBus.register(this, SubscriberPriority.HIGH);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        controller = new AdminController();
        controller.getAdminDetail(PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_EMAIL));

        View headerLayout = navigationView.getHeaderView(0);

        textViewName = headerLayout.findViewById(R.id.text_view_name);
        textViewEmail = headerLayout.findViewById(R.id.text_view_email);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {

        } else if (id == R.id.nav_grup) {
            getSupportActionBar().setTitle("Grup");
            addFragment(GroupFragment.newInstance());

        } else if (id == R.id.nav_admin) {
            startActivity(new Intent(this, AdminSettingActivity.class));
        } else if (id == R.id.nav_format_rekapan) {

        } else if (id == R.id.nav_keluar) {
            PreferenceHelper.getInstance().logout();
            Intent i = new Intent(this, SignInActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void onEventMainThread(GetDetailUserEvent event) {
        if (event.isSuccess()) {
            textViewName.setText(event.getAdmin().getName());
            textViewEmail.setText(event.getAdmin().getEmail());
        }
    }

}
