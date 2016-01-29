package com.xiwa.test.util;

import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.bean.NVWAResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * NVWA HTTP
 * <p/>
 * Created by lufaxdev on 2014/11/25.
 */
public class NVWAHttp implements ResponseConstant
{

    public static String validateCode = null;

    /**
     * Invoke
     *
     * @param httpclient
     * @param httpost
     * @return
     */
    private static String invoke(HttpClient httpclient,
                                 HttpUriRequest httpost)
    {
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    /**
     * Get
     *
     * @param url
     * @return
     */
    private static String get(String url)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String response = invoke(httpClient, new HttpGet(url));
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    /**
     * Get  返回httpClient
     *  主要用于测试验证码,因为验证码是存在session里面的
     * @param url
     * @return
     */
    public static HttpClient getReturnClient(String url)
    {
        HttpClient httpClient = new DefaultHttpClient();
        String response = invoke(httpClient, new HttpGet(url));
        int start = response.indexOf("{\"ok\"");
        String tempRes = response.substring(start,response.length());
        JSONObject resJson = JSONObject.fromObject(tempRes);
        if(resJson.containsKey("dataMap")){
            validateCode = resJson.getJSONObject("dataMap").getString("validateCode");
        }
    //    httpClient.getConnectionManager().shutdown();
        return httpClient;
    }

    /**
     * POST
     *
     * @param url
     * @param params
     * @return
     */
    public static NVWAResponse post(HttpClient httpClient, String url, Map<String, String> params)
    {
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        NVWAResponse nvwaResponse = new NVWAResponse();

        String response = invoke(httpClient, httpost);
        JSONObject responseJSON = JSONObject.fromObject(response);
        if (responseJSON.containsKey(OK) && responseJSON.getBoolean(OK)) {
            //如果业务逻辑执行成功则开始判断返回结果是否正确
            if (responseJSON.containsKey(DataMap)) {
                //获取DataMap里面的内容
                JSONObject dataMap = responseJSON.getJSONObject(DataMap);
                //开始执行测试用例返回结果的判断
                nvwaResponse.setOk(responseJSON.getBoolean(OK));
                nvwaResponse.setDataMap(dataMap);
            }
        }
        else {
            System.out.println("test false:" + url);
            System.out.println(responseJSON.toString());
            nvwaResponse.setOk(responseJSON.getBoolean(OK));
        }

        return nvwaResponse;
    }

    /**
     * 登陆Producer
     */
    private static void producerAuth(HttpClient httpClient)
    {
        String url = ResponseConstant.HOST + "/nvwaSecurity/login";

        Map<String, String> postData = new HashMap<String, String>();
        postData.put("loginName", Producer_Login_Name);
        postData.put("password", Producer_Login_Password);

        NVWAResponse nvwaResponse = post(httpClient, url, postData);

        if (nvwaResponse.isOk()) {
            System.out.println("Success login in Producer System....");
        }
        else {
            System.out.println("Fail login in Producer System....");
        }
    }

    /**
     * 根据不同的认证情况发送请求
     *
     * @param url
     * @param postData
     */
    private static NVWAResponse _sendPost(String url, Map<String, String> postData,boolean producerAuth)
    {
        //发送POST请求
        DefaultHttpClient httpClient = new DefaultHttpClient();

        if (producerAuth) {
            //要先进行Produer 认证
            producerAuth(httpClient);
        }
        NVWAResponse nvwaResponse = post(httpClient, url, postData);
        httpClient.getConnectionManager().shutdown();
        return nvwaResponse;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param postData
     */
    public static NVWAResponse sendPostRequest(String url, Map<String, String> postData)
    {
        return _sendPost(url, postData, false);
    }

    /**
     * 发送经过Producer认证的请求
     *
     * @param url
     * @param postData
     */
    public static NVWAResponse sendProducerAuthPostRequest(String url, Map<String, String> postData)
    {
        return _sendPost(url, postData, true);
    }

    /**
     * 发送Get请求
     *
     * @param url
     */
    public static NVWAResponse sendGetRequest(String url)
    {
        NVWAResponse nvwaResponse = new NVWAResponse();

        //发送get请求
        String response = get(url);
        //将结果转换为JSON
        try {
            JSONObject responseJSON = JSONObject.fromObject(response);
            if (responseJSON.containsKey(OK) && responseJSON.getBoolean(OK)) {
                nvwaResponse.setOk(responseJSON.getBoolean(OK));
                //如果逻辑业务测试成功则开始判断返回结果是否正确
                if (responseJSON.containsKey(DataMap)) {
                    //获取DataMap里面内容
                    JSONObject dataMap = responseJSON.getJSONObject(DataMap);
                    //开始测试用例返回结果的判断
                    nvwaResponse.setDataMap(dataMap);
                }
            }
            else {
                System.out.println("逻辑业务测试失败");
                nvwaResponse.setOk(false);
                nvwaResponse.setBody(response);
            }
        }
        catch (JSONException e) {
            nvwaResponse.setOk(true);
            nvwaResponse.setBody(response);
        }

        return nvwaResponse;
    }


}

