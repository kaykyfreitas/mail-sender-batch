package dev.kaykyfreitas.mailsender.processor;

import dev.kaykyfreitas.mailsender.entity.CustomerProductInterest;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class CustomerProductEmailProcessor implements ItemProcessor<CustomerProductInterest, SimpleMailMessage> {

    @Override
    public SimpleMailMessage process(CustomerProductInterest customerProductInterest) throws Exception {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("xpto@no-reply.com");
        email.setTo(customerProductInterest.getCustomer().getEmail());
        email.setSubject("Flash promotion!!!");
        email.setText(emailContentGenerator(customerProductInterest));

        Thread.sleep(2000); // Only for 'mailtrap.io' email service, because have some limitations for free usage

        return email;
    }

    private String emailContentGenerator(CustomerProductInterest customerProductInterest) {
        StringBuilder writer = new StringBuilder();
        writer.append(String.format("Hello, %s!\n\n", customerProductInterest.getCustomer().getName()));
        writer.append("This promotion could interest you:\n\n");
        writer.append(String.format("%s - %s\n\n", customerProductInterest.getProduct().getName(), customerProductInterest.getProduct().getDescription()));
        writer.append(String.format("Only %s!", NumberFormat.getCurrencyInstance().format(customerProductInterest.getProduct().getPrice())));
        return writer.toString();
    }

}
