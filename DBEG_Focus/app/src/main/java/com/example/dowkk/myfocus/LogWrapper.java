package com.example.dowkk.myfocus;

import android.os.Binder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogWrapper {
    // 각 앱 이름
    private static final String APP_NAME = "MYFocus";
    private static final String TAG = "LogWrapper";
    private static final int LOG_FILE_SIZE_LIMIT = 512 * 1024;
    private static final int LOG_FILE_MAX_COUNT = 2;
    private static final String LOG_FILE_NAME = "LogMyFocus%g.txt";
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
    private static final Date date = new Date();
    private static Logger logger;
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler(Environment.getExternalStorageDirectory()
                    + File.separator + APP_NAME + " : " +
                    LOG_FILE_NAME, LOG_FILE_SIZE_LIMIT, LOG_FILE_MAX_COUNT, true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord r) {
                    date.setTime(System.currentTimeMillis());

                    StringBuilder ret = new StringBuilder(80);
                    ret.append(formatter.format(date));
                    ret.append(r.getMessage());
                    return ret.toString();
                }
            });
            logger = Logger.getLogger(LogWrapper.class.getName());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            Log.d(TAG, "init_success");
            v(TAG, "init_success");
        } catch (IOException e) {
            Log.d(TAG, "init failure");
        }
    }

    public static void v (String tag, String event, String msg) {
        if (logger != null) {
            logger.log(Level.FINE, String.format("F/%s(%d): %s: %s\n",
                    tag, Binder.getCallingPid(), event, msg));
            Log.e("log monitor", String.format("F/%s(%d): %s\n",
                    tag, Binder.getCallingPid(), event, msg));    }
    }

    public static void v (String tag, String msg) {
        v(tag, msg, "");
    }
}