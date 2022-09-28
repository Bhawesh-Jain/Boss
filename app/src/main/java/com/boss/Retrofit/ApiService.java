package com.boss.Retrofit;

import com.boss.model.Response_Models.AboutUsModel;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.FaqModel;
import com.boss.model.Response_Models.LoginModel;
import com.boss.model.Response_Models.OtpResModel;
import com.boss.model.Response_Models.PrivacyPolModel;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.model.Response_Models.ResendOtpResModel;
import com.boss.model.TermsConditionModel;
import com.boss.util.BaseUrl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST(BaseUrl.login)
    Call<LoginModel> login(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(BaseUrl.logout)
    Call<CommonResModel> logout(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(BaseUrl.verify_otp)
    Call<OtpResModel> verify_otp(@Field("user_id") String user_id,
                                 @Field("otp") String otp);

    @FormUrlEncoded
    @POST(BaseUrl.resend_otp)
    Call<ResendOtpResModel> resend_otp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(BaseUrl.get_profile)
    Call<ProfileModel> getProfile(@Field("user_id") String user_id);


    @POST(BaseUrl.get_about_us)
    Call<AboutUsModel> getAboutUs();


    @POST(BaseUrl.get_privacy_policy)
    Call<PrivacyPolModel> getPrivacyPolicy();


    @POST(BaseUrl.get_terms_condition)
    Call<TermsConditionModel> getTermsCondition();


    @POST(BaseUrl.get_faq)
    Call<FaqModel> getFaq();

}