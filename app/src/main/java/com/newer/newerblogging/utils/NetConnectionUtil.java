package com.newer.newerblogging.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.newer.newerblogging.application.NewerApplication;
import com.newer.newerblogging.bean.UserInfo;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Chalmers on 2016-09-12 11:58.
 * email:qxinhai@yeah.net
 */

/**
 * 网络连接类
 */
public class NetConnectionUtil {

    private static RequestQueue mRequestQueue = Volley.newRequestQueue(NewerApplication.getInstance());

    /**
     * 根据登录用户id获得登录用户信息
     *
     * @param context 上下文对象
     * @param userId  登录用户id
     */
    public static void netGetLocalUserInfo(final Context context, String userId) {
        String url = BlogInterfaceConfig.USERS_SHOW + "?" + BlogInterfaceConfig.ACCESS_TOKEN
                + "=" + AccessTokenKeeper.readAccessToken(context).getToken()
                + "&uid=" + userId;

        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                UserInfo localUserInfo = new Gson()
                        .fromJson(jsonObject.toString(), UserInfo.class);
                SharedPrefUtil.saveLocalUserInfo(context, localUserInfo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }));
    }

    /**
     * 通过网络获取微博接口数据,该方法可任意调用
     *
     * @param url         网址
     * @param netCallback 回调方法
     */
    public static void netFromGetWeiboData(String url, final NetCallback netCallback) {
        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(volleyError.getMessage());
            }
        }));
    }

    /**
     * 获得微博数据
     *
     * @param context     上下文对象
     * @param id_command  指令，包括两个，Since_id表示获得最新的微博，Max_id表示获得之前的微博
     * @param id          微博的id
     * @param netCallback 回调方法
     */
    public static void netToGetWeiboData(Context context, String id_command, String id, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.STATUES_FRIENDS_TIMELINE,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                id_command, id);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 取消点赞
     *
     * @param context     上下文对象
     * @param idStr       微博的id
     * @param netCallback 回调方法
     */
    public static void netToDestroyAttitudes(Context context, String idStr, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.ATTITUDES_DESTROY,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                BlogInterfaceConfig.ID, idStr);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 给某条微博点赞
     *
     * @param context     上下文对象
     * @param idStr       weibo的id值
     * @param netCallback 回到方法
     */
    public static void netToCreateAttitudes(Context context, String idStr, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.ATTITUDES_CREATE,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                BlogInterfaceConfig.ID, idStr);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LoggerUtil.i("ATTITUDES", volleyError.toString());
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 转发一条微博
     *
     * @param id         转发的微博的id
     * @param status     要转发的文本，如果不填，默认为“转发微博”
     * @param is_comment 是否评论 0：否 1：评论给当前微博 2：评论给原微博 3：都评论
     */
    public static void netToRepostStatus(Context context, String id, String status,
                                         int is_comment, final NetCallback netCallback) {
        String url = null;
        try {
            url = String.format("%s?%s=%s&id=%s&status=%s&is_comment=%s",
                    BlogInterfaceConfig.STATUSES_REPOST,
                    BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                    id,
                    //编码
                    URLEncoder.encode(status, "UTF-8"),
                    is_comment);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 收藏一条微博
     *
     * @param context     上下文对象
     * @param idStr       微博的id
     * @param netCallback 回调方法
     */
    public static void netToCreateFavorites(Context context, String idStr, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.FAVORITES_CREATE,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                BlogInterfaceConfig.ID, idStr);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 取消收藏
     *
     * @param context     上下文对象
     * @param idStr       微博 的值
     * @param netCallback 回调方法
     */
    public static void netToDestroyFavorites(Context context, String idStr, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.FAVORITES_DESTROY,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                BlogInterfaceConfig.ID, idStr);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 对某条微博发表评论
     *
     * @param context     上下文对象
     * @param id          微博的id
     * @param comment     评论内容
     * @param comment_ori 是否评论给原微博 1.是 0.否 默认是0
     * @param netCallback 回调方法
     */
    public static void netToCreateComments(Context context, String id, String comment,
                                           int comment_ori, final NetCallback netCallback) {
        String url = null;
        try {
            url = String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s",
                    BlogInterfaceConfig.COMMENTS_CREATE,
                    BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                    "id", id,
                    "comment", URLEncoder.encode(comment, "UTF-8"),
                    "comment_ori", comment_ori);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mRequestQueue.add(new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(Config.SUCCESS);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));

    }

    /**
     * 获取某条微博下的评论
     *
     * @param context          上下文对象
     * @param idStr            微博的id值
     * @param id_comment       评论的id值
     * @param count            每次获取的评论数量
     * @param page             页数
     * @param filter_by_author 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @param netCallback      回调方法
     */
    public static void netToShowComments(Context context, String idStr, String id_comment,
                                         int count, int page, int filter_by_author, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                BlogInterfaceConfig.COMMENTS_SHOW,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                BlogInterfaceConfig.ID, idStr,
                BlogInterfaceConfig.MAX_ID, id_comment,
                "count", count,
                "page", page,
                "filter_by_author", filter_by_author
        );
        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        netCallback.doSuccess(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 获得公共微博数据
     *
     * @param context     上下文对象
     * @param id_command  指令，包括两个，Since_id表示获得最新的微博，Max_id表示获得之前的微博
     * @param id          微博的id
     * @param netCallback 回调方法
     */
    public static void netToPublicTimelineStatues(Context context, String id_command, String id, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s",
                BlogInterfaceConfig.STATUES_PUBLIC_TIMELINE,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                id_command, id);
        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                netCallback.doSuccess(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    /**
     * 获取话题排行榜的数据
     *
     * @param context     上下文对象
     * @param command     指令，具体获得那一部分的
     * @param netCallback 回调方法
     */
    public static void netToTrendsSort(Context context, String command, final NetCallback netCallback) {
        String u = null;
        switch (command) {
            case Config.HOURLY:
                u = BlogInterfaceConfig.TRENDS_HOURLY;
                break;
            case Config.DAILY:
                u = BlogInterfaceConfig.TRENDS_DAILY;
                break;
            case Config.WEEKLY:
                u = BlogInterfaceConfig.TRENDS_WEEKLY;
                break;
        }

        String url = String.format("%s?%s=%s",
                u,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken());

        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        netCallback.doSuccess(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LoggerUtil.i("TRENDS", "error");
            }
        }));
    }

    /**
     * 获得某个话题下的微博
     *
     * @param context     上下文对象
     * @param trend       话题
     * @param count       每次获得微博数量
     * @param page        页数
     * @param netCallback 回调方法
     */
    public static void netToTrendsStatuses(Context context, String trend, int count,
                                           int page, final NetCallback netCallback) {
        String url = String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s",
                BlogInterfaceConfig.TRENDS_STATUSES,
                BlogInterfaceConfig.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(context).getToken(),
                "q", trend,
                "count", count,
                "page", page
        );
        mRequestQueue.add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        netCallback.doSuccess(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netCallback.doFail(Config.FAIL);
            }
        }));
    }

    public interface NetCallback {
        void doSuccess(String data);

        void doFail(String message);
    }
}