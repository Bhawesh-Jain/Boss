package com.boss.view.activity;


import android.os.Bundle;

import com.boss.databinding.ActivityPostVideoBinding;

public class PostVideoActivity extends BaseCameraActivity {
    /*
    private GLSurfaceView sampleGLView;
    private GPUCameraRecorder gpuCameraRecorder;
    private final Activity activity = PostVideoActivity.this;
    private boolean running = false;
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.boss.databinding.ActivityPostVideoBinding binding = ActivityPostVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onCreateActivity();
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;
    }
    /*  @Override
    protected void onResume() {
        super.onResume();
        sampleGLView = new GLSurfaceView(getApplicationContext());
        FrameLayout frameLayout = findViewById(R.id.wrap_view);
        frameLayout.addView(sampleGLView);

        try {
            gpuCameraRecorder.setFilter(new GlBoxBlurFilter());
            gpuCameraRecorder = new GPUCameraRecorderBuilder(activity, sampleGLView)
                    .cameraRecordListener(new CameraRecordListener() {
                        @Override
                        public void onGetFlashSupport(boolean flashSupport) {

                        }

                        @Override
                        public void onRecordComplete() {
                            Toast.makeText(activity, "Completed!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onRecordStart() {
                            Toast.makeText(activity, "Started!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception exception) {
                            Toast.makeText(activity, "" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCameraThreadFinish() {
                            Toast.makeText(activity, "onCameraThreadFinish", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onVideoFileReady() {
                            Toast.makeText(activity, "onVideoFileReady", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .lensFacing(LensFacing.BACK)
                    .build();

            String time = DateFormat.format("yyyy-MM-dd_hh:mm:ss", new Date()).toString();
            File file = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + time
                    + "/recording.mp4");

            binding.buttonStart.setOnClickListener(view -> {
                if (running) {
                    // record stop.
                    running = false;
                    gpuCameraRecorder.stop();
                } else {
                    // record start.
                    running = true;
                    gpuCameraRecorder.start(file.getPath());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sampleGLView.onPause();

        gpuCameraRecorder.stop();
        gpuCameraRecorder.release();
        gpuCameraRecorder = null;

        ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
        sampleGLView = null;
   */
}