package com.example.clapphonefinder.service;

import android.media.AudioRecord;

import com.example.clapphonefinder.utils.Recorder;
import com.musicg.api.ClapApi;
import com.musicg.wave.WaveHeader;

import java.util.LinkedList;

public class DetectorThread extends Thread {
    private Thread _thread;
    private ClapApi clapApi;
    private int clapCheckLength;
    private int clapPassScore;
    private LinkedList<Boolean> clapResultList;
    private int numClaps;
    private OnClapListener onClapListener;
    private Recorder recorder;
    private int totalClapsDetected;
    private WaveHeader waveHeader;

    public interface OnClapListener {
        void onClap();
    }

    public DetectorThread(Recorder recorder) {
        this.clapResultList = new LinkedList();
        this.totalClapsDetected = 0;
        this.clapCheckLength = 3;
        this.clapPassScore = 1;
        this.recorder = recorder;
        AudioRecord audioRecord = recorder.getAudioRecord();
        if (audioRecord != null) {
            int bitsPerSample = 0;
            if (audioRecord.getAudioFormat() == 2) {
                bitsPerSample = 16;
            } else if (audioRecord.getAudioFormat() == 3) {
                bitsPerSample = 8;
            }
            int channel = 0;
            if (audioRecord.getChannelConfiguration() == 16) {
                channel = 1;
            }
            this.waveHeader = new WaveHeader();
            this.waveHeader.setChannels(channel);
            this.waveHeader.setBitsPerSample(bitsPerSample);
            this.waveHeader.setSampleRate(audioRecord.getSampleRate());
            this.clapApi = new ClapApi(this.waveHeader);
        }
    }

    private void initBuffer() {
        this.numClaps = 0;
        this.clapResultList.clear();
        for (int i = 0; i < this.clapCheckLength; i++) {
            this.clapResultList.add(Boolean.valueOf(false));
        }
    }

    public void start() {
        this._thread = new Thread(this);
        this._thread.start();
    }

    public void stopDetection() {
        this._thread = null;
    }

    public void run() {
        try {
            initBuffer();
            Thread thisThread = Thread.currentThread();
            while (this._thread == thisThread) {
                byte[] buffer = this.recorder.getFrameBytes();
                if (buffer != null) {
                    try {
                        boolean isClap = this.clapApi.isClap(buffer);
                        if (((Boolean) this.clapResultList.getFirst()).booleanValue()) {
                            this.numClaps--;
                        }
                        this.clapResultList.removeFirst();
                        this.clapResultList.add(Boolean.valueOf(isClap));
                        if (isClap) {
                            this.numClaps++;
                        }
                        if (this.numClaps >= this.clapPassScore) {
                            initBuffer();
                            this.totalClapsDetected++;
                            if (this.onClapListener != null) {
                                this.onClapListener.onClap();
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    if (((Boolean) this.clapResultList.getFirst()).booleanValue()) {
                        this.numClaps--;
                    }
                    this.clapResultList.removeFirst();
                    this.clapResultList.add(Boolean.valueOf(false));
                }
            }
        } catch (Exception e2) {
        }
    }

    public void setOnClapListener(OnClapListener onClapListener) {
        this.onClapListener = onClapListener;
    }

    public int getTotalClapsDetected() {
        return this.totalClapsDetected;
    }
}
