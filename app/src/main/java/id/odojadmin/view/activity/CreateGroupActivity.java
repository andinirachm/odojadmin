package id.odojadmin.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.odojadmin.R;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_group);
        getSupportActionBar().setTitle("Buat Grup");
    }
}
