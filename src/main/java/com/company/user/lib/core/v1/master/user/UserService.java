package com.company.user.lib.core.v1.master.user;

import com.company.user.lib.core.v1.master.user.dto.UserDto;
import com.company.user.lib.core.v1.master.user.dto.UserRegisterRequest;

public interface UserService {
    UserDto register(UserRegisterRequest request);
    User findById(String id);
    User findByEmail(String email);
}