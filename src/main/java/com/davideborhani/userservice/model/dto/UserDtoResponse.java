package com.davideborhani.userservice.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Response: details about the User with id field)")
public class UserDtoResponse {

    @ApiModelProperty(notes = "The id of the User")
    private Long id;
    @ApiModelProperty(notes = "The username of the User")
    private String username;
    @ApiModelProperty(notes = "The birth date of the User")
    private String birthDate;//Date on entity
    @ApiModelProperty(notes = "The country of residence of the User")
    private String countryOfResidence;//only France allowed
    @ApiModelProperty(notes = "(optional) The phone number of the User in the format: " +
            "1234567890 or 123-456-7890 or (123)456-7890 or (123)4567890. " +
            "On the database will be anyway saved in the format 1234567890")
    private String phoneNumber;//optional
    @ApiModelProperty(notes = "(optional) The gender of the User" +
            "(only male or female ignoring uppercase or lowercase. " +
            "On the database will be saved lowercase)")
    private String gender;//optional
}
