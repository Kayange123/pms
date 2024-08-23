package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.MessageRepository;
import dev.kayange.projectmanagementsystem.entity.Message;
import dev.kayange.projectmanagementsystem.service.MessageService;
import dev.kayange.projectmanagementsystem.service.ProjectService;
import dev.kayange.projectmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ProjectService projectService;
    private final UserService userService;

    @Override
    public void sendMessage(Long senderId, Long projectId, String content) {
        var sender = userService.findUserById(senderId);
        var chat = projectService.getProjectById(projectId).getChat();
        var message = Message.builder()
                .message(content)
                .chat(chat)
                .sender(sender)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) {
        var chat = projectService.getProjectById(projectId).getChat();
        return messageRepository.findMessageByChatOrderByCreatedAtAsc(chat);
    }
}
