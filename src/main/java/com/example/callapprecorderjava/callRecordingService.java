package com.example.callapprecorderjava;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

public class callRecordingService extends Service {
    private String outputFilePath;
    private MediaRecorder recorder;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        if (action != null) {
            if (action.equals("StartRecording")) {
                startRecording();
            } else if (action.equals("EndRecording")) {
                stopRecording();
            }
        }
        return START_NOT_STICKY;
    }

    private void startRecording() {
        File dir = new File(Environment.getExternalStorageDirectory(), "callRecordings");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        outputFilePath = dir.getAbsolutePath() + "/Recording_" + System.currentTimeMillis() + ".mp4";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioEncodingBitRate(128000);
        recorder.setAudioSamplingRate(44100);
        recorder.setOutputFile(outputFilePath);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            recorder.release();
            recorder = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
