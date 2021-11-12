package com.example.shop.service.impl;

import com.example.shop.domain.dao.NotificationHistory;
import com.example.shop.repository.NotificationHistoryRepository;
import com.example.shop.service.NotificationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHistoryServiceImpl implements NotificationHistoryService {
    private final NotificationHistoryRepository notificationHistoryRepository;

    @Override
    public NotificationHistory save(NotificationHistory notificationHistory) {
        return notificationHistoryRepository.save(notificationHistory);
    }
}
