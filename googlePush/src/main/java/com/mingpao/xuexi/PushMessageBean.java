package com.mingpao.xuexi;

import com.google.gson.annotations.SerializedName;

public class PushMessageBean {

    /**
     * to : f4EU4zBGJro:APA91bEDNfGFmUB62N9hcM5yKWCBI6FnQe5AL-7jR5U3NepZlDZQJCM0gWGkwgjMjHi5MWOjQz-dZZBVhzvOO51TzH6GxXfujraiNqt_9xZaq6FPC6gYIx3VRe4GaGQBYV_ow9bw7lNc
     * notification : {"body":"推送的内容  推送的内容 推送的内容","title":"推送的标题","color":"#ffcc0000","sound":"2","click_action":"com.mingpao.xuexi.push","icon":"jl","image":"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3880341262,3308316348&fm=26&gp=0.jpg"}
     * data : {"url":"http://mcildigital.com/internalapps/p/testMessage.html","image":"https://img2.utuku.china.com/554x0/news/20191220/c7149ce5-77b8-424b-9f91-f6c7fecca3f5.jpg"}
     */

    @SerializedName("to")
    private String to;
    @SerializedName("notification")
    private NotificationBean notification;
    @SerializedName("data")
    private DataBean data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class NotificationBean {
        /**
         * body : 推送的内容  推送的内容 推送的内容
         * title : 推送的标题
         * color : #ffcc0000
         * sound : 2
         * click_action : com.mingpao.xuexi.push
         * icon : jl
         * image : https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3880341262,3308316348&fm=26&gp=0.jpg
         */

        @SerializedName("body")
        private String body;
        @SerializedName("title")
        private String title;
        @SerializedName("color")
        private String color;
        @SerializedName("sound")
        private String sound;
        @SerializedName("click_action")
        private String clickAction;
        @SerializedName("icon")
        private String icon;
        @SerializedName("image")
        private String image;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getClickAction() {
            return clickAction;
        }

        public void setClickAction(String clickAction) {
            this.clickAction = clickAction;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class DataBean {
        /**
         * url : http://mcildigital.com/internalapps/p/testMessage.html
         * image : https://img2.utuku.china.com/554x0/news/20191220/c7149ce5-77b8-424b-9f91-f6c7fecca3f5.jpg
         */

        @SerializedName("url")
        private String url;
        @SerializedName("image")
        private String image;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
