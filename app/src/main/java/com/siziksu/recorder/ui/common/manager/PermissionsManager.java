package com.siziksu.recorder.ui.common.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public final class PermissionsManager {

    private static final int PERMISSION_REQUEST_CODE = 1001;

    private PermissionsManager() {}

    public static void checkPermissions(Activity context) {
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    public static boolean allGood(int requestCode, int[] grantResults) {
        boolean write = false;
        boolean record = false;
        switch (requestCode) {
            case PermissionsManager.PERMISSION_REQUEST_CODE:
                write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                record = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        return write && record;
    }
}
