package com.qs.mediainfoapp.api;

public class ApiConfig {
//    public static final String BASE_URL = "http://192.168.56.1:8080/renren-fast";
    public static final String BASE_URL = "http://47.112.180.188:8080/renren-fast";//失效则设置VideoFragment的VIDEO_CATEGORY_LIST为VIDEO_LIST
    public static final String LOGIN = "/app/login";
    public static final String Register = "/app/register";
    public static final String VIDEO_LIST = "/app/videolist/list";//所有视频列表
    public static final int PAGE_SIZE = 5;
    public static final String VIDEO_LIST_BY_CATEGORY = "/app/videolist/getListByCategoryId";//各类型视频列表
    public static final String VIDEO_CATEGORY_LIST = "/app/videocategory/list";//视频类型列表
    public static final String NEWS_LIST = "/app/news/api/list";//资讯列表
    public static final String VIDEO_UPDATE_COUNT = "/app/videolist/updateCount";//更新点赞,收藏,评论
    public static final String VIDEO_MYCOLLECT = "/app/videolist/mycollect";//我的收藏
}
