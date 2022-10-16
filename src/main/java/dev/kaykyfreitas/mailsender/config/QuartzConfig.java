package dev.kaykyfreitas.mailsender.config;

import dev.kaykyfreitas.mailsender.job.EmailSenderScheduleJob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.quartz.*;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder
                .newJob(EmailSenderScheduleJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(60)
                .withRepeatCount(2);

        return TriggerBuilder
                .newTrigger()
                .forJob(quartzJobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }

}
