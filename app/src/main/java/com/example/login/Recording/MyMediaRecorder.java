package com.example.login.Recording;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MyMediaRecorder {
    public File recordedFile;
    private MediaRecorder mediaRecorder = null;
    private final String TAG = "MyMediaRecorder";
    private boolean isRecording = false;

    public void MediaRecorder(){

    }
    public boolean startRecord(){
        if (mediaRecorder == null) {
            try {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
                return true;
            } catch (IOException e) {
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
                Log.e(TAG, "IO exception when start recording");
                isRecording = false;
                return false;
            } catch (IllegalStateException e) {
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
                Log.e(TAG, "the hard ware is already used by other");
                isRecording = false;
                return false;
            }
        }else {
            try {
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
                return true;
            }catch (Exception e){
                mediaRecorder.reset();
                mediaRecorder.release();
                isRecording = false;
                return false;
            }
        }
    }

    public void  stopRecording(){
        if (mediaRecorder != null){
            if (isRecording){
                try{
                    mediaRecorder.stop();
                    mediaRecorder.release();
                }catch (Exception e){
                    Log.e(TAG,"error whend stop recording");
                }
            }
            mediaRecorder = null;
            isRecording = false;
        }
    }

    //get the amplitude
    public float getMaxAmp(){
        if (mediaRecorder == null){
            return 0;
        }else {
            try {
                return mediaRecorder.getMaxAmplitude();
            }catch (Exception e){
                Log.e(TAG,"error in get max amd:"+e.toString());
                return 0;
            }
        }
    }


    public File getRecordedFile(){
        return this.recordedFile;
    }

    public void setRecordedFile(File tagert){
        this.recordedFile=tagert;
    }

    public float getDBValue(){
        float volume = this.getMaxAmp();
        return 20*(float) Math.log10(volume);
    }

}
