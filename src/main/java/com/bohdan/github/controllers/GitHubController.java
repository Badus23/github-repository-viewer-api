package com.bohdan.github.controllers;

import com.bohdan.github.exception.UserNotFoundException;
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
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), notAcceptableErrorMessage);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
        }

        try {
            List<Repository> repositories = gitHubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), userNotFoundErrorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), forbiddenErrorMessage);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalServerErrorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}