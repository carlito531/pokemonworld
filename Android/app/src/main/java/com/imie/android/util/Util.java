package com.imie.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by charly on 19/07/2016.
 */
public class Util {

    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Return the password in sha 256
     * @param password
     * @return
     */
    public static String toSha256(String password) {

        StringBuffer sb = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Return string without double quotes
     * @param str
     * @return
     */
    public static String escapeDoubleQuotes(String str) {
        if (str.contains("\"")) {
            str = str.split("\"")[1];
        }
        return str;
    }

    /**
     * Save something in sharedPreferences
     * @param key
     * @param value
     * @param context
     */
    public static void saveToSharedPreferences(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get something saved in sharedPreferences
     * @param key
     * @param context
     * @return
     */
    public static String getSharedPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    /**
     * Return the api base url from the config.properties file
     * @param context
     * @return
     */
    public static String getApiUrlBase(Context context) {
        AssetPropertyReader assetsPropertyReader = new AssetPropertyReader(context);
        Properties p = assetsPropertyReader.getProperties("config.properties");

        return p.getProperty("ApiUrl");
    }


    /**
     * Get only opponent trainer name
     * @param markerTitle
     * @return
     */
    public static String parseMarkerTitle(String markerTitle) {
        markerTitle = StringUtils.substringAfter(markerTitle, ">");
        markerTitle = markerTitle.trim();

        return markerTitle;
    }
}
