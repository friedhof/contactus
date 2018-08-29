/*
 * Contactus – Provide random user data as a demo service for RESTful clients
 * Copyright (C) 2016 Marcus Fihlon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.teko.contactus.business.user.boundary;

import ch.teko.contactus.business.user.control.UserService;
import ch.teko.contactus.business.user.entity.User;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private UserService userService;

    @Inject
    public UsersResource(@NotNull final UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response create(@NotNull final User user,
                           @Context final UriInfo info) {
        final UUID id = userService.addUser(user);
        final URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @GET
    public Collection<User> read() {
        return userService.getAllUsers();
    }

    @GET
    @Path("{id}")
    public User read(@PathParam("id") @NotNull final UUID id) {
        return userService.getUser(id)
                .orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("{id}")
    public User update(@PathParam("id") @NotNull final UUID id,
                       @NotNull final User user) {
        return userService.updateUser(user.toBuilder().id(id).build())
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") @NotNull final UUID id) {
        userService.deleteUser(id)
                .orElseThrow(NotFoundException::new);
        return Response.noContent().build();
    }

}
