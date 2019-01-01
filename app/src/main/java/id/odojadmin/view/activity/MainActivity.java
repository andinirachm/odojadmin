package id.odojadmin.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odojadmin.R;
import id.odojadmin.controller.UserController;
import id.odojadmin.event.GetDetailUserEvent;
import id.odojadmin.event.SubscriberPriority;
import id.odojadmin.helper.DateHelper;
import id.odojadmin.helper.PreferenceHelper;
import id.odojadmin.view.fragment.BerandaFragment;
import id.odojadmin.view.fragment.FormatRekapanFragment;
import id.odojadmin.view.fragment.GroupFragment;
import id.odojadmin.view.fragment.UserSettingFragment;

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

    private UserController controller;
    private AlertDialog dialog;

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

        controller = new UserController();
        controller.getAdminDetail(PreferenceHelper.getInstance().getSessionString(PreferenceHelper.KEY_EMAIL));

        View headerLayout = navigationView.getHeaderView(0);

        textViewName = headerLayout.findViewById(R.id.text_view_name);
        textViewEmail = headerLayout.findViewById(R.id.text_view_email);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Beranda");
        addFragment(BerandaFragment.newInstance());
        navigationView.getMenu().getItem(0).setChecked(true);
        DateHelper dateHelper = new DateHelper();
        System.out.println("HOLA " + dateHelper.getYesterdayDateString() + " - " + dateHelper.getTomorrowDateString());
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            getSupportActionBar().setTitle("Beranda");
            addFragment(BerandaFragment.newInstance());
        } else if (id == R.id.nav_grup) {
            getSupportActionBar().setTitle("Grup");
            addFragment(GroupFragment.newInstance());
        } else if (id == R.id.nav_admin) {
            getSupportActionBar().setTitle("Profil Pengguna");
            addFragment(UserSettingFragment.newInstance());
        } else if (id == R.id.nav_format_rekapan) {
            getSupportActionBar().setTitle("Format Rekapan");
            addFragment(FormatRekapanFragment.newInstance());
        } else if (id == R.id.nav_keluar) {
            showDialogLogout();
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

    private void showDialogLogout() {
        LayoutInflater li = LayoutInflater.from(this);
        final View dialogView = li.inflate(R.layout.dialog_logout, null);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(dialogView);

        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (PreferenceHelper.getInstance().isAuthenticated()) {
                    PreferenceHelper.getInstance().logout();
                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog = alertDialog.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.black));
            }
        });

        dialog.show();
    }

}
