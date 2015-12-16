package ru.nojs.vk;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

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
