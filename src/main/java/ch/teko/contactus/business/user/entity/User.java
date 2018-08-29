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
package ch.teko.contactus.business.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@JsonDeserialize(builder = User.UserBuilder.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String forename;
    private String surname;
    private String street;
    private String zip;
    private String city;
    private String state;
    private String email;
    private String phone;
    private String mobile;
    private String username;
    private String pictureSmall;
    private String pictureMedium;
    private String pictureLarge;

    public static User fromRandomUserJSON(@NotNull final JsonValue jsonValue) {
        final JsonObject jsonObject = (JsonObject) jsonValue;
        return User.builder()
                .id(UUID.randomUUID())
                .forename(jsonObject.getJsonObject("name").getString("first"))
                .surname(jsonObject.getJsonObject("name").getString("last"))
                .street(jsonObject.getJsonObject("location").getString("street"))
                .zip(jsonObject.getJsonObject("location").getJsonNumber("postcode").toString())
                .city(jsonObject.getJsonObject("location").getString("city"))
                .state(jsonObject.getJsonObject("location").getString("state"))
                .email(jsonObject.getString("email"))
                .phone(jsonObject.getString("phone"))
                .mobile(jsonObject.getString("cell"))
                .username(jsonObject.getJsonObject("login").getString("username"))
                .pictureSmall(jsonObject.getJsonObject("picture").getString("thumbnail"))
                .pictureMedium(jsonObject.getJsonObject("picture").getString("medium"))
                .pictureLarge(jsonObject.getJsonObject("picture").getString("large"))
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserBuilder {
    }

}
