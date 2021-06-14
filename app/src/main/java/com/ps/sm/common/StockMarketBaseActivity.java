package com.ps.sm.common;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.ps.sm.R;
import com.ps.sm.session.CustomToast;
import com.ps.sm.session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import com.tapadoo.alerter.Alerter;


public abstract class StockMarketBaseActivity extends AppCompatActivity {
    Context mContext;
    SessionManager sessionManager;
    public Animation shakeAnimation, bounceAnimation;
    public Typeface fontRegular, fontBold;
    public String LOG_TAG = "StockMarketBaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        sessionManager = new SessionManager(mContext);

    }

    //todo calling custom toast
    public void customToast(String valueError, boolean isVibrate) {
        new CustomToast().Show_Toast(mContext, getWindow().getDecorView().getRootView(),
                valueError);
        if (isVibrate) {
            vibrate(200);
        }
    }

    //todo calling animation when entering form activity
    public void enterActivityAnimation() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    //todo calling animation when exit form activity
    public void exitActivityAnimation() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    //todo calling animation when open activity form bottom to top
    public void openToTopAnimation() {
        overridePendingTransition(R.anim.slide_in_up, R.anim.nothing);
    }

    //todo calling animation when open activity form top to bottom
    public void closeToBottomAnimation() {
        overridePendingTransition(R.anim.nothing, R.anim.slide_out_up);
    }

    //todo for vibrating the device when  get error
    public void vibrate(int duration) {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(duration);
    }

//    //    //todo for sweet alert
//    public void sweetAlert(int alertType, String message) {
////        int Type = SweetAlertDialog.ERROR_TYPE;
//        new SweetAlertDialog(this, alertType)
//                .setTitleText("Alert")
//                .setContentText(message)
//                .show();
//    }


    public String getCurrentDateTime() {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy'&'HH:mm:ss");
        return simpleDateFormat.format(smsTime.getTime());
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

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    //2020-06-05
    public static String parseDate(String dateTime) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
//        String outputPattern = "yyyy MMM dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateTime);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }


    public SessionManager getInstanceSession() {
        if (sessionManager == null) {
            sessionManager = new SessionManager(this);
        }
        return sessionManager;
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


    public String getHHTohhTime(String vDate, String vTimeTime) {
        String vTime = vTimeTime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        SimpleDateFormat outSdf = new SimpleDateFormat("hh:mm a");
        try {
            vTime = outSdf.format(sdf.parse(vDate));
        } catch (Exception e) {

        }
        return vTime.toUpperCase();
    }


    //
    public String getCurrentTime() {
        Calendar smsTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss a");
        return simpleDateFormat.format(smsTime.getTime());
    }


}
