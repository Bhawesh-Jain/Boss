package com.boss.model.Response_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReelCommentModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("user_path")
    @Expose
    private String userPath;
    @SerializedName("data_1")
    @Expose
    private UserData userData;
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

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("reel_id")
        @Expose
        private String reelId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private String userImage;

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

        public String getReelId() {
            return reelId;
        }

        public void setReelId(String reelId) {
            this.reelId = reelId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

    }
    public class UserData {

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
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private String userImage;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

    }

}