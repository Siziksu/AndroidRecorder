package com.siziksu.recorder.ui.common.manager;

import android.os.Environment;

import com.siziksu.recorder.common.utils.Print;

import java.io.File;
import java.util.Locale;

import javax.inject.Inject;

public class FileManager {

    private final static String AUDIO_FORMAT_EXTENSION = ".aac";
    private final static String AUDIO_FOLDER = "Recordings/";

    @Inject
    public FileManager() {}

    public String getFile() {
        createFolderIfNotExists();
        File file = composeFile();
        Print.info(String.format(Locale.getDefault(), "File being recorded: \"%s\"", file.getPath()));
        return file.getPath();
    }

    private void createFolderIfNotExists() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), AUDIO_FOLDER);
        if (!folder.exists()) {
            Print.info(String.format(Locale.getDefault(), "Folder \"%s\" created: %b", folder.getPath(), folder.mkdirs()));
        }
    }

    private File composeFile() {
        int num = 0;
        String name = "audio_recording_";
        File file = setFile(composeName(name, formatNumber(num)));
        while (file.exists()) {
            file = setFile(composeName(name, formatNumber(++num)));
        }
        return file;
    }

    private String composeName(String name, String num) {
        return AUDIO_FOLDER + name + num + AUDIO_FORMAT_EXTENSION;
    }

    private String formatNumber(int number) {
        return String.format(Locale.getDefault(), "%03d", number);
    }

    private File setFile(String name) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), name);
    }
}
