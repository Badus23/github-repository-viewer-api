package com.bohdan.github.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Repository {
    private String name;
    private String ownerLogin;
    private List<Branch> branches;
}