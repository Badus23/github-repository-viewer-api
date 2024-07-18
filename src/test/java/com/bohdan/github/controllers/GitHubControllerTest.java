package com.bohdan.github.controllers;

import com.bohdan.github.exception.UserNotFoundException;
import com.bohdan.github.models.Repository;
import com.bohdan.github.services.GitHubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GitHubController.class)
@ExtendWith(MockitoExtension.class)
public class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    @Test
    public void testGetUserRepositoriesSuccess() throws Exception {
        Repository repository = new Repository("repo1", "testUser", Collections.emptyList());
        when(gitHubService.getRepositories("testUser")).thenReturn(Collections.singletonList(repository));

        mockMvc.perform(get("/api/github/users/testUser/repos")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("repo1"))
                .andExpect(jsonPath("[0].ownerLogin").value("testUser"));
    }

    @Test
    public void testGetUserRepositoriesUserNotFound() throws Exception {
        when(gitHubService.getRepositories("unknownUser")).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/api/github/users/unknownUser/repos")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value(404))
                .andExpect(jsonPath("message").value("User not found."));
    }

    @Test
    public void testGetUserRepositoriesNotAcceptable() throws Exception {
        mockMvc.perform(get("/api/github/users/testUser/repos")
                        .header("Accept", "text/plain"))
                .andExpect(status().isNotAcceptable());
    }
}
