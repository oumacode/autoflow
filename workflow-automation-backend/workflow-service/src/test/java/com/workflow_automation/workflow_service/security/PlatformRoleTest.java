package com.workflow_automation.workflow_service.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlatformRoleTest {

    @Test
    @DisplayName("from() converts a lowercase role name to the matching enum value")
    void from_lowercaseName_returnsMatchingRole() {
        // Arrange
        String rawRole = "admin";

        // Act
        PlatformRole result = PlatformRole.from(rawRole);

        // Assert
        assertEquals(PlatformRole.ADMIN, result);
    }

    @Test
    @DisplayName("if input is null the role is USER")
    void from_null_returnUser(){
        //Arrange
        String rawRole = null;

        //Act
        PlatformRole result = PlatformRole.from(rawRole);

        //Assert
        assertEquals(PlatformRole.USER, result);
    }

    @Test
    @DisplayName("if blank input the role is USER")
    void from_blank_returnUser(){
        //Arrange
        String rawRole = " ";

        //Act
        PlatformRole result = PlatformRole.from(rawRole);

        //Assert
        assertEquals(PlatformRole.USER, result);
    }

    @Test
    @DisplayName("if \" Viewer \" the role is VIEWER")
    void from_mixedCaseWithSpaces_returnsViewer(){
        //Arrange
        String rawRole =" Viewer ";

        //Act
        PlatformRole result = PlatformRole.from(rawRole);

        //Assert
        assertEquals(PlatformRole.VIEWER, result);
    }

    @Test
    @DisplayName("if unknown retuen user")
    void from_unknownRole_returnsUSER(){
        //Arrange
        String rawRole ="superuser";

        //Act
        PlatformRole result = PlatformRole.from(rawRole);

        //Assert
        assertEquals(PlatformRole.USER, result);
    }
}
