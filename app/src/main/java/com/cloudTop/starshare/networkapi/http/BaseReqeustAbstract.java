package com.cloudTop.starshare.networkapi.http;


import com.alibaba.fastjson.JSON;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.listener.OnErrorListener;
import com.cloudTop.starshare.listener.OnSuccessListener;
import com.cloudTop.starshare.networkapi.NetworkAPIException;
import com.cloudTop.starshare.utils.NetWorkUtil;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public abstract class BaseReqeustAbstract {
    /**
     * 检测config 是否初始化
     *
     * @param errorListener 监听
     * @return
     */
    protected boolean checkInitConfig(OnErrorListener errorListener) {
        if (NetworkHttpAPIFactoryImpl.getInstance().getConfig() == null) {
            if (errorListener != null)
                errorListener.onError(new NetworkAPIException(NetworkAPIException.INITCONFIG_ERROR, "not init config"));
            return false;
        }
        return true;
    }

    /**
     * 检测网络
     *
     * @param errorListener
     * @return
     */
    protected boolean checkNetwork(OnErrorListener errorListener) {
        boolean isNetwork = NetWorkUtil.isNetworkConnected(NetworkHttpAPIFactoryImpl.getInstance().getConfig().getContext());
        if (!isNetwork && errorListener != null)
            errorListener.onError(new NetworkAPIException(NetworkAPIException.NOTNETWORK_ERROR, "Network is not available!"));
        return isNetwork;
    }

    /**
     * 填加token参数
     *
     * @param map         参数map
     * @param isMustToken 是否必须要token true:时如果token为空时回调返回异常
     * @param listener    监听
     */
    protected boolean addMapToken(HashMap<String, Object> map, boolean isMustToken, OnErrorListener listener) {
        if (map != null) {
            if (checkInitConfig(listener)) {
                String token = NetworkHttpAPIFactoryImpl.getInstance().getConfig().getUserToken();
                if (token != null && token.trim().length() > 0) {
                    map.put("token", token);
                    return true;
                } else if (isMustToken) {
                    if (listener != null) {
                        listener.onError(new NetworkAPIException(NetworkAPIException.TOKEN_ERROR, "not set token"));
                    }
                }
            }

        }
        return isMustToken == false;
    }


    protected abstract void postRequest(String url,
                                        HashMap<String, Object> map,
                                        OnSuccessListener<?> onSuccessListener,
                                        OnErrorListener onErrorListener);

    /**
     * 请求返回实体
     *
     * @param url
     * @param map
     * @param cls
     * @param listener
     */
    public void postRequestEntity(String url,
                                  HashMap<String, Object> map,
                                  final Class<?> cls,
                                  final OnAPIListener listener) {
        postRequest(url, map, new OnSuccessListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                if (listener != null) {
                    listener.onSuccess(JSON.parseObject(jsonObject.toString(),cls));
                }
            }
        }, listener);
    }

    /**
     * 请求返回list 实体
     *
     * @param url
     * @param map
     * @param cls
     * @param listener
     */
    public void postRequestEntitys(String url,
                                   HashMap<String, Object> map,
                                   final Class<?> cls,
                                   final OnAPIListener listener) {
        postRequest(url, map, new OnSuccessListener<JSONArray>() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (listener != null) {
                    listener.onSuccess(JSON.parseArray(jsonArray.toString(),cls));
                }
            }
        }, listener);
    }

    /**
     * 请求返回 boolen
     *
     * @param url
     * @param map
     * @param listener
     */
    public void postRequestBoolean(String url,
                                   HashMap<String, Object> map,
                                   final OnAPIListener<Boolean> listener) {
        postRequest(url, map, new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                if (listener != null) {
                    listener.onSuccess(true);
                }
            }
        }, listener);
    }


    protected void onJSONObjectSuccess(JSONObject jsonObject, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
        try {
            int status = jsonObject.getInt("status");
            if (status == 1) {//成功
                if (onSuccessListener != null) {
                    Class cls = getClass(onSuccessListener.getClass());
                    if (cls.isAssignableFrom(JSONObject.class)) {
                        onSuccessListener.onSuccess(jsonObject.getJSONObject("data"));
                    } else if (cls.isAssignableFrom(JSONArray.class)) {
                        onSuccessListener.onSuccess(jsonObject.getJSONArray("data"));
                    } else if (cls.isAssignableFrom(Object.class)) {
                        onSuccessListener.onSuccess(jsonObject.get("data"));
                    }
                }
            } else {
                switch (status) {
                    case 0://提示用用户
                        status = NetworkAPIException.HINT_ERROR;
                        break;
                    case -2://token 失效
                        status = NetworkAPIException.TOKEN_ERROR;
                        //UserManager.getInstance().logout();
                        break;
                    case -1220:
                        status = NetworkAPIException.CREATE_UNION_ERROR;
                        break;
                    default: //其它错误
                        if (status != -100) {
                            status = NetworkAPIException.SYSTEM_ERROR;
                        }
                }
                onError(status, jsonObject.getString("failed"), onErrorListener);
            }
        } catch (JSONException e) {
            onError(NetworkAPIException.JSON_ERROR, e, onErrorListener);
        }
    }


    protected void onError(int errorCode, String errorStr, OnErrorListener onErrorListener) {
        if (onErrorListener != null) {
            onErrorListener.onError(new NetworkAPIException(errorCode, errorStr));
        }
    }

    protected void onError(int errorCode, Throwable throwable, OnErrorListener onErrorListener) {
        if (onErrorListener != null) {
            onErrorListener.onError(new NetworkAPIException(errorCode, throwable));
        }
    }

    protected void onError(Throwable throwable, OnErrorListener onErrorListener) {

        int errorCode = NetworkAPIException.SYSTEM_ERROR;
        if (throwable instanceof HttpHostConnectException
                || throwable instanceof SocketTimeoutException
                || throwable instanceof IOException) {
            errorCode = NetworkAPIException.NETWORK_ERROR;
        }
        onError(errorCode, throwable, onErrorListener);
    }

    protected Class getActualTypeArguments(Type type, int index) {
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (index < types.length) {
                return (Class) types[index];
            }
        }
        return null;
    }

    protected Class getClass(Class cls) {

        Type[] types = cls.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                return getActualTypeArguments(type, 0);
            }
        }
        return null;
    }


}
