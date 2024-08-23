package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.request.CommentRequest;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
@Tag(name = "Comments", description = "The Comments API to use")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createNewProject(
            @RequestBody @Valid CommentRequest request,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        commentService.createComment(user.getId(), request.getIssueId(), request.getComment());
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Comment Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteComment(
            @PathVariable("id") Long commentId,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        commentService.deleteComment(user.getId(), commentId);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Comment Deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{issue-id}")
    public ResponseEntity<ApiResponse<?>> getCommentByIssue(
            @PathVariable("issue-id") Long issueId
    ){
        commentService.findCommentByIssueId(issueId);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Project Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
