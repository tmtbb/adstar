package com.cloudTop.starshare.widget.audioplayer;

import android.media.AudioManager;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * Created by Administrator on 2017/8/24.
 */

public class MyAudioPlayer {
    private IjkMediaPlayer ijkMediaPlayer;
    private boolean mIsStopped=true;


    public MyAudioPlayer() {
        // 初始化播放器
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.setOnPreparedListener(mPreparedListener);
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {

           start();
        }
    };

    public MyAudioPlayer setDataSource(String path) {
        try {
            if(!mIsStopped){
                    stop();
            }
            ijkMediaPlayer.setDataSource(path);
            ijkMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void start() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.start();
            mIsStopped=false;
        }
    }

    public void stop(){
        if (ijkMediaPlayer != null&&!mIsStopped) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.reset();
        }
        mIsStopped = true;
//        mMediaPlayer = null;
    }

    public void release(){
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
    }
    public boolean getMediaPlayerStatus(){
        return mIsStopped;
    }

//
//    public void setPreparedListener(IMediaPlayer.OnPreparedListener preparedListener){
//        this.preparedListener=preparedListener;
//
//    }
}
