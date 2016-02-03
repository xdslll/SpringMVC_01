package com.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author xiads
 * @date 11/14/14
 */
public class HttpHelper {

    private static CloseableHttpResponse getHttpResponse(CloseableHttpClient client, String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet);
    }

    private static CloseableHttpResponse getHttpResponse(CloseableHttpClient client, String url, List<NameValuePair> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        return client.execute(httpPost);
    }

    private static CloseableHttpResponse getHttpResponse(CloseableHttpClient client, String url, HttpEntity entity) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return client.execute(httpPost);
    }

    private static CloseableHttpResponse getHttpResponse(CloseableHttpClient client, String url, String filePath) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        FileBody bin = new FileBody(new File(filePath));
        StringBody comment = new StringBody("a binary file", ContentType.MULTIPART_FORM_DATA);
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("bin", bin)
                .addPart("comment", comment)
                .build();
        httpPost.setEntity(reqEntity);
        return client.execute(httpPost);
    }

    private static String parseHttpResponse(CloseableHttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String result = "";
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        } else {
            return null;
        }
    }

    public static String get(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = getHttpResponse(client, url);
        String result = parseHttpResponse(response);
        client.close();
        response.close();
        return result;
    }
}
