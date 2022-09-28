package com.boss.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.boss.R;
import com.boss.databinding.ActivityHomeBinding;
import com.boss.view.fragment.HomeFragment;
import com.boss.view.fragment.NotificationsFragment;
import com.boss.view.fragment.ProfileFragment;
import com.boss.view.fragment.SearchFragment;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class HomeActivity extends AppCompatActivity {

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
    public void addBottomSheet () {
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
          //  startActivity(new Intent(this, CreateProjectActivity.class));
        });
        if (list != null) list.setOnClickListener(view -> {
            mBottomSheetDialog.dismiss();
         //   startActivity(new Intent(this, CreateProjectActivity.class));
        });

        mBottomSheetDialog.show();
    }

}