package com.protonmail.kschay.cryptotrader.domain.email

import org.springframework.mail.SimpleMailMessage

class Email extends SimpleMailMessage {

    private final StringBuilder stringBuilder

    Email(String to, String from) {
        this.to = to
        this.from = from
        this.text = ""
        this.stringBuilder = new StringBuilder(0)
    }

    void appendToMessage(String message) {
        this.text = stringBuilder.append(this.text).append(System.lineSeparator()).append(message).toString()
        stringBuilder.setLength(0)
    }
}
