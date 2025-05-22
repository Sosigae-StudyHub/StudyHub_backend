package com.studyhub.service;

public interface PaymentHistoryService {
    void recordPointCharge(Long userId, int amount);
}
