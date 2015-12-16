package ru.nojs.vk;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces("application/json")
public interface VkService {
    @GET
    @Path("/method/users.get?user_ids={userId}")
    VkUsers getInfo(@PathParam("userId") String userId);
}
