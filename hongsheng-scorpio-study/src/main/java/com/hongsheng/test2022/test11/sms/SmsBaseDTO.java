package com.hongsheng.test2022.test11.sms;

import lombok.Data;

/***
 * 短信
 */
@Data
public abstract class SmsBaseDTO {
    /***
     * 获取文本内容
     * @return
     */
    public abstract String content();
}
