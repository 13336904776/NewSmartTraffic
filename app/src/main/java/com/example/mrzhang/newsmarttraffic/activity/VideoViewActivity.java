package com.example.mrzhang.newsmarttraffic.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mrzhang.newsmarttraffic.R;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private SeekBar seekBar;
    /**
     * 播放
     */
    private Button btn_play;
    /**
     * 暂停
     */
    private Button btn_pause;
    /**
     * 重播
     */
    private Button btnReplay;
    /**
     * 停止
     */
    private Button btnStop;
    private VideoView vv_video;
    private final String TAG = "main";
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        initView();
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(change);
    }

    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (vv_video != null && vv_video.isPlaying()) {
                // 设置当前播放的位置
                vv_video.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    private void initView() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(this);
        btnReplay = (Button) findViewById(R.id.btn_replay);
        btnReplay.setOnClickListener(this);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(this);
        vv_video = (VideoView) findViewById(R.id.vv_videoview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_play:
                play(0);
                break;
            case R.id.btn_pause:
                pause();
                break;
            case R.id.btn_replay:
                replay();
                break;
            case R.id.btn_stop:
                stop();
                break;
        }
    }

    protected void play(int msec) {
                 Log.i("zz", " 获取视频文件地址");
               String path = Environment.getExternalStorageDirectory().getPath() + "/Download/大人的片想.mp4";
                File file = new File(path);
                if (!file.exists()) {
                       Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show();
                       return;
                    }

               Log.i("zz", "指定视频源路径");
               vv_video.setVideoPath(file.getAbsolutePath());
                Log.i("zz", "开始播放");
                vv_video.start();

                 // 按照初始位置播放
                vv_video.seekTo(msec);
                // 设置进度条的最大进度为视频流的最大播放时长
                 seekBar.setMax(vv_video.getDuration());

                // 开始线程，更新进度条的刻度
               new Thread() {

                   @Override
            public void run() {
                         try {
                                 isPlaying = true;
                                 while (isPlaying) {
                                         // 如果正在播放，没0.5.毫秒更新一次进度条
                                         int current = vv_video.getCurrentPosition();
                                         seekBar.setProgress(current);

                                         sleep(500);
                                     }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                     }
 }.start();
         // 播放之后设置播放按钮不可用
         btn_play.setEnabled(false);

         vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

             @Override
     public void onCompletion(MediaPlayer mp) {
          // 在播放完毕被回调
                 btn_play.setEnabled(true);
                           }
 });

         vv_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {

             @Override
             public boolean onError(MediaPlayer mp, int what, int extra) {
                                // 发生错误重新播放
                                play(0);
                                isPlaying = false;
                                return false;
                            }
 });
     }

     /**
      * 重新开始播放
       */
         protected void replay() {
             if (vv_video != null && vv_video.isPlaying()) {
                     vv_video.seekTo(0);
                     Toast.makeText(this, "重新播放", Toast.LENGTH_SHORT).show();
                     btn_pause.setText("暂停");
                     return;
                 }
             isPlaying = false;
             play(0);

         }
     /**
      * 暂停或继续
      */
         protected void pause() {
             if (btn_pause.getText().toString().trim().equals("继续")) {
                   btn_pause.setText("暂停");
                   vv_video.start();
                   Toast.makeText(this, "继续播放", Toast.LENGTH_SHORT).show();
                   return;
               }
           if (vv_video != null && vv_video.isPlaying()) {
                   vv_video.pause();
                   btn_pause.setText("继续");
                   Toast.makeText(this, "暂停播放", Toast.LENGTH_SHORT).show();
               }
       }

     /*
     * 停止播放
     */
         protected void stop() {
             if (vv_video != null && vv_video.isPlaying()) {
                     vv_video.stopPlayback();
                     btn_play.setEnabled(true);
                     isPlaying = false;
                 }
         }

}
