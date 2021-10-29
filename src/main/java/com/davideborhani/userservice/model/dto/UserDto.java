package com.davideborhani.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userName;
    private String birthDate;//Date on entity
    private String countryOfResidence;//only France allowed
    private String phoneNumber;//optional
    private String gender;//optional
}
