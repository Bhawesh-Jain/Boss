package com.boss.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.boss.R;
import com.boss.adapter.HomeInterface;
import com.boss.databinding.ActivityHomeBinding;
import com.boss.view.fragment.NotificationsFragment;
import com.boss.view.fragment.ProfileFragment;
import com.boss.view.fragment.SearchFragment;
import com.boss.view.homepage.HomeFragment;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeInterface {

    private final Activity activity = HomeActivity.this;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();

        window.setStatusBarColor(getColor(R.color.black));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications,
                R.id.navigation_live, R.id.profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        binding.navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment_home = new HomeFragment();
                    FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.nav_host_fragment_activity_home, fragment_home);
                    ft_home.commit();
                    return true;

                case R.id.navigation_search:
                    Fragment fragment_dashboard = new SearchFragment();
                    FragmentTransaction ft_search = getSupportFragmentManager().beginTransaction();
                    ft_search.replace(R.id.nav_host_fragment_activity_home, fragment_dashboard);
                    ft_search.commit();
                    return true;

                case R.id.navigation_notifications:
                    Fragment fragment_notification = new NotificationsFragment();
                    FragmentTransaction ft_add = getSupportFragmentManager().beginTransaction();
                    ft_add.replace(R.id.nav_host_fragment_activity_home, fragment_notification);
                    ft_add.commit();
                    return true;

                case R.id.navigation_live:
                    addBottomSheet();
                    return true;

                case R.id.profile:
                    Fragment fragment_profile = new ProfileFragment();
                    FragmentTransaction ft_add1 = getSupportFragmentManager().beginTransaction();
                    ft_add1.replace(R.id.nav_host_fragment_activity_home, fragment_profile);
                    ft_add1.commit();
                    return true;
            }
            return false;
        });


    }

    public void addBottomSheet() {
        RoundedBottomSheetDialog mBottomSheetDialog = new RoundedBottomSheetDialog(this);
        View sheetView = mBottomSheetDialog.getLayoutInflater().inflate(R.layout.add_bottom_sheet, null);

        mBottomSheetDialog.setContentView(sheetView);

        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBottomSheetDialog.setContentView(sheetView);

        LinearLayout create, list;

        create = mBottomSheetDialog.findViewById(R.id.create_project_ll);
        list = mBottomSheetDialog.findViewById(R.id.list_item_ll);

        if (create != null) create.setOnClickListener(view -> {
            mBottomSheetDialog.dismiss();

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(HomeActivity.this, PostVideoActivity.class));
            } else
                Dexter.withContext(this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        ).withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                startActivity(new Intent(HomeActivity.this, PostVideoActivity.class));
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            }
                        }).check();

        });
        if (list != null) list.setOnClickListener(view -> {
            mBottomSheetDialog.dismiss();

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(HomeActivity.this, PostVideoActivity.class));
            } else
                Dexter.withContext(this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        ).withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                startActivity(new Intent(HomeActivity.this, PostVideoActivity.class));
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                            }
                        }).check();
        });

        mBottomSheetDialog.show();
    }


    @Override
    public void onClick(String value) {

    }
}