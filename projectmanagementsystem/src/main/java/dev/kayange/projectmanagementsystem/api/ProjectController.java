package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.request.InvitationRequest;
import dev.kayange.projectmanagementsystem.dto.request.ProjectRequest;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.service.ChatService;
import dev.kayange.projectmanagementsystem.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("projects")
@Tag(name = "Projects", description = "The Projects API to use")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> findAllProjectsByTeam(
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(required = false, name = "tag") String tag,
            Authentication authentication
    ){
        //boolean authenticated = authentication.isAuthenticated();
        var user = (UserEntity) authentication.getPrincipal();
        var projects = projectService.getProjectsByTeam(user, category, tag);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Projects Fetched successfully")
                .response(projects)
                .build();

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> findAllProjects(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size,
            Authentication authentication
    ){
        //boolean authenticated = authentication.isAuthenticated();
        var user = (UserEntity) authentication.getPrincipal();
        var projects = projectService.getAllProjects(page, size);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Projects Fetched successfully")
                .response(projects)
                .build();

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    @Operation(method = "Get Project By Passing Project ID")
    public ResponseEntity<?> getProjectById(@PathVariable(name = "projectId") String projectId){
        var project = projectService.getProjectById(Long.valueOf(projectId));
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Project Fetched successfully")
                .response(project)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createNewProject(
            @RequestBody @Valid ProjectRequest request,
            Authentication authentication
            ){
        var user = (UserEntity) authentication.getPrincipal();
        projectService.createProject(request, user.getId());
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Project Created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<ApiResponse<?>> updateProject(
            @RequestBody @Valid ProjectRequest request,
            @PathVariable(name = "projectId") Long projectId,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        projectService.updateProjectDetails(request, projectId, user.getId());
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Project updated successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<ApiResponse<?>> deleteProject(
            @PathVariable(name = "projectId") Long projectId,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        projectService.deleteProject(projectId, user.getId());
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Project deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchProjects(
            @RequestParam(required = false, name = "keyword") String keyword,
            Authentication authentication
    ){
        var user = (UserEntity) authentication.getPrincipal();
        var projects = projectService.searchProjects(keyword, user);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Projects Fetched successfully")
                .response(projects)
                .build();

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<ApiResponse<?>> getProjectChat(@PathVariable(name = "projectId") Long projectId){
        var chat = projectService.getProjectChat(projectId);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Project chat fetched successfully")
                .response(chat)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<?>> inviteMember(
            @Valid @RequestBody InvitationRequest request,
            Authentication authentication
            ) throws MessagingException {
        var user = (UserEntity) authentication.getPrincipal();
        projectService.inviteMember(request, user);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Invitation sent successfully")
                .build();

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/accept-invite")
    public ResponseEntity<ApiResponse<?>> acceptInvite(
            @RequestParam(name = "token") String token,
            Authentication authentication
    )  {
        var user = (UserEntity) authentication.getPrincipal();
        projectService.acceptInvite(token, user);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Invitation accepted successfully")
                .build();

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
