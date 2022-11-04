package com.boss.view.activity;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.TabLayoutUserProfileAdapter;
import com.boss.databinding.ActivityUserProfileBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.util.Session;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private final Activity activity = UserProfileActivity.this;
    private ActivityUserProfileBinding binding;
    private Session session;
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);

        user_id = getIntent().getStringExtra("id");

        init();
    }

    private void init() {
        getProfile();

        binding.followingLay.setOnClickListener(this);
        binding.followersLay.setOnClickListener(this);
        binding.icMoreOption.setOnClickListener(this);
        binding.tvFollow.setOnClickListener(this);
        ArrayList<Integer> Integers = new ArrayList<>();
        Integers.add(R.drawable.ic_instagram_reel);
        Integers.add(R.drawable.ic_at);
        Integers.add(R.drawable.ic_info);
        Integers.add(R.drawable.ic_star);
        Integers.add(R.drawable.ic_stream_svgrepo);

        TabLayoutUserProfileAdapter tabLayoutAdapterSecond = new TabLayoutUserProfileAdapter(UserProfileActivity.this);
        binding.ViewPager.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.ViewPager, (tab, position) -> tab.setIcon(Integers.get(position))).attach();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.icMoreOption) dialogThreeDots();
        else if (view == binding.tvFollow) followUnfollow();
        else if (view == binding.followersLay) startActivity(new Intent(activity, RelationsActivity.class).putExtra("type", 1).putExtra("user_id", user_id));
        else if (view == binding.followingLay) startActivity(new Intent(activity, RelationsActivity.class).putExtra("type", 0).putExtra("user_id", user_id));
    }

    private void dialogThreeDots() {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_three_dots);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue_curve_background);
        dialog.show();
    }

    private void followUnfollow() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.follow_Unfollow(session.getUser_Id(), user_id).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getMsg().equalsIgnoreCase("Followed")) {
                                binding.tvFollow.setText(R.string.following);
                                binding.tvFollowers.setText(String.valueOf(Integer.parseInt(binding.tvFollowers.getText().toString()) + 1));
                            } else {
                                binding.tvFollow.setText(R.string.follow);
                                binding.tvFollowers.setText(String.valueOf(Integer.parseInt(binding.tvFollowers.getText().toString()) - 1));
                            }
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void getProfile() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getProfile(user_id, session.getUser_Id()).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ProfileModel.Data data = response.body().getData();

                            binding.tvUserName.setText(data.getName());
                            binding.tvCompanyName.setText(data.getCompany());
                            binding.tvFollowers.setText(String.valueOf(data.getTotalFollower()));
                            binding.tvFollowing.setText(String.valueOf(data.getTotalFollowing()));
                            binding.tvPosts.setText(String.valueOf(data.getTotalPost()));

                            if (data.getiFollowed() == 0)
                                    binding.tvFollow.setText(R.string.follow);
                            else binding.tvFollow.setText(R.string.following);

                            if (data.getImage().length() != 0) {
                                Glide.with(activity)
                                        .load(response.body().getPath() + data.getImage())
                                        .error(R.drawable.ic_user)
                                        .placeholder(R.drawable.ic_user)
                                        .into(binding.icProfileImg);
                            }
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}