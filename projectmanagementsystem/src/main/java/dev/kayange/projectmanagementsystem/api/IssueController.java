package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.request.IssueRequest;
import dev.kayange.projectmanagementsystem.dto.response.IssueResponse;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.service.IssueService;
import dev.kayange.projectmanagementsystem.utils.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("issues")
@Tag(name = "Issues", description = "The Issues API to use")
public class IssueController {
    private final IssueService issueService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IssueResponse>> findIssueById(
            @PathVariable("id") Long issueId
    ){
        var issue = issueService.getIssueById(issueId);
        ApiResponse<IssueResponse> response = ApiResponse.<IssueResponse>builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Issues Fetched successfully")
                .response(ObjectMapper.convertToIssueResponse(issue))
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ApiResponse<List<IssueResponse>>> findIssueByProjectId(
            @PathVariable("id") Long projectId
    ){
        var issues = issueService.getIssueByProjectId(projectId);
        var data = issues.stream().map(ObjectMapper::convertToIssueResponse).toList();
        ApiResponse<List<IssueResponse>> response = ApiResponse
                .<List<IssueResponse>>builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Issues Fetched successfully")
                .response(data)
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<IssueResponse>> createNewIssue(
            @RequestBody @Valid IssueRequest request,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        issueService.createIssue(request, user.getId());
        ApiResponse<IssueResponse> response = ApiResponse.<IssueResponse>builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Issue Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
