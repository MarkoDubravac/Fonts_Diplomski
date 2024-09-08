package com.mdubravac.fonts.dto;

import com.mdubravac.fonts.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminDto {
    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String token;
    private Role role;
}
