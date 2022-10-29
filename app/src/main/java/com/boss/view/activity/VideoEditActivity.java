package com.boss.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.boss.R;
import com.boss.databinding.ActivityVideoEditBinding;
import com.boss.util.BaseUrl;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class VideoEditActivity extends AppCompatActivity {

    private final String TAG = "VideoEditActivity";
    File video_file, video_gif, thumbnail;
    private ActivityVideoEditBinding binding;
    private boolean isPlaying;
    private Activity activity;
    private Session session;
    private String videoUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = VideoEditActivity.this;
        session = new Session(this);

        videoUri = getIntent().getStringExtra("VideoUri");

        progressDialog = new ProgressDialog(activity);

        SimpleMediaSource mediaSource = new SimpleMediaSource(videoUri);
        Log.e(TAG, "onBindViewHolder: Uri" + videoUri);
        binding.videoPlayer.hideController();
        binding.videoPlayer.setControllerAutoShow(true);
        binding.videoPlayer.setUseController(true);
        binding.videoPlayer.changeWidgetVisibility(R.id.exo_player_controller_back, View.GONE);
        binding.videoPlayer.play(mediaSource);
        isPlaying = true;

        binding.parent.setOnClickListener(view -> {
            if (view != null) {
                if (isPlaying) {
                    binding.videoPlayer.pause();
                    isPlaying = false;
                } else {
                    isPlaying = true;
                    binding.videoPlayer.resume();
                }
            }
        });
        binding.fabUpload.setOnClickListener(view -> openBottomSheet());

    }

    private void openBottomSheet() {
        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(activity);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.video_post_bottom_sheet, null);

        mBottomSheetDialog.setContentView(sheetView);

        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);

        EditText description = mBottomSheetDialog.findViewById(R.id.edt_description);
        TextView add_reel = mBottomSheetDialog.findViewById(R.id.text_add_reel);

        if (add_reel != null && description != null)
        add_reel.setOnClickListener(view -> {
            progressDialog.show();
            video_file = new File(videoUri);
            Log.d(TAG, "add_reel() called videoFile-->" + video_file);
            addReel(description.getText().toString());
//                convert_to_file(videoUri, description.getText().toString());
        });

        mBottomSheetDialog.show();
    }

    private void addReel(String description) {
        ANRequest.MultiPartBuilder anAdd = AndroidNetworking.upload(BaseUrl.Base_Url + BaseUrl.add_reels);
        anAdd.addMultipartParameter("user_id", session.getUser_Id());
        anAdd.addMultipartParameter("type", "video");
        anAdd.addMultipartParameter("description", description);

        if (video_file != null) anAdd.addMultipartFile("image", video_file);
        if (video_gif != null) anAdd.addMultipartFile("video_gif", video_gif);
        if (thumbnail != null) anAdd.addMultipartFile("thumbnail", thumbnail);

        anAdd.build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            Log.d("---rrrProfile", "save_postsave_post" + jsonObject.toString());
                            // JSONObject obj = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            Toast.makeText(activity, ""+result, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, HomeActivity.class));
                        } catch (JSONException e) {
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("TAG", "onError: "+anError);
                        progressDialog.dismiss();
                    }
                });
    }
}