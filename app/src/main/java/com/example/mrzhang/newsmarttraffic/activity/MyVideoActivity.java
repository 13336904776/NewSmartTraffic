package com.example.mrzhang.newsmarttraffic.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mrzhang.newsmarttraffic.R;

import java.io.File;

public class MyVideoActivity extends BaseActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        initView();

        String videopath = Environment.getExternalStorageDirectory().getPath() + "/Download/大人的片想.mp4";
        Uri uri = Uri.parse(videopath);
//        mVideoView.setMediaController(new MediaController(this));
//        mVideoView.setVideoURI(uri);
        File file = new File(videopath);
        if(!file.exists()){
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show();
            return;
        }
        mVideoView.setVideoPath(file.getAbsolutePath());
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);

    }
}
