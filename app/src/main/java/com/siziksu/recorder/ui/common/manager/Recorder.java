package com.siziksu.recorder.ui.common.manager;

import android.media.MediaRecorder;

import com.siziksu.recorder.R;
import com.siziksu.recorder.common.function.Action;
import com.siziksu.recorder.common.utils.Print;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class Recorder {

    public final static String AUDIO_FORMAT_EXTENSION = ".aac";

    @Inject
    FileManager fileManager;

    private MediaRecorder recorder;
    private boolean recording;
    private Action maxDurationListener;

    @Inject
    public Recorder() {}

    public void setListener(@NonNull Action maxDurationListener) {
        this.maxDurationListener = maxDurationListener;
    }

    public void onDestroy() {
        if (recorder != null) {
            close();
        }
    }

    public void onClick(int view) {
        switch (view) {
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
        Print.info("Recording");
        recorder = new MediaRecorder();
        recorder.setAudioChannels(1);
        recorder.setAudioSamplingRate(32000);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        recorder.setOutputFile(fileManager.getOutputAudioFile());
        recorder.setAudioEncodingBitRate(16000);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setMaxDuration(20 * 60 * 1000); // 20 minutes
        recorder.setOnInfoListener((mediaRecorder, what, extra) -> {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                maxDurationListener.execute();
            }
        });
        try {
            recorder.prepare();
        } catch (IOException e) {
            Print.error("Recorder prepare failed");
        }
        recorder.start();
    }

    private void stopRecording() {
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
