package com.example.mrzhang.newsmarttraffic.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mrzhang.newsmarttraffic.R;

public class MyVideoActivity extends BaseActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        initView();

        String videopath = Environment.getExternalStorageDirectory().getPath() + "/Download/God is a woman.mkv";
        Uri uri = Uri.parse(videopath);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.start();

    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);

    }
}
