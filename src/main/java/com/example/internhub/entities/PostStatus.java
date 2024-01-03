package com.example.internhub.entities;

import lombok.Getter;
import lombok.ToString;

@Getter
public enum PostStatus {
    ALWAYS_OPENED, OPENED, CLOSED, NEARLY_CLOSED, DELETED
}
