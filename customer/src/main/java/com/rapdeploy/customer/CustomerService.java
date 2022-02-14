package com.rapdeploy.customer;

import com.rapdeploy.amqp.RabbitMQMessageProducer;
import com.rapdeploy.clients.fraud.FraudCheckResponse;
import com.rapdeploy.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final FraudClient fraudClient;
    private final RestTemplate restTemplate;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);

//        FraudCheckResponse fraudCheckResponse =
//                fraudClient.isFraudster(customer.getId());

//        if (fraudCheckResponse.isFraudster()) {
//            throw new IllegalStateException("fraudster");
//        }

        restTemplate.getForObject("http://localhost:8081/ap1/v1/fraud-check/{customerId}",FraudCheckResponse.class,customer.getId());

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode...",
                        customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

    }
}
