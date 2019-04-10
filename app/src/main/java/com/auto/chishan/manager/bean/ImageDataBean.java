package com.auto.chishan.manager.bean;

/**
 * Date: 2019/3/4
 * desc:
 *
 * @author:DingZhixiang
 */
public class ImageDataBean {
    private String mediaType;
    private String mediaIsComplete;
    private String mediaName;
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaIsComplete() {
        return mediaIsComplete;
    }

    public void setMediaIsComplete(String mediaIsComplete) {
        this.mediaIsComplete = mediaIsComplete;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }
}
