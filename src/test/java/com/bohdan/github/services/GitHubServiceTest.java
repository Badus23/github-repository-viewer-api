package com.bohdan.github.services;

import com.bohdan.github.exceptions.UserNotFoundException;
import com.bohdan.github.models.Branch;
import com.bohdan.github.models.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GitHubServiceTest {

    @InjectMocks
    private GitHubService gitHubService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testGetRepositoriesSuccess() throws Exception {
        String username = "testUser";
        String reposJson = "[{\"name\": \"repo1\", \"owner\": {\"login\": \"testUser\"}, \"fork\": false}]";
        JsonNode reposNode = new ObjectMapper().readTree(reposJson);

        String branchesJson = "[{\"name\": \"main\", \"commit\": {\"sha\": \"abc123\"}}]";
        JsonNode branchesNode = new ObjectMapper().readTree(branchesJson);

        when(restTemplate.getForObject(contains("/users/"), eq(JsonNode.class))).thenReturn(reposNode);
        when(restTemplate.getForObject(contains("/repos/testUser/repo1/branches"), eq(JsonNode.class))).thenReturn(branchesNode);

        List<Repository> repositories = gitHubService.getRepositories(username);

        assertEquals(1, repositories.size());
        assertEquals("repo1", repositories.getFirst().getName());
        assertEquals("testUser", repositories.getFirst().getOwnerLogin());
    }

    @Test
    public void testGetRepositoriesUserNotFound() {
        String username = "unknownUser";

        when(restTemplate.getForObject(anyString(), eq(JsonNode.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        assertThrows(UserNotFoundException.class, () -> gitHubService.getRepositories(username));
    }

    @Test
    public void testGetBranchesSuccess() throws Exception {
        String username = "testUser";
        String repository = "repo1";
        String branchesJson = "[{\"name\": \"main\", \"commit\": {\"sha\": \"abc123\"}}]";
        JsonNode branchesNode = new ObjectMapper().readTree(branchesJson);

        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(branchesNode);

        List<Branch> branches = gitHubService.getBranches(username, repository);

        assertEquals(1, branches.size());
        assertEquals("main", branches.getFirst().getName());
        assertEquals("abc123", branches.getFirst().getLastCommitSha());
    }
}

