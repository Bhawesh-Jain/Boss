package com.boss.adapter;


import static com.boss.util.BaseUrl.Base_Url;
import static com.boss.util.BaseUrl.Post_Video_Url;
import static com.boss.util.BaseUrl.Post_image_Url;
import static com.boss.util.BaseUrl.References_unRef;
import static com.boss.util.BaseUrl.User_image_Url;
import static com.boss.util.BaseUrl.count_like;
import static com.boss.util.BaseUrl.like_unlike_ios;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.boss.R;
import com.boss.model.HomePostModel;
import com.boss.util.Session;
import com.boss.util.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.makeramen.roundedimageview.RoundedImageView;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.MyViewHolder> {


    Context context;
    Session session;
    ArrayList<HomePostModel> homePostModels;

    public HomePostAdapter(Context context, ArrayList<HomePostModel> homePostModels) {
        this.context = context;
        this.homePostModels = homePostModels;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.home_post_layout, parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

//        Log.e("HOME ADAPTER ", "onBindViewHolder: "+position );
        session = new Session(context);


        if (homePostModels.size() > 0) {


            HomePostModel homePostModel = homePostModels.get(position);

            holder.user_name.setText(homePostModel.getUsername());
            holder.total_likes.setText(homePostModel.getTotal_like());
//            homePostModel.getTotal_refer()


            if (homePostModel.getTotal_refer() != null && !homePostModel.getTotal_refer().isEmpty() && !homePostModel.getTotal_refer().equals("null") && !homePostModel.getTotal_refer().equals("0")) {
                holder.total_refers.setText(homePostModel.getTotal_refer());
            }
            else {
                holder.total_refers.setText("0");
            }
            holder.user_position.setText(homePostModel.getUser_position());


            Glide.with(context).load(User_image_Url + homePostModel.getUser_img()).into(holder.user_image);




           /* holder.user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, VisitSingleProfileActivity.class);
                    intent.putExtra("user_id",homePostModel.getUser_id());

                    context.startActivity(intent);

                }
            });*/
            if (homePostModel.getStatus().equals("1")) {
                holder.like_img.setImageResource(R.drawable.heart_blue);

            } else if (homePostModel.getStatus().equals("0")) {

                holder.like_img.setImageResource(R.drawable.empty_heart);
            }

            if (homePostModel.getPost_img().equals("") && homePostModel.getPost_video().equals("") && homePostModel.getText_message().equals("")) {


            } else if (homePostModel.getPost_img().equals("") && homePostModel.getPost_video().equals("") && !homePostModel.getText_message().equals("")) {
                //only text
                holder.only_text_message.setVisibility(View.VISIBLE);
                holder.only_text_message.setText(homePostModel.getText_message());
                holder.user_text_message.setVisibility(View.GONE);
                holder.more.setVisibility(View.GONE);
                holder.post_image.setVisibility(View.GONE);


                holder.card_video.setVisibility(View.GONE);

            } else if (!homePostModel.getPost_video().equals("") && !homePostModel.getText_message().equals("") && homePostModel.getPost_img().equals("")) {

                // video and text

//                Log.e(" Adapter", "onBindViewHolder:  Video text " );

                holder.card_video.setVisibility(View.VISIBLE);
                holder.user_text_message.setVisibility(View.VISIBLE);
                holder.more.setVisibility(View.VISIBLE);
                holder.only_text_message.setVisibility(View.GONE);
                holder.user_text_message.setText(homePostModel.getText_message());
                holder.post_image.setVisibility(View.GONE);

                SimpleMediaSource mediaSource = new SimpleMediaSource(Post_Video_Url + homePostModel.getPost_video());

                holder.post_video_view.hideController();
                holder.post_video_view.setControllerAutoShow(false);
                holder.post_video_view.setUseController(false);
                holder.post_video_view.changeWidgetVisibility(R.id.exo_player_controller_back, View.INVISIBLE);

                holder.post_video_view.play(mediaSource);


//                holder.post_video_view.play(mediaSource,true,position);


//                Glide.with(context).load(Post_image_Url+homePostModel.getPost_video()).into(holder.post_image);

            } else if (!homePostModel.getPost_img().equals("") && !homePostModel.getText_message().equals("") && homePostModel.getPost_video().equals("")) {
                // image and text

                holder.post_image.setVisibility(View.VISIBLE);
                holder.card_video.setVisibility(View.GONE);

                holder.user_text_message.setVisibility(View.VISIBLE);
                holder.more.setVisibility(View.VISIBLE);

                holder.only_text_message.setVisibility(View.GONE);
                holder.user_text_message.setText(homePostModel.getText_message());

                Glide.with(context).load(Post_image_Url + homePostModel.getPost_img()).into(holder.post_image);

            } else if (!homePostModel.getPost_img().equals("") && homePostModel.getText_message().equals("")) {

                //only image

                holder.post_image.setVisibility(View.VISIBLE);
                holder.card_video.setVisibility(View.GONE);
                holder.user_text_message.setVisibility(View.GONE);
                holder.only_text_message.setVisibility(View.GONE);
                holder.more.setVisibility(View.GONE);
                Glide.with(context).load(Post_image_Url + homePostModel.getPost_img()).into(holder.post_image);


            } else if (!homePostModel.getPost_video().equals("") && homePostModel.getText_message().equals("")) {
                //only video


//                Log.e(" Adapter", "onBindViewHolder: Only Vide" );
                holder.card_video.setVisibility(View.VISIBLE);

                holder.post_image.setVisibility(View.GONE);

                holder.user_text_message.setVisibility(View.GONE);
                holder.more.setVisibility(View.GONE);
                holder.only_text_message.setVisibility(View.GONE);

                holder.user_text_message.setText(homePostModel.getText_message());


                SimpleMediaSource mediaSource = new SimpleMediaSource(Post_Video_Url + homePostModel.getPost_video());

                holder.post_video_view.hideController();
                holder.post_video_view.setControllerAutoShow(false);
                holder.post_video_view.setUseController(false);
                holder.post_video_view.changeWidgetVisibility(R.id.exo_player_controller_back, View.INVISIBLE);

                holder.post_video_view.play(mediaSource);


//                Glide.with(context).load(Post_image_Url+homePostModel.getPost_video()).into(holder.post_image);

            }
/*
            holder.comment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PostCommentActivity.class);
                    intent.putExtra("post_id", homePostModel.getId());
                    intent.putExtra("user_id", homePostModel.getUser_id());


                    context.startActivity(intent);

                }
            });*/


            holder.like_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (homePostModels.get(position).getStatus().equals("0")) {
                        Toast.makeText(context, "" + homePostModel.getStatus(), Toast.LENGTH_SHORT).show();
                        postLikeDislike(holder.like_img, homePostModel.getId(), "1", holder.total_likes);

                    } else {
                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();

                        postLikeDislike(holder.like_img, homePostModel.getId(), "0", holder.total_likes);
                    }

                }
            });

            holder.refrence_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Reference Add")
                            .setMessage("I do refer his/her business")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            postRefer(session.getUser_Id(), homePostModel.getUser_id(),holder.refrence_img);


                        }
                    }).show();



                }
            });

        }


    }

    private void postRefer(String my_id, String user_id,ImageView referImg) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url + References_unRef, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("result").equals("true")){

                        progressDialog.dismiss();
                        String status = jsonObject.getString("status");

                        if(status.equals("1")){
                            referImg.setImageResource(R.drawable.heart_blue);
                        }else {
                            referImg.setImageResource(R.drawable.heart_blue);
                        }
                    }else {
                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                    Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(context, "" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("user_id", my_id);
                map.put("to_user_id", user_id);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

 /*   private void playVideo(int position, ExoVideoView post_video_view, SimpleMediaSource mediaSource) {

        for (int i = 0; i < video_position.size(); i++) {

            if (video_position.get(i) == position) {

                post_video_view.play(mediaSource);
            } else {

                post_video_view.releasePlayer();

            }
        }

    }*/


    @Override
    public int getItemCount() {
        return homePostModels.size();
    }

    private void postLikeDislike(ImageView heart_img, String post_id, String status, TextView total_like) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url + like_unlike_ios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("result").equals("true")) {
                        String status = jsonObject.getString("status");


                        if (status.equals("1")) {
                            postLikeCount(total_like, post_id);
                            heart_img.setImageResource(R.drawable.heart_blue);

                        } else if (status.equals("0")) {
                            postLikeCount(total_like, post_id);
                            heart_img.setImageResource(R.drawable.empty_heart);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("user_id", session.getUser_Id());
                map.put("post_id", post_id);
                map.put("status", status);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void postLikeCount(TextView total_like, String post_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url + count_like, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Post ", "onResponse: " + response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("result").equals("true")) {

                        total_like.setText(jsonObject.getString("total_like"));
                    } else {

                        total_like.setText(jsonObject.getString("total_like"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("user_id", session.getUser_Id());
                map.put("post_id", post_id);

                return map;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView post_image;
        ImageView report_abuse_img, like_img, comment_img, refrence_img;
        CircleImageView user_image;
        TextView user_name, user_position, user_text_message, total_likes, only_text_message,
                total_refers, more, post_date_time;
        ExoVideoView post_video_view;
        CardView card_video;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            user_position = itemView.findViewById(R.id.user_position);
            user_text_message = itemView.findViewById(R.id.user_text_message);
            total_likes = itemView.findViewById(R.id.total_likes);
            only_text_message = itemView.findViewById(R.id.only_text_message1);
            total_refers = itemView.findViewById(R.id.total_refers);
            more = itemView.findViewById(R.id.more);
            post_date_time = itemView.findViewById(R.id.post_date_time);
            post_image = itemView.findViewById(R.id.post_image);
            report_abuse_img = itemView.findViewById(R.id.report_abuse_img);
            like_img = itemView.findViewById(R.id.like_img);
            comment_img = itemView.findViewById(R.id.comment_img);
            refrence_img = itemView.findViewById(R.id.refrence_img);
            user_image = itemView.findViewById(R.id.user_image);
            card_video = itemView.findViewById(R.id.card_video);
            post_video_view = itemView.findViewById(R.id.videoView);

        }
    }
}
