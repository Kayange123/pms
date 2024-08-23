package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.dto.request.IssueRequest;
import dev.kayange.projectmanagementsystem.entity.Issue;

import java.util.List;

public interface IssueService {
    Issue getIssueById(Long id);
    List<Issue> getIssueByProjectId(Long projectId);
    void createIssue(IssueRequest request, Long userId);
    void deleteIssue(Long issueId, Long userId);
    void addUserToIssue(Long issueId, Long userId);
    void removeUserFromIssue(Long issueId, Long userId);
    void updateIssueStatus(Long issueId, String status, Long userId);
}
