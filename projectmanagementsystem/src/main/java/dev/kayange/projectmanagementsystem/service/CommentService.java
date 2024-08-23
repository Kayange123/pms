package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.entity.Comment;

import java.util.List;

public interface CommentService {

    void createComment(Long userId, Long issueId, String comment);
    void deleteComment(Long userId, Long commentId);
    List<Comment> findCommentByIssueId(Long issueId);

}
