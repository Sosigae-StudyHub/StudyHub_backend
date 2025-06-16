package com.studyhub.service;

public interface PortOneService {
    boolean verifyPaymentByPaymentId(String paymentId, int expectedAmount);
}