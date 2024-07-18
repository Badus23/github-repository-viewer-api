package com.bohdan.github.controllers;

import com.bohdan.github.exceptions.UserNotFoundException;
import com.bohdan.github.models.ErrorResponse;
import com.bohdan.github.models.Repository;
import com.bohdan.github.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @Value("${error.notAcceptable}")
    private String notAcceptableErrorMessage;
    @Value("${error.internalServerError}")
    private String internalServerErrorMessage;
    @Value("${error.userNotFound}")
    private String userNotFoundErrorMessage;
    @Value("${error.forbidden}")
    private String forbiddenErrorMessage;

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getUserRepositories(
            @PathVariable String username,
            @RequestHeader(value = "Accept", required = false) String acceptHeader) {

        if (!"application/json".equals(acceptHeader)) {
            return createErrorResponse(HttpStatus.NOT_ACCEPTABLE, notAcceptableErrorMessage);
        }

        try {
            List<Repository> repositories = gitHubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (UserNotFoundException e) {
            return createErrorResponse(HttpStatus.NOT_FOUND, userNotFoundErrorMessage);
        } catch (HttpClientErrorException e) {
            return createErrorResponse(HttpStatus.FORBIDDEN, forbiddenErrorMessage);
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, internalServerErrorMessage);
        }
    }

    private ResponseEntity<?> createErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}