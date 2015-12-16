package ru.nojs.vk;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class VkUsers {

    @JsonProperty("response")
    List<VkUser> VkUsers = new ArrayList<>();

    @JsonSetter()
    public void add(VkUser user){
        VkUsers.add(user);
    }

    public List<VkUser> users(){
        return VkUsers;
    }
}
