package com.demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
public class EnfordSystemMenu {

    private int id;

    private String text;

    private String state;

    private List<EnfordSystemMenu> children = new ArrayList<EnfordSystemMenu>();

    private String url;

    int parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<EnfordSystemMenu> getChildren() {
        return children;
    }

    public void setChildren(List<EnfordSystemMenu> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                ", children=" + children +
                ", url='" + url + '\'' +
                ", parent=" + parent +
                '}';
    }
}
