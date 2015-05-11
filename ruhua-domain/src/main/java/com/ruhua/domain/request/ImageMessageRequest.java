package com.ruhua.domain.request;

/**
 * 图片消息
 * Created by IntelliJ IDEA.
 * User: zhangkuan
 * Date: 14-4-1
 * Time: 上午11:42
 */
public class ImageMessageRequest extends BaseMessageRequest {
    // 图片链接
    private String picUrl;

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
