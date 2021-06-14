package com.ps.sm.session;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.ps.sm.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SessionManager {
    public static final String PREF_NAME = "USER_ANDROID_DB";
    public static final String PREF_NAME_GLOBEL = "USER_ANDROID_DB_GLOBEL";
    private static final String USER_DETAIL = "user_info";
    private static final String USER_ADDRESS = "user_address";
    private static final String USER_KYC = "user_kyc";
    private static final String USER_BANK = "user_bank";
    private static final String ASSURED_DETAIL = "assured_detail";
    private static final String FCM_USER_DETAIL = "fcm_user_info";
    private static final String USER_PLAN = "user_plan";
    private static final String IS_LOGIN = "IsLogin";
    private static final String IS_LANGUAGE_ENABLE = "IsLanguageEnable";
    private static final String LANGUAGE_DETAIL = "languageDetail";
    private static final String IS_DELETE_ATTACH = "IsDeleteAttach";
    private static final String REMEMBER_DATA = "RememberData";
    private static final String IS_REMEMBER = "IsRemember";
    private static final String LANGUAGE_ID = "LanguageId"; // 1: hindi 2: english
    private static final String VENDOR_ID = "VendorId";
    private static final String FCM_DEVICE_ID = "FCM_DeviceId";
    private static final String IS_NOTIFICATION = "IsNotification";
    private static final String LANGUAGE = "language";
    private static final String NOTI_MSG = "NOTI_MSG";
    private static final String HELP_CONTENT = "helpcontent";
    public static final String USER_SWITCH_TYPE = "user_switch_type";
    public static final String USER_TYPE = "usertype";
    public static final String USER_EMAIL = "usermail";
    public static final String USER_PASSWORD = "userpassword";
    public static final String IS_FILTER = "is_filter";
    public static final String FILTER_LOCATION = "filter_location";
    public static final String FILTER_CAT_ID = "filter_cat_id";
    public static final String FILTER_SUB_CAT_ID = "filter_sub_cat_id";
    public static final String FILTER_P_MASTER_ID = "filter_p_master_id";
    public static final String FILTER_PRICE = "filter_price";
    public static final String WEATHER_DETAILS = "weather_detail";

    private SharedPreferences pref_globel;
    private Editor editor_globel;

    private Context _context;
    private int PRIVATE_MODE = 0;
    String languageId = "";
    public boolean openDelete = false;
//    public Animation shakeAnimation;


    public SessionManager(Context context) {
        this._context = context;


        // globel preferences...
        pref_globel = _context.getSharedPreferences(PREF_NAME_GLOBEL, PRIVATE_MODE);
        editor_globel = pref_globel.edit();
//        shakeAnimation = AnimationUtils.loadAnimation(context,
//                R.anim.shake);

    }



    public void setLanguageId(String langId) {
        editor_globel.putString(LANGUAGE_ID, langId);
        editor_globel.commit();
        editor_globel.apply();
    }

    public String getLanguageId() {
        //todo if language id not found then default will be 2: English  & 1: Hindi
        return pref_globel.getString(LANGUAGE_ID, "2");
    }

    public void setVendorId(String vanId) {
        //todo we are storing vendor id separately
        editor_globel.putString(VENDOR_ID, vanId);
        editor_globel.commit();
        editor_globel.apply();
    }

    public String getVendorId() {
        //todo we are storing vendor id separately
        return pref_globel.getString(VENDOR_ID, "");
    }

    public void setFcmDeviceId(String fcmDeviceId) {
        editor_globel.putString(FCM_DEVICE_ID, fcmDeviceId);
        editor_globel.commit();
        editor_globel.apply();
    }

    public String getFcmDeviceId() {
        //todo if language id not found then default will be Vietnam
        return pref_globel.getString(FCM_DEVICE_ID, "abcd1234");
    }

    public void setLanguageEnable(boolean isLanguageEnable) {
        editor_globel.putBoolean(IS_LANGUAGE_ENABLE, isLanguageEnable);
        editor_globel.commit();
        editor_globel.apply();
    }


    public boolean getLanguageEnable() {
        return pref_globel.getBoolean(IS_LANGUAGE_ENABLE, false);
    }


    public void setOpenDeleteDialog(boolean openDelete) {
        editor_globel.putBoolean(IS_DELETE_ATTACH, openDelete);
        editor_globel.commit();
        editor_globel.apply();
    }

    public boolean getDeleteAttach() {
        return pref_globel.getBoolean(IS_DELETE_ATTACH, false);
    }

    public Dialog dialogProgress;

    public Dialog LoadingSpinner(Context mContext) {
        dialogProgress = new Dialog(mContext, android.R.style.Theme_Black);
        View _view = LayoutInflater.from(mContext).inflate(R.layout.custom_loader, null);
        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgress.setContentView(_view);
//        dialogProgress.setCanceledOnTouchOutside(false);
//        dialogProgress.setCancelable(false);
        return dialogProgress;
    }


    public void login() {
        editor_globel.putBoolean(IS_LOGIN, true);
        editor_globel.commit();
    }

    public boolean isLogin() {
        return pref_globel.getBoolean(IS_LOGIN, false);
    }

    public void setLogin(boolean isLogin) {
        editor_globel.putBoolean(IS_LOGIN, isLogin);
        editor_globel.putBoolean(IS_LOGIN, isLogin);
        editor_globel.commit();
        editor_globel.apply();
        editor_globel.commit();
        editor_globel.apply();
    }

    public void setRemember(boolean isRem) {
        editor_globel.putBoolean(IS_REMEMBER, isRem);
        editor_globel.commit();
        editor_globel.apply();
    }

    public boolean isRemember() {
        return pref_globel.getBoolean(IS_REMEMBER, false);
    }

    public void setRememberCred(String vUserEmail, String vUserPassword, String vUserType) {
        editor_globel.putString(USER_EMAIL, vUserEmail);
        editor_globel.putString(USER_PASSWORD, vUserPassword);
        editor_globel.putString(USER_TYPE, vUserType);
        editor_globel.commit();
    }

    public HashMap<String, String> getRememberCred() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USER_EMAIL, pref_globel.getString(USER_EMAIL, ""));
        user.put(USER_PASSWORD, pref_globel.getString(USER_PASSWORD, ""));
        user.put(USER_TYPE, pref_globel.getString(USER_TYPE, ""));
        return user;
    }


    public void notification() {
        editor_globel.putBoolean(IS_NOTIFICATION, true);
        editor_globel.commit();
    }


    public void setNotiMsg(String vMsg) {
        editor_globel.putString(NOTI_MSG, vMsg);
        editor_globel.commit();
    }

    public String getNotiMsg() {
        return pref_globel.getString(NOTI_MSG, "");
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void setFilters(boolean is_filter, String vLocationID, String vCatID, String vSubCatID, String vProductMasterID, String vPrice) {
        editor_globel.putBoolean(IS_FILTER, is_filter);
        editor_globel.putString(FILTER_LOCATION, vLocationID);
        editor_globel.putString(FILTER_CAT_ID, vCatID);
        editor_globel.putString(FILTER_SUB_CAT_ID, vSubCatID);
        editor_globel.putString(FILTER_P_MASTER_ID, vProductMasterID);
        editor_globel.putString(FILTER_PRICE, vPrice);
        editor_globel.commit();
    }

    public HashMap<String, String> getFilers() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(FILTER_LOCATION, pref_globel.getString(FILTER_LOCATION, ""));
        user.put(FILTER_CAT_ID, pref_globel.getString(FILTER_CAT_ID, ""));
        user.put(FILTER_SUB_CAT_ID, pref_globel.getString(FILTER_SUB_CAT_ID, ""));
        user.put(FILTER_P_MASTER_ID, pref_globel.getString(FILTER_P_MASTER_ID, ""));
        user.put(FILTER_PRICE, pref_globel.getString(FILTER_PRICE, ""));
        return user;
    }

    public boolean isFilter() {
        return pref_globel.getBoolean(IS_FILTER, false);
    }


    public void setUserSwitchType(String vUserType) {
        editor_globel.putString(USER_SWITCH_TYPE, vUserType);
        editor_globel.commit();
    }

    public String getUserSwitchType() {
        return pref_globel.getString(USER_SWITCH_TYPE, "1");
    }


    //2020-06-05 00:54:30
    public static String parseDateTime(String dateTime) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
//        String outputPattern = "yyyy MMM dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateTime);
            str = outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d("Error", "Close Keyboard====> " + e);
        }
    }

    public String getUniqueId(String u_id, String f_id) {
        long uid = 0;
        long f_uid = 0;
        String unique_Key = "";
        for (char c : u_id.toCharArray())
            uid = uid + (int) c;

        for (char c : f_id.toCharArray())
            f_uid = f_uid + (int) c;

        if (uid > f_uid) {
            unique_Key = (uid + "") + (f_uid + "");
        } else {
            unique_Key = (f_uid + "") + (uid + "");
        }
        return unique_Key;
    }

    public String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            try {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return sb.toString().trim();
    }


    public String getCurrentDateTime() {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy'&'HH:mm:ss");
        return simpleDateFormat.format(smsTime.getTime());
    }


    public String name = "";
    public String size = "0";
    public File file;

    //ClientRequestProposalActivity
    public void getFileFromDrive(Context context, Uri fileUri) {
        try {
            Log.d("Session Manager", "getFileFromDrive\t\t" + fileUri);
            name = "";
            size = "0";
            file = null;
            Uri returnUri = fileUri;
            Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             *     * move to the first row in the Cursor, get the data,
             *     * and display it.
             * */
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            name = (returnCursor.getString(nameIndex));
            size = (Long.toString(returnCursor.getLong(sizeIndex)));
            file = new File(context.getCacheDir(), name);
        } catch (Exception e) {
            Log.d("Session Manager", "getFileFromDrive\t\t-Exception-\t" + e);
        }

    }

    //ClientRequestProposalActivity
    public /*static*/ File getFileFromUri(final Context context, final Uri uri) throws Exception {

        /*if (isGoogleDrive(uri)) // check if file selected from google drive
        {*/
        return saveFileIntoExternalStorageByUri(context, uri);
       /* }else
            // do your other calculation for the other files and return that file
            return null;*/
    }


    public static boolean isGoogleDrive(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public File saveFileIntoExternalStorageByUri(Context context, Uri uri) throws

            Exception {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        int originalSize = inputStream.available();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        /*String*/
        name = getFileName(context, uri);
        /*File*/
        file = makeEmptyFileIntoExternalStorageWithTitle(name);
        bis = new BufferedInputStream(inputStream);
        bos = new BufferedOutputStream(new FileOutputStream(
                file, false));

        byte[] buf = new byte[originalSize];
        bis.read(buf);
        do {
            bos.write(buf);
        } while (bis.read(buf) != -1);

        bos.flush();
        bos.close();
        bis.close();

        return file;

    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public static File makeEmptyFileIntoExternalStorageWithTitle(String title) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        return new File(root, title);
    }

    public String getCurrentDate() {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        return simpleDateFormat.format(smsTime.getTime());
    }


}