package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);

}
