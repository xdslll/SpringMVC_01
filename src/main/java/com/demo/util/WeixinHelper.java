package com.demo.util;

import com.demo.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/25
 */
public final class WeixinHelper implements Consts {

    public static CloseableHttpClient getWeixinHttpClient() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("api.weixin.qq.com", 443),
                new UsernamePasswordCredentials(""));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
    }

    public static CloseableHttpResponse getWeixinHttpsResponse(CloseableHttpClient client, String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet);
    }

    public static CloseableHttpResponse getWeixinHttpsResponse(CloseableHttpClient client, String url, HttpEntity entity) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return client.execute(httpPost);
    }

    public static CloseableHttpResponse getWeixinHttpsResponse(CloseableHttpClient client, String url, String filePath) throws IOException {
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

    public static WeixinResponse parseMenuResponseCode(String result) {
        return FastJSONHelper.deserialize(result, WeixinResponse.class);
    }

    public static WeixinMediaResponse parseUploadMediaRequest(CloseableHttpClient client, String url, String filePath) throws IOException {
        CloseableHttpResponse response = getWeixinHttpsResponse(client, url, filePath);
        String result = parseWeixinHttpsResponse(response);
        client.close();
        response.close();
        return FastJSONHelper.deserialize(result, WeixinMediaResponse.class);
    }

    public static String parseDownloadMediaRequest(CloseableHttpClient client, String url) throws IOException {
        CloseableHttpResponse response = getWeixinHttpsResponse(client, url);
        String result = parseWeixinHttpsResponse(response);
        client.close();
        response.close();
        return result;
    }

    public static String parseWeixinHttpsResponse(CloseableHttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String result = "";
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            System.out.println("response --> " + result);
            reader.close();
            return result;
        } else {
            return null;
        }
    }

    public static WeixinIpList getIpList(WeixinAccessToken accessToken) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken.getAccess_token();
        CloseableHttpClient client = getWeixinHttpClient();
        CloseableHttpResponse response = WeixinHelper.getWeixinHttpsResponse(client, url);
        String json = WeixinHelper.parseWeixinHttpsResponse(response);
        return FastJSONHelper.deserialize(json, WeixinIpList.class);
    }

    public static WeixinAccessToken getAccessToken() throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token" +
                "?grant_type=client_credential" +
                "&appid=" + APP_ID +
                "&secret=" + APP_SECRET_KEY;
        CloseableHttpClient client = getWeixinHttpClient();
        CloseableHttpResponse response = WeixinHelper.getWeixinHttpsResponse(client, url);
        String result = WeixinHelper.parseWeixinHttpsResponse(response);
        client.close();
        response.close();
        if (result != null) {
            return FastJSONHelper.deserialize(result, WeixinAccessToken.class);
        } else {
            return null;
        }
    }

    public static WeixinMenuButtonType createMenuOne() {
        WeixinMenuButtonType menuButton = new WeixinMenuButtonType();
        menuButton.setName("解决方案");
        menuButton.setKey("SOLUTIONS");

        WeixinMenuSubButton subButton1 = new WeixinMenuSubButton();
        subButton1.setName("商业市调系统");
        subButton1.setUrl(HTTP_URL + "weixin/login");
        subButton1.setType("view");

        WeixinMenuSubButton subButton2 = new WeixinMenuSubButton();
        subButton2.setName("集成框架平台");
        subButton2.setUrl(HTTP_URL + "weixin/login");
        subButton2.setType("view");

        WeixinMenuSubButton subButton3 = new WeixinMenuSubButton();
        subButton3.setName("智能营销分析平台");
        subButton3.setUrl(HTTP_URL + "weixin/login");
        subButton3.setType("view");

        List<WeixinMenuSubButton> subButtons = new ArrayList<WeixinMenuSubButton>();
        subButtons.add(subButton1);
        subButtons.add(subButton2);
        subButtons.add(subButton3);
        //subButtons.add(subButton4);
        //subButtons.add(subButton5);
        menuButton.setSub_button(subButtons);

        return menuButton;
    }

    public static WeixinMenuButtonType createMenuTwo() {
        WeixinMenuButtonType menuButton = new WeixinMenuButtonType();
        menuButton.setName("个人中心");
        menuButton.setKey("SERVICE");

        WeixinMenuSubButton subButton1 = new WeixinMenuSubButton();
        subButton1.setName("本馆书目查询");
        //subButton1.setType("click");
        //subButton1.setKey("SERVICE_OPAC_SEARCH");
        subButton1.setType("view");
        subButton1.setUrl("http://xkfw.shou.edu.cn/wap/b.php?id=3");

        WeixinMenuSubButton subButton2 = new WeixinMenuSubButton();
        subButton2.setType("click");
        subButton2.setName("个人借阅信息");
        subButton2.setKey("SERVICE_MY_BORROW");
        //subButton2.setUrl("http://xkfw.shou.edu.cn/wap/c.php");

        WeixinMenuSubButton subButton3 = new WeixinMenuSubButton();
        subButton3.setType("click");
        //subButton3.setName("图书续借");
        subButton3.setName("图书荐购");
        subButton3.setKey("SERVICE_RENEW");
        //subButton3.setUrl("http://xkfw.shou.edu.cn/wap/c.php?page=2&id=14");

        WeixinMenuSubButton subButton4 = new WeixinMenuSubButton();
        subButton4.setType("click");
        subButton4.setName("绑定读者证");
        subButton4.setKey("SERVICE_BIND");

        WeixinMenuSubButton subButton5 = new WeixinMenuSubButton();
        subButton5.setType("click");
        subButton5.setName("解绑读者证");
        subButton5.setKey("SERVICE_UNBIND");

        List<WeixinMenuSubButton> subButtons = new ArrayList<WeixinMenuSubButton>();
        subButtons.add(subButton1);
        subButtons.add(subButton2);
        subButtons.add(subButton3);
        subButtons.add(subButton4);
        subButtons.add(subButton5);
        menuButton.setSub_button(subButtons);

        return menuButton;
    }

    public static WeixinMenuButtonType createMenuThree() {
        WeixinMenuButtonType menuButton = new WeixinMenuButtonType();
        menuButton.setName("关于我们");
        menuButton.setKey("ABOUT");

        WeixinMenuSubButton subButton1 = new WeixinMenuSubButton();
        subButton1.setType("click");
        subButton1.setName("绑定会员账号");
        subButton1.setKey("ABOUT_BIND");

        WeixinMenuSubButton subButton2 = new WeixinMenuSubButton();
        subButton2.setType("click");
        subButton2.setName("取消账号绑定");
        subButton2.setKey("ABOUT_UNBIND");

        /*WeixinMenuSubButton subButton3 = new WeixinMenuSubButton();
        subButton3.setName("电子教参");
        subButton3.setType("view");
        subButton3.setUrl("http://shfujc.superlib.com:8012/mobile/");
        //subButton3.setType("click");
        //subButton3.setKey("INTERACTION_COMMON");

        WeixinMenuSubButton subButton4 = new WeixinMenuSubButton();
        subButton4.setType("click");
        subButton4.setName("联系我们");
        subButton4.setKey("INTERACTION_CONTACT");

        WeixinMenuSubButton subButton5 = new WeixinMenuSubButton();
        subButton5.setType("click");
        subButton5.setName("加入图书馆");
        subButton5.setKey("INTERACTION_JOIN");
        //subButton5.setUrl("http://xkfw.shou.edu.cn/wap/m.php?id=8");
*/
        List<WeixinMenuSubButton> subButtons = new ArrayList<WeixinMenuSubButton>();
        subButtons.add(subButton1);
        subButtons.add(subButton2);
        //subButtons.add(subButton3);
        //subButtons.add(subButton4);
        //subButtons.add(subButton5);
        menuButton.setSub_button(subButtons);

        return menuButton;
    }

    public static WeixinMenuButtonType createTestMenu() {
        WeixinMenuButtonType menuButton = new WeixinMenuButtonType();
        menuButton.setName("测试菜单");
        menuButton.setKey("TEST");

        WeixinMenuSubButton subButton1 = new WeixinMenuSubButton();
        subButton1.setType("scancode_waitmsg");
        subButton1.setName("二维码");
        subButton1.setKey("TEST_1");

        WeixinMenuSubButton subButton2 = new WeixinMenuSubButton();
        subButton2.setType("scancode_push");
        subButton2.setName("二维码推送");
        subButton2.setKey("TEST_2");

        WeixinMenuSubButton subButton3 = new WeixinMenuSubButton();
        subButton3.setType("pic_sysphoto");
        subButton3.setName("系统拍照");
        subButton3.setKey("TEST_3");

        WeixinMenuSubButton subButton4 = new WeixinMenuSubButton();
        subButton4.setType("pic_photo_or_album");
        subButton4.setName("拍照或系统相册");
        subButton4.setKey("TEST_4");

        WeixinMenuSubButton subButton5 = new WeixinMenuSubButton();
        subButton5.setType("pic_weixin");
        subButton5.setName("微信相册发图");
        subButton5.setKey("TEST_5");

        List<WeixinMenuSubButton> subButtons = new ArrayList<WeixinMenuSubButton>();
        subButtons.add(subButton1);
        subButtons.add(subButton2);
        subButtons.add(subButton3);
        subButtons.add(subButton4);
        subButtons.add(subButton5);
        menuButton.setSub_button(subButtons);

        return menuButton;
    }

    public static WeixinMenuButtonType createLocationMenu() {
        WeixinMenuButtonType menuButton = new WeixinMenuButtonType();
        menuButton.setType("location_select");
        menuButton.setName("测试定位");
        menuButton.setKey("LOCATION");

        return menuButton;
    }

    public static WeixinMenuButton createMenuButton() {

        WeixinMenuButtonType menuButtonOne = createMenuOne();
        //WeixinMenuButtonType menuButtonTwo = createMenuTwo();
        WeixinMenuButtonType menuButtonThree = createMenuThree();

        //WeixinMenuButtonType menuTestButton = createTestMenu();
        //WeixinMenuButtonType menuLocationButton = createLocationMenu();


        List<WeixinMenuButtonType> menuTypes = new ArrayList<WeixinMenuButtonType>();
        menuTypes.add(menuButtonOne);
        //menuTypes.add(menuButtonTwo);
        menuTypes.add(menuButtonThree);
        //menuTypes.add(menuTestButton);
        //menuTypes.add(menuLocationButton);

        WeixinMenuButton menu = new WeixinMenuButton();
        menu.setButton(menuTypes);
        return menu;
    }

    public static boolean createMenu(WeixinAccessToken accessToken) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.getAccess_token();

        WeixinMenuButton menu = createMenuButton();

        String json = FastJSONHelper.serialize(menu);
        System.out.println(json);

        StringEntity entity = new StringEntity(json, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        CloseableHttpClient client = getWeixinHttpClient();
        CloseableHttpResponse orgResponse = getWeixinHttpsResponse(client, url, entity);

        String result = WeixinHelper.parseWeixinHttpsResponse(orgResponse);
        WeixinResponse responseCode = WeixinHelper.parseMenuResponseCode(result);
        client.close();
        orgResponse.close();
        if (responseCode.getErrcode() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static WeixinMenu getMenu(WeixinAccessToken accessToken) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken.getAccess_token();
        CloseableHttpClient client = getWeixinHttpClient();
        CloseableHttpResponse response = getWeixinHttpsResponse(client,url);
        String json = WeixinHelper.parseWeixinHttpsResponse(response);
        client.close();
        response.close();
        return FastJSONHelper.deserialize(json, WeixinMenu.class);
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(WeixinHelper.class);
        try {
            WeixinAccessToken accessToken = getAccessToken();
            createMenu(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("error!");
        }

    }
}
