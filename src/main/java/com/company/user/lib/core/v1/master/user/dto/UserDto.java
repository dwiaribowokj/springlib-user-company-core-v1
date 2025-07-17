package com.company.user.lib.core.v1.master.user.dto;

import com.company.user.lib.core.v1.master.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String id;
    private String nama;
    private String noHp;
    private String email;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nama(user.getNama())
                .noHp(user.getNoHp())
                .email(user.getEmail())
                .build();
    }
}
