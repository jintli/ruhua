package com.ruhua.domain.constants;

/**
 * 业务结果常量类，定义业务响应结果代码常量
 * User: wuqingming
 * Date: 14-1-7
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public enum ServiceResponseConstants {
    /**
     * 基本错误
     */

    /** 系统错误 */
    SYSTEM_ERROR("-3", "系统错误"),
    /** 处理警告，主要针对允许成功失败同时存在的处理（如批处理）*/
    WARNING("-2", "警告"),
    /** 处理失败 */
    FAILURE("-1", "失败"),
    /** 处理成功 */
    SUCCESS("0", "成功"),
    /** 参数错误 */
    PARAM_ERROR("1", "参数错误"),
    /** 方法不存在 */
    NO_SUCH_METHOD("2", "方法不存在"),
    /** 用户未登录 */
    USER_NOT_LOGIN("3", "用户未登录"),

    NON_INVOKE_SOURCE("101","不存在的调用方"),
    INVOKE_SECRET_VAL_FAIL("102","调用秘钥校验失败"),
    INVOKE_FAIL("103","调用失败"),

    /** 用户信息**/
    USER_INFO_LOSS("401","用户信息不全"),

    LIKE_ME_USER_INFO_NULL("402","暂时没有搭我的用户"),
    CAN_DATE_USER_INFO_NULL("403","暂时没有搭我的用户"),


    IMG_UPLOAD_FAIL("1002", "上传商品图片发生异常，请到修改页面上传商品图片"),
    GET_WARE_ATTR_FAILURE("1003", " 获取商品属性发生异常"),
    GET_WARE_ATTRVALUE_FAILURE("1004", " 获取商品属性值发生异常"),
    /** 图片空间空间不足 */
    IMG_ZONE_LACK_SPACE("1000", "图片空间空间不足"),
    WARE_MAIN_IMG_NOT_NULL("1001", "商品主图不能为空"),

    EMAIL_HAS_CREATED("501","邮箱已经被注册");



    private String code;
    private String msg;

    private ServiceResponseConstants(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
