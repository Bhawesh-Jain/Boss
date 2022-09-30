package com.boss.view.activity;

import static com.boss.util.BaseUrl.Base_Url;
import static com.boss.util.BaseUrl.signup;
import static com.boss.util.ImageShortCut.bitmapToFile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.boss.R;
import com.boss.databinding.ActivitySignUpBinding;
import com.boss.util.Constants;
import com.boss.util.ImageShortCut;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private final String TAG = SignUpActivity.class.getSimpleName();
    private ActivitySignUpBinding binding;
    private Session session;
    private final Activity activity = SignUpActivity.this;
    private File imageFile = null;
    private final String[] storagePermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private final String[] cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);

        binding.cvUserImage.setOnClickListener(v -> selectImage());

        binding.tvSubmit.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString();
            String phone = binding.edtNumber.getText().toString();
            String email = binding.edtEmail.getText().toString();

            if (name.length() < 3)
                binding.edtName.setError("Enter a valid name");
            else if (phone.length() > 10)
                binding.edtNumber.setError("Enter a valid number");
            else if (email.length() == 0)
                binding.edtEmail.setError("Enter a valid email");
            else if (imageFile == null)
                Toast.makeText(activity, "Select Profile Image to Continue", Toast.LENGTH_SHORT).show();
            else
                signUp(name, phone, email, imageFile);
        });

        binding.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(activity, WelcomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        });
    }

    private void selectImage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.create();
        final View popupView = getLayoutInflater().inflate(R.layout.image_select_mode_dialog_layout, null);

        dialogBuilder.setTitle("Pick Method: ");
        dialogBuilder.setView(popupView);
        Dialog dialog = dialogBuilder.create();

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout camera = popupView.findViewById(R.id.selectImage_dialog_camera);
        LinearLayout gallery = popupView.findViewById(R.id.selectImage_dialog_gallery);

        camera.setOnClickListener(view -> {
            getImage(100);
            dialog.dismiss();
        });

        gallery.setOnClickListener(view -> {
            getImage(200);
            dialog.dismiss();
        });
    }

    private void getImage(int requestCode) {
        if (requestCode == 100) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
                requestPermissions(cameraPermission, 100);
            } else {
                capturePhoto();
            }
        } else if (requestCode == 200) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
                requestPermissions(storagePermission, 200);
            } else {
                getImageFromGallery.launch("image/*");
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    private Uri photoURI;
    private void capturePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            getFileFromCamera.launch(photoURI);
        }
    }

    private final ActivityResultLauncher<String> getImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        imageFile = ImageShortCut.from(getApplicationContext(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Bitmap d;
                    try {
                        d = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                        Bitmap bitmap = Bitmap.createScaledBitmap(d, 512, nh, true);
                        binding.icUserImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void signUp(String name, String mobile, String email, File file) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        AndroidNetworking.upload(Base_Url + signup)
                .addMultipartParameter("name", name)
                .addMultipartParameter("mobile", mobile)
                .addMultipartParameter("email", email)
                .addMultipartFile("image", file)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            Log.d(TAG, "onResponse() called with: jsonObject = [" + jsonObject + "]");
                            progressDialog.dismiss();
                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            if (result.equalsIgnoreCase("true")) {
                                session.setValue("firstTime", "false");
                                int otp = jsonObject.getInt("otp");
                                String user_id = jsonObject.getString("user_id");

                                startActivity(new Intent(activity, OtpVerificationActivity.class)
                                        .putExtra(Constants.Key.otp, String.valueOf(otp))
                                        .putExtra(Constants.Key.mobile, binding.edtNumber.getText().toString())
                                        .putExtra(Constants.Key.user_id, user_id));
                                finish();
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                Bitmap bitmap = BitmapFactory.decodeFile(data.getData().getPath());
                binding.icUserImage.setImageBitmap(bitmap);

                imageFile = bitmapToFile(activity, bitmap);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private final ActivityResultLauncher<Uri> getFileFromCamera = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        try {
                            imageFile = ImageShortCut.from(getApplicationContext(), photoURI);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap d;
                        try {
                            d = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                            int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                            Bitmap bitmap = Bitmap.createScaledBitmap(d, 512, nh, true);
                            binding.icUserImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


}