package id.odojadmin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class ApplicationMain extends Application {

    public static final String TAG = ApplicationMain.class
            .getSimpleName();
    private static ApplicationMain instance;
    private static Context context;

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
    }
}
