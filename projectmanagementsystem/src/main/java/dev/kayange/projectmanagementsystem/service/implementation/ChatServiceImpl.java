package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.ChatRepository;
import dev.kayange.projectmanagementsystem.entity.Chat;
import dev.kayange.projectmanagementsystem.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    @Override
    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }
}
