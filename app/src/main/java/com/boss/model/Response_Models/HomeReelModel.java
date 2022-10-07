package com.boss.model.Response_Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeReelModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("reel_path")
    @Expose
    private String reelPath;
    @SerializedName("user_path")
    @Expose
    private String userPath;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public String getReelPath() {
        return reelPath;
    }

    public void setReelPath(String reelPath) {
        this.reelPath = reelPath;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        private String reelPath;
        private String userPath;

        public String getReelPath() {
            return reelPath;
        }

        public void setReelPath(String reelPath) {
            this.reelPath = reelPath;
        }

        public String getUserPath() {
            return userPath;
        }

        public void setUserPath(String userPath) {
            this.userPath = userPath;
        }

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("file")
        @Expose
        private String file;
        @SerializedName("image_2")
        @Expose
        private String image2;
        @SerializedName("image_3")
        @Expose
        private String image3;
        @SerializedName("image_4")
        @Expose
        private String image4;
        @SerializedName("image_5")
        @Expose
        private String image5;
        @SerializedName("image_6")
        @Expose
        private String image6;
        @SerializedName("image_7")
        @Expose
        private String image7;
        @SerializedName("image_8")
        @Expose
        private String image8;
        @SerializedName("image_9")
        @Expose
        private String image9;
        @SerializedName("image_10")
        @Expose
        private String image10;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image_text")
        @Expose
        private String imageText;
        @SerializedName("textcolor")
        @Expose
        private String textcolor;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("like_count")
        @Expose
        private int likeCount;
        @SerializedName("comment_count")
        @Expose
        private int commentCount;
        @SerializedName("i_liked")
        @Expose
        private int iLiked;
        @SerializedName("total_views")
        @Expose
        private int total_views;

        public int getTotal_views() {
            return total_views;
        }

        public void setTotal_views(int total_views) {
            this.total_views = total_views;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getImage4() {
            return image4;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
        }

        public String getImage5() {
            return image5;
        }

        public void setImage5(String image5) {
            this.image5 = image5;
        }

        public String getImage6() {
            return image6;
        }

        public void setImage6(String image6) {
            this.image6 = image6;
        }

        public String getImage7() {
            return image7;
        }

        public void setImage7(String image7) {
            this.image7 = image7;
        }

        public String getImage8() {
            return image8;
        }

        public void setImage8(String image8) {
            this.image8 = image8;
        }

        public String getImage9() {
            return image9;
        }

        public void setImage9(String image9) {
            this.image9 = image9;
        }

        public String getImage10() {
            return image10;
        }

        public void setImage10(String image10) {
            this.image10 = image10;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageText() {
            return imageText;
        }

        public void setImageText(String imageText) {
            this.imageText = imageText;
        }

        public String getTextcolor() {
            return textcolor;
        }

        public void setTextcolor(String textcolor) {
            this.textcolor = textcolor;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getiLiked() {
            return iLiked;
        }

        public void setiLiked(int iLiked) {
            this.iLiked = iLiked;
        }

    }
}
