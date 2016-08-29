package br.com.livroandroid.carros.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by EdmilsonS on 18/08/2016.
 */
public class Prefs {

    public static final String PREF_ID = "livroandroid";

    public static void setString(Context context, String chave, String valor) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getString(Context context, String chave) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        return preferences.getString(chave, "");
    }

    public static Integer getInteger(Context context, String chave) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        return preferences.getInt(chave, 0);
    }

    public static void setInteger(Context context, String chave, int valor) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }
}
