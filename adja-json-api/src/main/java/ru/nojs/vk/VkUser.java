package ru.nojs.vk;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser {
    String id;
    String firsName;
    String lastName;

    @JsonCreator
    public VkUser(
            @JsonProperty("uid") String id,
            @JsonProperty("first_name") String firsName,
            @JsonProperty("last_name") String lastName
    ) {
        this.id = id;
        this.firsName = firsName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firsName;
    }

    public String getLastName() {
        return lastName;
    }


}
