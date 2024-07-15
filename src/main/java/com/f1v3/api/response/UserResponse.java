package com.f1v3.api.response;

import com.f1v3.api.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;

    private final String name;


    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
