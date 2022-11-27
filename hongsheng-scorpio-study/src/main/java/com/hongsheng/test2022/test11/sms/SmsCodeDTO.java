package com.hongsheng.test2022.test11.sms;

import lombok.Data;
import java.io.Serializable;
import java.util.Objects;

/***
 * 短信验证码
 */
@Data
public class SmsCodeDTO extends SmsBaseDTO implements Serializable {

    private static final long serialVersionUID = -5614921781842068775L;

    /***
     * 文本
     */
    private static final String CONTENT = "【锦鲤好客】您的验证码是：%s，请勿泄露给其他人";

    private static final String CONTENT_HS = "【好送】您的短信验证码为：%s，[有效期为5分钟]，感谢您使用好送服务。";



    private String code;
    /**
     * 0:锦鲤 1:好送
     */
    private Integer signType;

    public SmsCodeDTO(String code) {
        this.code = code;
    }
    public SmsCodeDTO(String code, Integer signType) {
        this.code = code;
        this.signType = signType;
    }

    public SmsCodeDTO() {
    }

    @Override
    public String content() {
        if(Objects.equals(this.signType, 1)){
            return String.format(CONTENT_HS, code);
        }else {
            return String.format(CONTENT, code);
        }
    }
}
