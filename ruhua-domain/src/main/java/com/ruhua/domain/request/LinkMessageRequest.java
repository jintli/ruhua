package com.ruhua.domain.request;

/**
 * 链接消息
 * Created by IntelliJ IDEA.
 * User: zhangkuan
 * Date: 14-4-1
 * Time: 上午11:50
 */
public class LinkMessageRequest extends BaseMessageRequest {
    // 消息标题
    private String title;
    // 消息描述
    private String description;
    // 消息链接
    private String url;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
