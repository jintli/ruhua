package com.ruhua.domain.request;

/**
 * 文本消息
 * Created by IntelliJ IDEA.
 * User: zhangkuan
 * Date: 14-4-1
 * Time: 上午11:40
 */
public class TextMessageRequest extends BaseMessageRequest {
    // 消息内容
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
