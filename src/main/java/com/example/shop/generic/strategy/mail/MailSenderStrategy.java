package com.example.shop.generic.strategy.mail;

import com.example.shop.generic.strategy.GenericStrategy;
import com.example.shop.generic.strategy.mail.model.MailType;

public interface MailSenderStrategy extends GenericStrategy<MailType> {
    void sendMail();
}
