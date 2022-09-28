package com.boss.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivityOtpVerificationBinding;
import com.boss.model.Response_Models.OtpResModel;
import com.boss.model.Response_Models.ResendOtpResModel;
import com.boss.util.Constants;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpVerificationActivity extends AppCompatActivity {
    private static final String TAG = "OtpVerificationActivity";
    private EditText[] editTexts;
    private long mTimeLeftInMillis;
    String timeLeftFormatted, user_id = "";

    private ActivityOtpVerificationBinding binding;
    private final Activity activity = OtpVerificationActivity.this;
    private Session session;
    private String mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.white));

        if (getIntent() != null){
            String otp = getIntent().getStringExtra(Constants.Key.otp);
            user_id = getIntent().getStringExtra(Constants.Key.user_id);
            mobile = getIntent().getStringExtra(Constants.Key.mobile);
            Toast.makeText(this, "" + otp, Toast.LENGTH_SHORT).show();
        }

        binding.AGOTPVERIFYE.setOnClickListener(v -> {
            String one = binding.edtOne.getText().toString();
            String two = binding.edtTwo.getText().toString();
            String three = binding.edtThree.getText().toString();
            String four = binding.edtFour.getText().toString();
            final String otp = one + two + three + four;
            if (valid()) {
                otpVerification(otp);
             }
        });


        editTexts = new EditText[]{binding.edtOne, binding.edtTwo, binding.edtThree, binding.edtFour};


        binding.edtOne.addTextChangedListener(new PinTextWatcher(0));
        binding.edtTwo.addTextChangedListener(new PinTextWatcher(1));
        binding.edtThree.addTextChangedListener(new PinTextWatcher(2));
        binding.edtFour.addTextChangedListener(new PinTextWatcher(3));

        binding.edtOne.setOnKeyListener(new PinOnKeyListener(0));
        binding.edtTwo.setOnKeyListener(new PinOnKeyListener(1));
        binding.edtThree.setOnKeyListener(new PinOnKeyListener(2));
        binding.edtFour.setOnKeyListener(new PinOnKeyListener(3));

//        long mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                binding.txtResend.setClickable(true);
                binding.txtResend.setText("Resend a new code.");

                binding.txtResend.setOnClickListener(view -> otpResend(mobile));
            }
        }.start();
    }

    private void otpVerification(String otp) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        Log.d(TAG, "otpVerification() called with: otp = [" + otp + "]" + " user_id = [" + user_id + "]");
        apiService.verify_otp(user_id, otp).enqueue(new Callback<OtpResModel>() {
            @Override
            public void onResponse(@NonNull Call<OtpResModel> call, @NonNull Response<OtpResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null){
                        if (response.body().getResult().equalsIgnoreCase("true")){
                            startActivity(new Intent(activity, AllowLocationActivity.class));

                            session.setUser_Id(response.body().getData().getId());
                            session.setValue(Constants.Key.mobile, response.body().getData().getMobile());
                            session.setValue(Constants.Key.user_type, response.body().getData().getType());
                            session.setLogin(true);

                            finish();
                        } else Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OtpResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void otpResend(String mobile) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.resend_otp(mobile).enqueue(new Callback<ResendOtpResModel>() {
            @Override
            public void onResponse(@NonNull Call<ResendOtpResModel> call, @NonNull Response<ResendOtpResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null){
                        if (response.body().getResult().equalsIgnoreCase("true")){
                            Toast.makeText(activity, ""+response.body().getMsg() + "\n" + response.body().getData().getOtp(), Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResendOtpResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean valid() {
        boolean flag = true;
        if (binding.edtOne.getText().toString().isEmpty()) {
            binding.edtOne.setError("Fill Otp");
            flag = false;
        } else if (binding.edtTwo.getText().length() <= 0) {
            binding.edtTwo.setError("Fill Otp");
            flag = false;
        } else if (binding.edtThree.getText().length() <= 0) {
            binding.edtThree.setError("Fill Otp");
            flag = false;
        } else if (binding.edtFour.getText().length() <= 0) {
            binding.edtFour.setError("Fill Otp");
            flag = false;
        }
        return flag;
    }

    public class PinTextWatcher implements TextWatcher {
        private final int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }


        @Override
        public void afterTextChanged(Editable s) {
            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0));

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);
            editTexts[currentIndex].setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sotpbackground));
            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                // hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }
    }

    public class PinOnKeyListener implements View.OnKeyListener {
        private final int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        binding.txtResend.setClickable(false);
        binding.txtResend.setText(timeLeftFormatted);
    }

}