package com.example.licensemanagementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
    private String type;
    private String id;
    private String title;
    private String subtitle;
}