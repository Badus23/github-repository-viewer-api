package com.bohdan.github.services;

import com.bohdan.github.exceptions.UserNotFoundException;
import com.bohdan.github.models.Branch;
import com.bohdan.github.models.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> getRepositories(String username) {
        String userUrl = "https://api.github.com/users/" + username;
        String url = "https://api.github.com/users/" + username + "/repos";

        try {
            validateUserExists(userUrl);

            JsonNode reposNode = restTemplate.getForObject(url, JsonNode.class);
            List<Repository> repositories = new ArrayList<>();

            if (reposNode != null) {
                for (JsonNode repoNode : reposNode) {
                    if (!repoNode.get("fork").asBoolean()) {
                        String repoName = repoNode.get("name").asText();
                        String ownerLogin = repoNode.get("owner").get("login").asText();
                        List<Branch> branches = getBranches(username, repoName);

                        Repository repository = new Repository(repoName, ownerLogin, branches);
                        repositories.add(repository);
                    }
                }
            }

            return repositories;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }

    protected List<Branch> getBranches(String username, String repository) {
        String url = "https://api.github.com/repos/" + username + "/" + repository + "/branches";
        JsonNode branchesNode = restTemplate.getForObject(url, JsonNode.class);
        List<Branch> branches = new ArrayList<>();

        if (branchesNode != null) {
            for (JsonNode branchNode : branchesNode) {
                String branchName = branchNode.get("name").asText();
                String lastCommitSha = branchNode.get("commit").get("sha").asText();
                branches.add(new Branch(branchName, lastCommitSha));
            }
        }

        return branches;
    }

    private void validateUserExists(String userUrl) throws UserNotFoundException {
        restTemplate.getForObject(userUrl, JsonNode.class);
    }
}