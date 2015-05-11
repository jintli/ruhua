package com.ruhua.domain.request;

/**
 * 音频消息
 * Created by IntelliJ IDEA.
 * User: zhangkuan
 * Date: 14-4-1
 * Time: 上午11:51
 */
public class VoiceMessageRequest extends BaseMessageRequest {
    // 媒体ID
    private String mediaId;
    // 语音格式
    private String format;

    public String getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
