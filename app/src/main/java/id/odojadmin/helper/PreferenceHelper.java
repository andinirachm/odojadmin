package id.odojadmin.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;
import id.odojadmin.ApplicationMain;


public class PreferenceHelper {

    public static final String KEY_IS_AUTHENTICATED = "isAuthenticated";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_KONFETTI = "konfetti";

    public static final String KEY_LANGUAGE = "LANGUAGE";
    public static final String KEY_LANGUAGE_ID = "LANGUAGE_ID";
    public static final String KEY_LANGUAGE_EN = "LANGUAGE_EN";

    private static PreferenceHelper mInstance;

    private SharedPreferences mPreferences;

    public PreferenceHelper() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationMain.getInstance());
    }

    public static PreferenceHelper getInstance() {
        if (mInstance == null) {
            mInstance = new PreferenceHelper();
        }
        return mInstance;
    }

    /**
     * Save a boolean to shared preference
     *
     * @param key   Key for access value at shared preference
     * @param value Value would be saved to shared preference
     */
    public void saveSession(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * Save an integer to shared preference
     *
     * @param key   Key for access value at shared preference
     * @param value Value would be saved to shared preference
     */
    public void saveSession(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    /**
     * Save a string to shared preference
     *
     * @param key   Key for access value at shared preference
     * @param value Value would be saved to shared preference
     */
    public void saveSession(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public void login(int userId, String token) {
        mPreferences.edit().putInt(PreferenceHelper.KEY_USER_ID, userId).apply();
        mPreferences.edit().putString(PreferenceHelper.KEY_TOKEN, token).apply();
        mPreferences.edit().putBoolean(PreferenceHelper.KEY_IS_AUTHENTICATED, true).apply();
    }

    /**
     * Save a list string to shared preference
     *
     * @param key   Key for access value at shared preference
     * @param value Value would be saved to shared preference
     */
    public void saveSession(String key, Set<String> value) {
        mPreferences.edit().putStringSet(key, value).apply();
    }

    /**
     * Retrieve a boolean from shared preference
     *
     * @param key Key for access value at shared preference
     */
    public boolean getSessionBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    /**
     * Retrieve an integer from shared preference
     *
     * @param key Key for access value at shared preference
     */
    public int getSessionInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    /**
     * Retrieve a string from shared preference
     *
     * @param key Key for access value at shared preference
     */
    public String getSessionString(String key) {
        return mPreferences.getString(key, "");
    }

    /**
     * Retrieve a list string from shared preference
     *
     * @param key Key for access value at shared preference
     */
    public Set<String> getSessionStringSet(String key) {
        return mPreferences.getStringSet(key, new HashSet<String>());
    }


    /**
     * Clear all value at shared preference
     */
    public void logout() {
        mPreferences.edit().clear().apply();
        saveSession(KEY_IS_AUTHENTICATED, false);
    }

    /**
     * Check app authentication status at shared preference
     */
    public boolean isAuthenticated() {
        return getSessionBoolean(KEY_IS_AUTHENTICATED);
    }

    public boolean isKonfettiShowed() {
        return getSessionBoolean(KEY_KONFETTI);
    }

    public SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}
