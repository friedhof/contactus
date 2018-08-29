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
package ch.teko.contactus.business.user.control;

import ch.teko.contactus.business.user.entity.User;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

@Singleton
public class UserService {

    private static final String RANDOM_USER_GENERATOR_URI = "https://randomuser.me/api/1.1/?results=12&nat=ch";
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(RANDOM_USER_GENERATOR_URI);
        final Response response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
        final JsonObject jsonResponse = response.readEntity(JsonObject.class);
        final JsonArray results = jsonResponse.getJsonArray("results");

        users.putAll(
                results.parallelStream()
                        .map(User::fromRandomUserJSON)
                        .collect(toMap(User::getId, u -> u))
        );
    }

    public synchronized UUID addUser(@NotNull final User user) {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (users.containsKey(id));
        users.put(id, user.toBuilder().id(id).build());
        return id;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Optional<User> getUser(@NotNull final UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> updateUser(@NotNull final User user) {
        return Optional.ofNullable(
                users.computeIfPresent(user.getId(), (key, value) -> user));
    }

    public Optional<User> deleteUser(@NotNull final UUID id) {
        final User user = users.get(id);
        users.remove(id);
        return Optional.ofNullable(user);
    }

}
