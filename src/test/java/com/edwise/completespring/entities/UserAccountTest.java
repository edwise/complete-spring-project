package com.edwise.completespring.entities;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAccountTest {
    private static final long ID_TEST1 = 123L;
    private static final long ID_TEST2 = 456L;
    private static final String USERNAME_TEST1 = "aragorn1981";
    private static final String USERNAME_TEST2 = "neo_80";
    private static final String PASSWORD_TEST1 = "passwordTest1";
    private static final UserAccountType USER_TYPE_TEST1 = UserAccountType.REST_USER;

    @Test
    public void testEquals() {
        UserAccount userAccount1 = createUserAccount(ID_TEST1, USERNAME_TEST1, PASSWORD_TEST1, USER_TYPE_TEST1);
        UserAccount userAccount2 = createUserAccount(ID_TEST1, USERNAME_TEST1, PASSWORD_TEST1, USER_TYPE_TEST1);

        assertThat(userAccount1.equals(userAccount2) && userAccount2.equals(userAccount1)).isTrue();
    }

    @Test
    public void testNotEqualsWithDifferentsFields() {
        UserAccount userAccount1 = createUserAccount(ID_TEST1, USERNAME_TEST1, null, USER_TYPE_TEST1);
        UserAccount userAccount2 = createUserAccount(ID_TEST1, USERNAME_TEST2, null, USER_TYPE_TEST1);

        assertThat(userAccount1.equals(userAccount2) || userAccount2.equals(userAccount1)).isFalse();
    }

    @Test
    public void testNotEqualsWithDifferentsObjects() {
        UserAccount userAccount = createUserAccount(ID_TEST1, null, null, USER_TYPE_TEST1);

        assertThat(userAccount).isNotEqualTo(new Object());
    }

    @Test
    public void testHashCode() {
        UserAccount userAccount1 = createUserAccount(ID_TEST1, USERNAME_TEST1, PASSWORD_TEST1, USER_TYPE_TEST1);
        UserAccount userAccount2 = createUserAccount(ID_TEST1, USERNAME_TEST1, PASSWORD_TEST1, USER_TYPE_TEST1);

        assertThat(userAccount1.hashCode()).isEqualTo(userAccount2.hashCode());
    }

    @Test
    public void testHasCodeWithDifferentFields() {
        UserAccount userAccount1 = createUserAccount(ID_TEST1, USERNAME_TEST1, PASSWORD_TEST1, USER_TYPE_TEST1);
        UserAccount userAccount2 = createUserAccount(ID_TEST2, USERNAME_TEST2, PASSWORD_TEST1, USER_TYPE_TEST1);

        assertThat(userAccount1.hashCode()).isNotEqualTo(userAccount2.hashCode());
    }

    @Test
    public void testToString() {
        UserAccount userAccount = createUserAccount(null, null, null, USER_TYPE_TEST1);

        assertThatUserAccountStringContainsAllFields(userAccount.toString());
    }

    private void assertThatUserAccountStringContainsAllFields(String userAccountString) {
        assertThat(userAccountString).contains("id=null");
        assertThat(userAccountString).contains("username=null");
        assertThat(userAccountString).contains("password=null");
    }

    private static UserAccount createUserAccount(Long id, String username, String password, UserAccountType userAccountType) {
        return new UserAccount()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setUserType(userAccountType);
    }
}