package dev.kaykyfreitas.mailsender.reader;

import dev.kaykyfreitas.mailsender.entity.Customer;
import dev.kaykyfreitas.mailsender.entity.CustomerProductInterest;
import dev.kaykyfreitas.mailsender.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;


@Configuration
public class CustomerProductInterestReaderConfig {

    @Bean
    public JdbcCursorItemReader<CustomerProductInterest> customerProductInterestReader(
            @Qualifier("appDataSource")DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<CustomerProductInterest>()
                .name("customerProductInterestReader")
                .dataSource(dataSource)
                .sql(
                        "SELECT * FROM customer_product_interest " +
                        "JOIN customer ON (customer = customer.id) " +
                        "JOIN product ON (product = product.id)"
                )
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<CustomerProductInterest> rowMapper() {
        return new RowMapper<CustomerProductInterest>() {
            @Override
            public CustomerProductInterest mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));

                Product product = new Product();
                product.setId(rs.getInt(6));
                product.setName(rs.getString(7));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));

                CustomerProductInterest customerProductInterest = new CustomerProductInterest();
                customerProductInterest.setCustomer(customer);
                customerProductInterest.setProduct(product);

                return customerProductInterest;
            }
        };
    }

}
