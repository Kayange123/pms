package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findIssueByProjectId(Long issueId);
}
