package com.example.dto;

import lombok.Data;

@Data
public class EntryRequest {
    private String title;
    private String content;
    private String summary;
}