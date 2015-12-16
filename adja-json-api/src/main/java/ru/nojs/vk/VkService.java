package ru.nojs.vk;

import javax.ws.rs.*;

@Produces("application/json")
public interface VkService {
    @GET
    @Path("/method/users.get")
    VkUsers getInfo(@QueryParam("user_ids") String userId);
}
