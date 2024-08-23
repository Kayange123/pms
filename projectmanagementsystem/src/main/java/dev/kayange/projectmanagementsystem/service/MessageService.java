package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Long senderId, Long projectId, String message);
    List<Message> getMessagesByProjectId(Long projectId);

}
