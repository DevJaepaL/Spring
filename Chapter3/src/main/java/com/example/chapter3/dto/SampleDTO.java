package com.example.chapter3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class SampleDTO {
    private long sampleNo;
    private String first;
    private String last;

    private LocalDateTime registTime;
}
