package com.greenfox.szilvi.githubchecker.greenfoxteam.formvalid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GreenfoxTeamForm {

    @NotNull(message = "You must add at least one member here!")
    String members;
    @NotNull(message = "You must provide a cohort name!")
    String cohortName ;
    @NotNull(message = "You must provide a class name!")
    String className;
}
