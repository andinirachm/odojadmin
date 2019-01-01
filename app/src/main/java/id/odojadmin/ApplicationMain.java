package id.odojadmin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.greenrobot.event.EventBus;

public class ApplicationMain extends Application {

    public static final String TAG = ApplicationMain.class
            .getSimpleName();
    private static ApplicationMain instance;
    private static Context context;
    private EventBus eventBus;
    private DatabaseReference firebaseDatabaseAdmin, firebaseDatabaseGroup, firebaseDatabaseMember, firebaseDatabaseRekapan;
    public FirebaseDatabase firebaseInstance;

    public static synchronized ApplicationMain getInstance() {
        return instance;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context mContext) {
        context = mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        instance = this;
        context = this;
        eventBus = new EventBus();
        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabaseAdmin = firebaseInstance.getReference("admin");
        firebaseDatabaseGroup = firebaseInstance.getReference("group");
        firebaseDatabaseMember = firebaseInstance.getReference("member");
        firebaseDatabaseRekapan= firebaseInstance.getReference("formatRekapan");
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public FirebaseDatabase getFirebaseInstance() {
        return firebaseInstance;
    }

    public DatabaseReference getFirebaseDatabaseAdmin() {
        return firebaseDatabaseAdmin;
    }

    public DatabaseReference getFirebaseDatabaseGroup() {
        return firebaseDatabaseGroup;
    }

    public DatabaseReference getFirebaseDatabaseMember() {
        return firebaseDatabaseMember;
    }

    public DatabaseReference getFirebaseDatabaseRekapan() {
        return firebaseDatabaseRekapan;
    }

}
