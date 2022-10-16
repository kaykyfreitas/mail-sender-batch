package dev.kaykyfreitas.mailsender.step;

import dev.kaykyfreitas.mailsender.entity.CustomerProductInterest;

import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
@RequiredArgsConstructor
public class EmailSenderStepConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step emailSenderStep(
            ItemReader<CustomerProductInterest> customerProductInterestReader,
            ItemProcessor<CustomerProductInterest, SimpleMailMessage> customerProductEmailProcessor,
            ItemWriter<SimpleMailMessage> customerProductEmailWriter) {
        return stepBuilderFactory
                .get("emailSenderStep")
                .<CustomerProductInterest, SimpleMailMessage>chunk(1)
                .reader(customerProductInterestReader)
                .processor(customerProductEmailProcessor)
                .writer(customerProductEmailWriter)
                .build();
    }

}
