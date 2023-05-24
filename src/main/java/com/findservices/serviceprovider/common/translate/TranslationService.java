package com.findservices.serviceprovider.common.translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslationService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Object... objs) {
        return this.messageSource.getMessage(key, objs, Locale.getDefault());
    }

    public String getMessage(String key) {
        return this.messageSource.getMessage(key, null, Locale.getDefault());
    }
}
