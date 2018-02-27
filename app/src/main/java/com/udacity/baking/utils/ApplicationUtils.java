package com.udacity.baking.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by fabiano.alvarenga on 10/24/17.
 */

public class ApplicationUtils {

    private static final String TAG = ApplicationUtils.class.getSimpleName();

    private static ApplicationInfo appInfo;

    public static String getMetaDataValue(Context context, String key) {
        appInfo = getApplicationInfo(context);
        Object metadataValue = appInfo.metaData.get(key);
        return metadataValue != null ? String.valueOf(metadataValue) : null;
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return appInfo;
    }
}
