package dev.kaykyfreitas.mailsender.entity;

import lombok.Data;

@Data
public class CustomerProductInterest {

    private Customer customer;
    private Product product;

}
