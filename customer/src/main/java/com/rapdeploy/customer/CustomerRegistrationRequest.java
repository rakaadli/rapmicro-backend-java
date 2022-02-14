package com.rapdeploy.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
