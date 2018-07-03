package com.greenfox.szilvi.githubchecker.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddMemberForm {

    @NotNull(message = "You must add at least one member here!")
    String members;
    @NotNull(message = "You must provide a valid team name!")
    String teamName;
}
