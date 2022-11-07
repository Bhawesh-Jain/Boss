package com.boss.Retrofit;

import com.boss.model.Response_Models.AboutUsModel;
import com.boss.model.Response_Models.AdminNotiResModel;
import com.boss.model.Response_Models.BannerResModel;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.FaqModel;
import com.boss.model.Response_Models.ILikeReelModel;
import com.boss.model.Response_Models.MyLikeReelModel;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.model.Response_Models.LikeUnlikeResModel;
import com.boss.model.Response_Models.LoginModel;
import com.boss.model.Response_Models.MyViewsReelModel;
import com.boss.model.Response_Models.OtpResModel;
import com.boss.model.Response_Models.PopularCreatorResModel;
import com.boss.model.Response_Models.PrivacyPolModel;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.model.Response_Models.ReelCommentModel;
import com.boss.model.Response_Models.RelationUserModel;
import com.boss.model.Response_Models.ResendOtpResModel;
import com.boss.model.SocialLoginResModel;
import com.boss.model.TermsConditionModel;
import com.boss.util.BaseUrl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST(BaseUrl.login)
    Call<LoginModel> login(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(BaseUrl.logout)
    Call<CommonResModel> logout(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.delete_account)
    Call<CommonResModel> deleteAccount(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_popular_creator)
    Call<PopularCreatorResModel> getPopularCreator(@Field("user_id") String user_id);

    @POST(BaseUrl.admin_notification)
    Call<AdminNotiResModel> getAdminNotification();

    @FormUrlEncoded
    @POST(BaseUrl.verify_otp)
    Call<OtpResModel> verify_otp(@Field("user_id") String user_id,
                                 @Field("otp") String otp);


    @FormUrlEncoded
    @POST(BaseUrl.delete_reel_comment)
    Call<CommonResModel> deleteReelComment(@Field("id") String id,
                                           @Field("type") String type);


    @FormUrlEncoded
    @POST(BaseUrl.share_reel)
    Call<CommonResModel> shareReel(@Field("user_id") String user_id,
                                   @Field("reel_id") String reel_id);


    @FormUrlEncoded
    @POST(BaseUrl.like_unlike_reel_main_comment)
    Call<LikeUnlikeResModel> likeUnlikeReelMainComment(@Field("user_id") String user_id,
                                                       @Field("comment_id") String comment_id);


    @FormUrlEncoded
    @POST(BaseUrl.view_reel)
    Call<CommonResModel> viewReel(@Field("user_id") String user_id,
                                  @Field("reel_id") String reel_id);


    @FormUrlEncoded
    @POST(BaseUrl.google_login)
    Call<SocialLoginResModel> googleLogin(@Field("name") String name,
                                          @Field("email") String email,
                                          @Field("fcm_id") String fcm_id,
                                          @Field("google_id") String google_id);

    @FormUrlEncoded
    @POST(BaseUrl.reply_on_reel_comment)
    Call<CommonResModel> replyOnReelComment(@Field("user_id") String name,
                                            @Field("comment_id") String email,
                                            @Field("comment") String google_id);

    @FormUrlEncoded
    @POST(BaseUrl.facebook_login)
    Call<SocialLoginResModel> facebookLogin(@Field("name") String name,
                                            @Field("fcm_id") String fcm_id,
                                            @Field("facebook_id") String facebook_id);

    @FormUrlEncoded
    @POST(BaseUrl.resend_otp)
    Call<ResendOtpResModel> resend_otp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(BaseUrl.load_reel_comments)
    Call<ReelCommentModel> loadReelComments(@Field("reel_id") String reel_id,
                                            @Field("user_id") String to_user_id);

    @FormUrlEncoded
    @POST(BaseUrl.followUnfollow)
    Call<CommonResModel> follow_Unfollow(@Field("user_id") String user_id,
                                         @Field("to_user_id") String to_user_id);


    @FormUrlEncoded
    @POST(BaseUrl.like_unlike_reel_reply_comment)
    Call<LikeUnlikeResModel> likeUnlikeReelReplyComment(@Field("user_id") String user_id,
                                                        @Field("comment_id") String comment_id);


    @FormUrlEncoded
    @POST(BaseUrl.comment_on_reel)
    Call<CommonResModel> addComment(@Field("user_id") String user_id,
                                    @Field("reel_id") String reel_id,
                                    @Field("comment") String comment);

    @FormUrlEncoded
    @POST(BaseUrl.get_folloing_userslist)
    Call<RelationUserModel> getFollowingList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_my_view_reels)
    Call<MyViewsReelModel> getMyViewReels(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_my_like_reels)
    Call<MyLikeReelModel> getMyLikeReels(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_i_like_reel)
    Call<ILikeReelModel> getILikeReels(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_follower_userslist)
    Call<RelationUserModel> getFollowersList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_profile)
    Call<ProfileModel> getProfile(@Field("user_id") String user_id, @Field("own_id") String own_id);

    @FormUrlEncoded
    @POST(BaseUrl.like_reel)
    Call<CommonResModel> likeReel(@Field("user_id") String user_id, @Field("reel_id") String reel_id);

    @FormUrlEncoded
    @POST(BaseUrl.get_reels)
    Call<HomeReelModel> getReels(@Field("user_id") String user_id);


    @POST(BaseUrl.get_banner)
    Call<BannerResModel> getBanner();


    @POST(BaseUrl.get_about_us)
    Call<AboutUsModel> getAboutUs();


    @POST(BaseUrl.get_privacy_policy)
    Call<PrivacyPolModel> getPrivacyPolicy();


    @POST(BaseUrl.get_terms_condition)
    Call<TermsConditionModel> getTermsCondition();


    @POST(BaseUrl.get_faq)
    Call<FaqModel> getFaq();

}