package com.boss.view.activity;

import static com.boss.util.ImageShortCut.bitmapToFile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.boss.R;
import com.boss.databinding.ActivityUpdateProfileBinding;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;


public class UpdateProfile extends AppCompatActivity implements View.OnClickListener {
    private final Activity activity = UpdateProfile.this;
    private ActivityUpdateProfileBinding binding;
    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivBack.setOnClickListener(this);
        binding.tvMale.setOnClickListener(this);
        binding.tvFeMale.setOnClickListener(this);
        binding.tvOthers.setOnClickListener(this);
        binding.tvChangePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.tvMale) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background_theme);
            binding.tvFeMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background);
        } else if (view == binding.tvFeMale) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvFeMale.setBackgroundResource(R.drawable.curve_background_theme);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background);
        } else if (view == binding.tvOthers) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvFeMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background_theme);
        } else if (view == binding.tvChangePhoto) {
            selectImage();
        }

    }

    private void selectImage() {
        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());

        dialog.setOnPickCancel(dialog::dismiss).setOnPickResult(r -> {
            if (r.getError() == null) {

                binding.ivUserImage.setImageBitmap(r.getBitmap());
                imgFile = bitmapToFile(activity, r.getBitmap());
                } else {
                Toast.makeText(activity, r.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }).show(this);
    }

}