package com.protonmail.kschay.cryptodatacollector.domain

import org.springframework.mail.SimpleMailMessage

class Email extends SimpleMailMessage {

    private final StringBuilder stringBuilder

    Email(final String to, final String from) {
        this.to = to
        this.from = from
        this.text = ""
        this.stringBuilder = new StringBuilder(0)
    }

    void appendToMessage(final String message) {
        this.text = stringBuilder.append(this.text).append(System.lineSeparator()).append(message).toString()
        stringBuilder.setLength(0)
    }
}
