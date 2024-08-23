package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.request.MessageRequest;
import dev.kayange.projectmanagementsystem.dto.response.IssueResponse;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
@Tag(name = "Messages", description = "The Message API to use")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<IssueResponse>> sendMessage(
            @RequestBody @Valid MessageRequest request,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        messageService.sendMessage(user.getId(), request.getProjectId(), request.getMessage());
        ApiResponse<IssueResponse> response = ApiResponse.<IssueResponse>builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Message Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{project-id}")
    public ResponseEntity<ApiResponse<IssueResponse>> getMessageByChatId(
            @PathVariable("project-id") Long projectId
    ){
        messageService.getMessagesByProjectId(projectId);
        ApiResponse<IssueResponse> response = ApiResponse.<IssueResponse>builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Message Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
