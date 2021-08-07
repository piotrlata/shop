package com.example.shop.generic.strategy.mail.impl;

import com.example.shop.generic.strategy.mail.MailSenderStrategy;
import com.example.shop.generic.strategy.mail.model.MailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterSenderStrategyImpl implements MailSenderStrategy {
    @Override
    public MailType getType() {
        return MailType.REGISTER;
    }

    @Override
    public void sendMail() {
        log.info("register");
    }
}
