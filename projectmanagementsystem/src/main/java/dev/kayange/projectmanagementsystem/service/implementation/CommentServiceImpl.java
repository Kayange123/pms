package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.CommentRepository;
import dev.kayange.projectmanagementsystem.entity.Comment;
import dev.kayange.projectmanagementsystem.entity.Issue;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.CommentService;
import dev.kayange.projectmanagementsystem.service.IssueService;
import dev.kayange.projectmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final IssueService issueService;

    @Override
    public void createComment(Long userId, Long issueId, String content) {
        var user = userService.findUserById(userId);
        Issue issue = issueService.getIssueById(issueId);
        var comment = Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .issue(issue)
                .user(user)
                .build();
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        var user = userService.findUserById(userId);
        var comment = findCommentById(commentId);
        var projectOwner = comment.getIssue().getProject().getOwner();
        //Allow only ProjectOwner and Comment creator to delete comment
        if(!comment.getUser().equals(projectOwner) || !comment.getUser().equals(user)) throw new ApiException("You are not authorized to delete this comment");
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {

        return issueService.getIssueById(issueId).getComments();
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new ApiException("Could NOT find Comment with ID "+commentId));
    }
}
