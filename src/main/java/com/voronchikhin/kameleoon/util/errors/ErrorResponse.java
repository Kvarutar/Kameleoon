package com.voronchikhin.kameleoon.util.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String msg;
    private long timestamp;
}
