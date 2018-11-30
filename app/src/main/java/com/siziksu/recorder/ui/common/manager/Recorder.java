package com.siziksu.recorder.ui.common.manager;

import android.media.MediaRecorder;
import android.view.View;

import com.siziksu.recorder.R;
import com.siziksu.recorder.common.function.Consumer;
import com.siziksu.recorder.common.utils.Print;

import java.io.IOException;

import javax.inject.Inject;

public class Recorder {

    public final static String AUDIO_FORMAT_EXTENSION = ".aac";

    @Inject
    FileManager fileManager;

    private MediaRecorder recorder;
    private boolean recording;
    private Consumer<Integer> listener;

    @Inject
    public Recorder() {}

    public void setListener(Consumer<Integer> listener) {
        this.listener = listener;
    }

    public void onDestroy() {
        if (recorder != null) {
            close();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (recording) { return; }
                startRecording();
                recording = !recording;
                break;
            case R.id.stop:
                if (!recording) { return; }
                stopRecording();
                recording = !recording;
                break;
            default:
                break;

        }
    }

    private void startRecording() {
        listener.accept(R.string.recording);
        Print.info("Recording");
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(fileManager.getOutputAudioFile());
        try {
            recorder.prepare();
        } catch (IOException e) {
            Print.error("Recorder prepare failed");
        }
        recorder.start();
    }

    private void stopRecording() {
        listener.accept(R.string.press_start_to_record);
        Print.info("Stopping");
        close();
    }

    private void close() {
        if (recorder != null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }
}
