package com.example.clapphonefinder.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioRecord;

import androidx.core.app.ActivityCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class Recorder {
    private int audioEncoding;
    private AudioRecord audioRecord;
    byte[] buffer;
    private int channelConfiguration;
    private int frameByteSize;
    private int sampleRate;

    public Recorder(Context context) {
        this.sampleRate = 44100;
        this.frameByteSize = AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT;
        this.channelConfiguration = 16;
        this.audioEncoding = 2;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.audioRecord = new AudioRecord(1, this.sampleRate, this.channelConfiguration, this.audioEncoding, AudioRecord.getMinBufferSize(this.sampleRate, this.channelConfiguration, this.audioEncoding));
        this.buffer = new byte[this.frameByteSize];
    }

    public AudioRecord getAudioRecord() {
        return this.audioRecord;
    }

    public boolean isRecording() {
        if (this.audioRecord.getRecordingState() == 3) {
            return true;
        }
        return false;
    }

    public void startRecording() {
        try {
            this.audioRecord.startRecording();
        } catch (Exception e) {
        }
    }

    public void stopRecording() {
        try {
            this.audioRecord.stop();
            this.audioRecord.release();
        } catch (Exception e) {
        }
    }

    public byte[] getFrameBytes() {
        this.audioRecord.read(this.buffer, 0, this.frameByteSize);
        int totalAbsValue = 0;
        for (int i = 0; i < this.frameByteSize; i += 2) {
            totalAbsValue += Math.abs((short) (this.buffer[i] | (this.buffer[i + 1] << 8)));
        }
        if (((float) ((totalAbsValue / this.frameByteSize) / 2)) < 30.0f) {
            return null;
        }
        return this.buffer;
    }
}
