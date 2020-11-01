package com.protonmail.kschay.cryptotrader.job;

import com.protonmail.kschay.cryptotrader.domain.email.Email;
import com.protonmail.kschay.cryptotrader.domain.log.Logging;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JobListener extends Logging implements JobExecutionListener {

    private final JobProperties jobProperties;
    private final JavaMailSender javaMailSender;
    private final Email email;

    public JobListener(JobProperties jobProperties,
                       JavaMailSender javaMailSender,
                       Email email) {
        this.jobProperties = jobProperties;
        this.javaMailSender = javaMailSender;
        this.email = email;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Starting " + jobProperties.getName() + "...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            exitAsSuccess();
        }
        exitAsFailure(jobExecution);
    }

    private void exitAsSuccess() {
        String successMessage = jobProperties.getName() + " completed.";

        email.setSubject(successMessage);
        javaMailSender.send(email);

        log.info(successMessage);
        System.exit(0);
    }

    private void exitAsFailure(JobExecution jobExecution) {
        jobExecution.setStatus(BatchStatus.FAILED);
        String failureMessage = jobProperties.getName() + " completed.";

        email.setSubject(jobProperties.getName() + " failed.");
        javaMailSender.send(email);
        log.info("Job failed.");
        System.exit(1);
    }
}
