package com.boss.view.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.GLException;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.boss.R;
import com.boss.model.BaseCameraInterface;
import com.boss.widget.FilterAdapter;
import com.boss.widget.FilterType;
import com.boss.widget.SampleCameraGLView;
import com.daasuu.gpuv.camerarecorder.CameraRecordListener;
import com.daasuu.gpuv.camerarecorder.GPUCameraRecorder;
import com.daasuu.gpuv.camerarecorder.GPUCameraRecorderBuilder;
import com.daasuu.gpuv.camerarecorder.LensFacing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class BaseCameraActivity extends AppCompatActivity implements BaseCameraInterface {
    private static final String TAG = "BaseCameraActivity";
    protected GPUCameraRecorder GPUCameraRecorder;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    private SampleCameraGLView sampleGLView;
    private String filepath;
    private TextView recordBtn;
    private boolean toggleClick = false;
    private File video, thumbnail;

    private ListView lv;
    private CountDownTimer timer;

    public static void exportMp4ToGallery(Context context, String filePath, BaseCameraInterface baseCameraInterface) {
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));

        new Handler().postDelayed(baseCameraInterface::onFinish, 2000);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HH:mm:ss").format(new Date()) + "BossReel.mp4";
    }

    public static File getAndroidMoviesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    private static void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HH:mm:ss").format(new Date()) + "GPUCameraRecorder.png";
    }

    public static File getAndroidImageFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);

            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap retrieveVideoFrameFromVideo(String p_videoPath) throws Throwable {
        Bitmap m_bitmap;
        MediaMetadataRetriever m_mediaMetadataRetriever = null;
        try {
            m_mediaMetadataRetriever = new MediaMetadataRetriever();
            m_mediaMetadataRetriever.setDataSource(p_videoPath);
            m_bitmap = m_mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception m_e) {
            throw new Throwable(
                    "Exception in retrieveVideoFrameFromVideo(String p_videoPath) "
                            + m_e.getMessage() + "\nFile Path - " + p_videoPath);
        } finally {
            if (m_mediaMetadataRetriever != null) {
                m_mediaMetadataRetriever.release();
            }
        }
        return m_bitmap;
    }

    protected void onCreateActivity() {
        recordBtn = findViewById(R.id.btn_record);
        recordBtn.setOnClickListener(v -> {
            if (recordBtn.getText().equals(getString(R.string.app_record))) {
                filepath = getVideoFilePath();
                GPUCameraRecorder.start(filepath);
                recordBtn.setText(R.string.stop);
                lv.setVisibility(View.GONE);
            } else {
                GPUCameraRecorder.stop();
                recordBtn.setText(getString(R.string.app_record));
                lv.setVisibility(View.VISIBLE);
                timer.cancel();
            }
        });

        findViewById(R.id.btn_flash).setOnClickListener(v -> {
            if (GPUCameraRecorder != null && GPUCameraRecorder.isFlashSupport()) {
                GPUCameraRecorder.switchFlashMode();
                GPUCameraRecorder.changeAutoFocus();
            }
        });

        findViewById(R.id.btn_switch_camera).setOnClickListener(v -> {
            releaseCamera();
            if (lensFacing == LensFacing.BACK) {
                lensFacing = LensFacing.FRONT;
            } else {
                lensFacing = LensFacing.BACK;
            }
            toggleClick = true;
        });

        findViewById(R.id.btn_image_capture).setOnClickListener(v ->
                captureBitmap(bitmap -> new Handler().post(() -> {
                    String imagePath = getImageFilePath();
                    saveAsPngImage(bitmap, imagePath);
                    exportPngToGallery(getApplicationContext(), imagePath);
                })));

        lv = findViewById(R.id.filter_list);

        final List<FilterType> filterTypes = FilterType.createFilterList();
        lv.setAdapter(new FilterAdapter(this, R.layout.row_white_text, filterTypes).whiteMode());
        lv.setOnItemClickListener((parent, view, position, id) -> {
            if (GPUCameraRecorder != null) {
                GPUCameraRecorder.setFilter(FilterType.createGlFilter(filterTypes.get(position), getApplicationContext()));
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (GPUCameraRecorder != null) {
            GPUCameraRecorder.stop();
            GPUCameraRecorder.release();
            GPUCameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
            sampleGLView = null;
        }

        timer.cancel();
    }

    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.wrap_view);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleCameraGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (GPUCameraRecorder == null) return;
                GPUCameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }

    private void setTimer() {
        timer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: currentMillis --->" + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                GPUCameraRecorder.stop();
                recordBtn.setText(getString(R.string.app_record));
                lv.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void setUpCamera() {
        setUpCameraView();

        GPUCameraRecorder = new GPUCameraRecorderBuilder(this, sampleGLView)
//                .recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                        runOnUiThread(() -> findViewById(R.id.btn_flash).setEnabled(flashSupport));
                    }

                    @Override
                    public void onRecordComplete() {
                        exportMp4ToGallery(getApplicationContext(), filepath, BaseCameraActivity.this);

                    }

                    @Override
                    public void onRecordStart() {
                        runOnUiThread(() -> lv.setVisibility(View.GONE));
                        setTimer();
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("GPUCameraRecorder", exception.toString());
                        timer.cancel();
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(() -> setUpCamera());
                        }
                        toggleClick = false;
                    }

                    @Override
                    public void onVideoFileReady() {

                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .build();
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {
        sampleGLView.queueEvent(() -> {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
            Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);

            runOnUiThread(() -> bitmapReadyCallbacks.onBitmapReady(snapshotBitmap));
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {
        int[] bitmapBuffer = new int[w * h];
        int[] bitmapSource = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveAsPngImage(@NonNull Bitmap bitmap, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        startActivity(new Intent(BaseCameraActivity.this, VideoEditActivity.class).putExtra("VideoUri", filepath));
        finish();
    }

    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }
}