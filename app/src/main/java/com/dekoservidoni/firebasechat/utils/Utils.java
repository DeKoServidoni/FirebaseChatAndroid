package com.dekoservidoni.firebasechat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.util.Date;

/**
 * Class responsible to hold all the utility
 * methods of the application
 */
public class Utils {

    public static void closeKeyboard(Context context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void saveLocalUser(Context context, String username, String email, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Constants.PREFERENCES_USER_NAME,username)
                .putString(Constants.PREFERENCES_USER_EMAIL,email)
                .putString(Constants.PREFERENCES_USER_ID,id)
                .apply();

    }

    public static String getLocalUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.PREFERENCES_USER_NAME, Constants.DEFAULT_USER);
    }

    public static String getLocalUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.PREFERENCES_USER_ID, Constants.DEFAULT_ID);
    }
}
