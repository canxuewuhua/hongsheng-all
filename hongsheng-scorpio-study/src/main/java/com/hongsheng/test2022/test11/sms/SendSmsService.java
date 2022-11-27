package com.hongsheng.test2022.test11.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 本例子一个是说明
 * 1、DTO可以使用继承抽象类写一个content方法 进行赋值
 * 2、子线程异步执行代码逻辑
 */
@Service
@Slf4j
public class SendSmsService {

    /**
     * 缓存后 发短信使用子线程去执行 考虑使用异步注解是不是也可以
     * @param prefix
     * @param phone
     * @param smsCodeDTO
     * @return
     */
    public String sendCode(String prefix, String phone, SmsCodeDTO smsCodeDTO) {
        // 缓存验证码信息 缓存验证码有效期
        Thread thread = new Thread() {
            public void run() {
                Boolean bool = Boolean.FALSE;
//                boolean bool = smsUtils.sendSms(phone, smsCodeDTO.content(), 0);
                if (!bool) {
                    log.error("发生错误");
                }
            }
        };
        thread.start();
        return null;
    }

    /**
     * 生存验证码
     * @return
     */
    public String getVerificationCode() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }
}
