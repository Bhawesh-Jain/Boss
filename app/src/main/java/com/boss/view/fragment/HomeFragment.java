package com.boss.view.fragment;

import static com.boss.util.BaseUrl.Base_Url;
import static com.boss.util.BaseUrl.Post_Video_Url;
import static com.boss.util.BaseUrl.get_user_post_home;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.boss.R;
import com.boss.adapter.HomePostAdapter;
import com.boss.adapter.On_Click;
import com.boss.adapter.VideoViewAdapter;
import com.boss.model.HomePostModel;
import com.boss.util.Session;
import com.boss.util.VolleySingleton;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    public static ViewPager2 videoview_pager;
    public static int layout_position = 0;
    public static ArrayList<Integer> video_position = new ArrayList<>();
    static int flag = 0;
    static int like = 0;
    static int like1 = 0;
    static int ref = 0;
    VideoViewAdapter videoViewAdapter;
    ArrayList<String> videos = new ArrayList<>();

    ImageView bookmark, like_img, like_img1, comment_img, refrence_img, report_abuse_img;
    CircleImageView user_image;
    Session session;
    ExoVideoView videoView;
    RecyclerView home_post_list;
    ArrayList<HomePostModel> homePostModels = new ArrayList<>();
    HomePostAdapter homePostAdapter;
    On_Click click;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        videoViewAdapter.onClick("");
        videoview_pager.invalidate();
        // click.onClick("clicked");
        videoview_pager.clearFocus();
        videoViewAdapter.notifyItemRemoved(layout_position);
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        videoview_pager = root.findViewById(R.id.videoview_pager);

        /*bookmark = root.findViewById(R.id.bookmark_img);*/
        user_image = root.findViewById(R.id.user_image);
        /*refrence_img = root.findViewById(R.id.refrence_img);*/
        like_img = root.findViewById(R.id.like_img);
        comment_img = root.findViewById(R.id.comment_img);
        /*like_img1 = root.findViewById(R.id.like_img1);*/
        report_abuse_img = root.findViewById(R.id.report_abuse_img);
        home_post_list = root.findViewById(R.id.home_post_list);
        videoView = root.findViewById(R.id.fullscreenVideoView);

        session = new Session(getActivity());
        videos.add("http://logicaltest.in/Exim_papa/assets/video/187126814_532689178137605_6087944053270491943_n.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/187126814_532689178137605_6087944053270491943_n.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/big_buck_bunny_720p_20mb.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/187126814_532689178137605_6087944053270491943_n.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/big_buck_bunny_720p_20mb.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/187126814_532689178137605_6087944053270491943_n.mp4");
        videos.add("http://logicaltest.in/Exim_papa/assets/video/big_buck_bunny_720p_20mb.mp4");

        videoViewAdapter = new VideoViewAdapter(getActivity(), videos);
        videoview_pager.setAdapter(videoViewAdapter);


        home_post_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();


                int visiblePosition = llm.findFirstCompletelyVisibleItemPosition();


                RecyclerView.SmoothScroller smoothScroller = new
                        LinearSmoothScroller(getActivity()) {
                            @Override
                            protected int getVerticalSnapPreference() {
                                return LinearSmoothScroller.SNAP_TO_ANY;
                            }
                        };


                if (visiblePosition > -1 && !homePostModels.get(visiblePosition).getPost_video().equals("")) {

                    smoothScroller.setTargetPosition(visiblePosition);

                    home_post_list.getLayoutManager().startSmoothScroll(smoothScroller);

                    SimpleMediaSource mediaSource = new SimpleMediaSource(Post_Video_Url + homePostModels.get(visiblePosition).getPost_video());

                    Log.e(" Scroll ", "onScrolled in : " + visiblePosition);

                    ExoVideoView post_video_view = llm.findViewByPosition(visiblePosition).findViewById(R.id.videoView);

                    post_video_view.hideController();
                    post_video_view.setControllerAutoShow(false);
                    post_video_view.setUseController(false);

                    post_video_view.changeWidgetVisibility(R.id.exo_player_controller_back, View.INVISIBLE);

                    for (int i = 0; i < video_position.size(); i++) {
                        if (visiblePosition == video_position.get(i)) {
                            post_video_view.play(mediaSource);
                            post_video_view.resume();
                        } else {
                            post_video_view.stop();
                            post_video_view.pause();
                        }
                    }
                }
            }
        });
        return root;
    }


    private void getHomePostList() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url + get_user_post_home, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("result").equals("true")) {
                        progressDialog.dismiss();

                        JSONArray data = jsonObject.getJSONArray("data");

                        video_position.clear();

                        Log.e("HOme Fragment ", "onResponse: size " + data.length());
                        for (int homelist = 0; homelist < data.length(); homelist++) {

                            JSONObject dataObj = data.getJSONObject(homelist);

                            HomePostModel homePostModel = new HomePostModel();

                            homePostModel.setId(dataObj.getString("id"));
                            homePostModel.setPost_img(dataObj.getString("post_img"));
                            homePostModel.setPost_video(dataObj.getString("post_video"));
                            homePostModel.setText_message(dataObj.getString("text_message"));
                            homePostModel.setUser_img(dataObj.getString("user_img"));
                            homePostModel.setTotal_like(dataObj.getString("total_like"));
                            homePostModel.setLike_status(dataObj.getString("status"));
                            homePostModel.setName(dataObj.getString("name"));
                            homePostModel.setUser_id(dataObj.getString("user_id"));
                            homePostModel.setUsername(dataObj.getString("username"));

                            if (!homePostModel.getPost_video().equals("")) {

                                video_position.add(homelist);

                            }

                            homePostModels.add(homePostModel);
                        }
                        homePostAdapter = new HomePostAdapter(getActivity(), homePostModels);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        home_post_list.setLayoutManager(layoutManager);
                        home_post_list.setAdapter(homePostAdapter);

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();

                    Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", session.getUser_Id());

                return map;

            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }


}