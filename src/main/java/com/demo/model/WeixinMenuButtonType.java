package com.demo.model;

import java.util.List;

/**
 * @author xiads
 * @date 11/14/14
 */
public class WeixinMenuButtonType {

    private String type;

    private String name;

    private String key;

    private String url;

    private List<WeixinMenuSubButton> sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<WeixinMenuSubButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WeixinMenuSubButton> sub_button) {
        this.sub_button = sub_button;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
