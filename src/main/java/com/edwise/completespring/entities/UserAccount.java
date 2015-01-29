package com.edwise.completespring.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UserAccount {

    @Id
    private Long id;

    private String username;
    private String password;

    @NonNull
    private UserAccountType userType = UserAccountType.REST_USER;
}
