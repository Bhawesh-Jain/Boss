package com.boss.model.Response_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("otp")
    @Expose
    private int otp;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("form_status")
        @Expose
        private String formStatus;
        @SerializedName("verify_profile")
        @Expose
        private String verifyProfile;
        @SerializedName("main_category_updated")
        @Expose
        private String mainCategoryUpdated;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormStatus() {
            return formStatus;
        }

        public void setFormStatus(String formStatus) {
            this.formStatus = formStatus;
        }

        public String getVerifyProfile() {
            return verifyProfile;
        }

        public void setVerifyProfile(String verifyProfile) {
            this.verifyProfile = verifyProfile;
        }

        public String getMainCategoryUpdated() {
            return mainCategoryUpdated;
        }

        public void setMainCategoryUpdated(String mainCategoryUpdated) {
            this.mainCategoryUpdated = mainCategoryUpdated;
        }

    }
}