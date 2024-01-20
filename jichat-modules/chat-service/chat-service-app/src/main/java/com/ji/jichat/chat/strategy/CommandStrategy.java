package com.ji.jichat.chat.strategy;


import com.ji.jichat.chat.enums.CommandCodeEnum;
import com.ji.jichat.common.pojo.UpMessage;

/**
 * 支付 PayService(统一的支付Service方便将来扩展)
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
public interface CommandStrategy {


    CommandCodeEnum getCommandCode();

    /**
      * 执行
       * @param message 消息
      * @author jishenglong on 2023/8/16 9:45
      **/
    String execute(UpMessage message);


}
