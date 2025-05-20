package org.example.enums;

public enum GraphQLTemplateName {
    GET_USER_COUNT("graphqlGetUserCountTemplate"),
    GET_USER_BY_ID("graphqlGetUserByIdTemplate"),
    DELETE_USER("graphqlDeleteUserTemplate"),
    CREATE_USER("graphqlCreateUserTemplate"),
    UPDATE_USER("graphqlUpdateUserTemplate");

    public String getTemplateName() {
        return templateName;
    }

    public final String templateName;

    private GraphQLTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
