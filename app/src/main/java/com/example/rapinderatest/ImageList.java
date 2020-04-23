package com.example.rapinderatest;

public class ImageList {
    String albumid,id,title,url,thumbnailurl;

    public ImageList() {
    }

    public ImageList(String albumid, String id, String title, String url, String thumbnailurl) {
        this.albumid = albumid;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailurl = thumbnailurl;
    }

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }
}
