package com.boss.model.Response_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("additional_mobile")
        @Expose
        private String additionalMobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("fname")
        @Expose
        private String fname;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("visiting_card_image")
        @Expose
        private String visitingCardImage;
        @SerializedName("govt_id_image")
        @Expose
        private String govtIdImage;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("verify_otp")
        @Expose
        private String verifyOtp;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;
        @SerializedName("signup_cat_id")
        @Expose
        private String signupCatId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("second_category_id")
        @Expose
        private String secondCategoryId;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("looking_for_workers")
        @Expose
        private String lookingForWorkers;
        @SerializedName("service_provided")
        @Expose
        private String serviceProvided;
        @SerializedName("social_media")
        @Expose
        private String socialMedia;
        @SerializedName("fb_link")
        @Expose
        private String fbLink;
        @SerializedName("insta_link")
        @Expose
        private String instaLink;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("form_status")
        @Expose
        private String formStatus;
        @SerializedName("verify_profile")
        @Expose
        private String verifyProfile;
        @SerializedName("main_category_updated")
        @Expose
        private String mainCategoryUpdated;
        @SerializedName("second_category_updated")
        @Expose
        private String secondCategoryUpdated;
        @SerializedName("signup_category_updated")
        @Expose
        private String signupCategoryUpdated;
        @SerializedName("mobile_hidden")
        @Expose
        private String mobileHidden;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("total_following")
        @Expose
        private int totalFollowing;
        @SerializedName("total_follower")
        @Expose
        private int totalFollower;
        @SerializedName("i_followed")
        @Expose
        private int iFollowed;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAdditionalMobile() {
            return additionalMobile;
        }

        public void setAdditionalMobile(String additionalMobile) {
            this.additionalMobile = additionalMobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVisitingCardImage() {
            return visitingCardImage;
        }

        public void setVisitingCardImage(String visitingCardImage) {
            this.visitingCardImage = visitingCardImage;
        }

        public String getGovtIdImage() {
            return govtIdImage;
        }

        public void setGovtIdImage(String govtIdImage) {
            this.govtIdImage = govtIdImage;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }

        public String getSignupCatId() {
            return signupCatId;
        }

        public void setSignupCatId(String signupCatId) {
            this.signupCatId = signupCatId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSecondCategoryId() {
            return secondCategoryId;
        }

        public void setSecondCategoryId(String secondCategoryId) {
            this.secondCategoryId = secondCategoryId;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getLookingForWorkers() {
            return lookingForWorkers;
        }

        public void setLookingForWorkers(String lookingForWorkers) {
            this.lookingForWorkers = lookingForWorkers;
        }

        public String getServiceProvided() {
            return serviceProvided;
        }

        public void setServiceProvided(String serviceProvided) {
            this.serviceProvided = serviceProvided;
        }

        public String getSocialMedia() {
            return socialMedia;
        }

        public void setSocialMedia(String socialMedia) {
            this.socialMedia = socialMedia;
        }

        public String getFbLink() {
            return fbLink;
        }

        public void setFbLink(String fbLink) {
            this.fbLink = fbLink;
        }

        public String getInstaLink() {
            return instaLink;
        }

        public void setInstaLink(String instaLink) {
            this.instaLink = instaLink;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
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

        public String getSecondCategoryUpdated() {
            return secondCategoryUpdated;
        }

        public void setSecondCategoryUpdated(String secondCategoryUpdated) {
            this.secondCategoryUpdated = secondCategoryUpdated;
        }

        public String getSignupCategoryUpdated() {
            return signupCategoryUpdated;
        }

        public void setSignupCategoryUpdated(String signupCategoryUpdated) {
            this.signupCategoryUpdated = signupCategoryUpdated;
        }

        public String getMobileHidden() {
            return mobileHidden;
        }

        public void setMobileHidden(String mobileHidden) {
            this.mobileHidden = mobileHidden;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public int getTotalFollowing() {
            return totalFollowing;
        }

        public void setTotalFollowing(int totalFollowing) {
            this.totalFollowing = totalFollowing;
        }

        public int getTotalFollower() {
            return totalFollower;
        }

        public void setTotalFollower(int totalFollower) {
            this.totalFollower = totalFollower;
        }

        public int getiFollowed() {
            return iFollowed;
        }

        public void setiFollowed(int iFollowed) {
            this.iFollowed = iFollowed;
        }

    }}