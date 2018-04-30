package com.yourscab.mobile.prototype;


public class TouristItemPrototype {
    public String image_url,title,node,parent_node;

    public TouristItemPrototype(){}

    public TouristItemPrototype(String image_url, String title, String node) {
        this.image_url = image_url;
        this.title = title;
        this.node = node;
        }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getParent_node() {
        return parent_node;
    }

    public void setParent_node(String parent_node) {
        this.parent_node = parent_node;
    }
}
