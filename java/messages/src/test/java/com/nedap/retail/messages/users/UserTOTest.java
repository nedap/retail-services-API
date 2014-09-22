package com.nedap.retail.messages.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for UserTO.
 */
public class UserTOTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void test_parse_json_user_unknown_role_name() throws Exception {

        final StringBuilder json = new StringBuilder();
        json.append("{\"id\":\"1\",\"display_name\":\"Bob\"");
        json.append(",\"authorisations\":[{\"role\":\"cube_nedap_admin\"},{\"role\":\"cube_back_office\"},{\"role\":\"foo\"}]}");

        // Parse JSON into user object.
        final UserTO user = mapper.readValue(json.toString(), UserTO.class);

        // Test user object.
        assertEquals("1", user.getId());
        assertEquals("Bob", user.getDisplayName());

        // Number of authorisations should be 3, including role "foo"!
        final Set<Authorisation> authorisations = user.getAuthorisations();
        assertEquals(3, authorisations.size());

        assertTrue(containsRole(authorisations, "cube_nedap_admin"));
        assertTrue(containsRole(authorisations, "cube_back_office"));
        assertTrue(containsRole(authorisations, "foo"));

        // Test isAuthorized using role enum.
        assertTrue(user.isAuthorized(Role.cube_nedap_admin));
        assertTrue(user.isAuthorized(Role.cube_back_office));
        assertFalse(user.isAuthorized(Role.cube_user_manager));

        // Test isAuthorized using role string value.
        assertTrue(user.isAuthorized("cube_nedap_admin"));
        assertTrue(user.isAuthorized("cube_back_office"));
        assertFalse(user.isAuthorized("cube_user_manager"));
        assertTrue(user.isAuthorized("foo"));
    }

    private static boolean containsRole(final Set<Authorisation> authorisations, final String role) {
        for (final Authorisation authorisation : authorisations) {
            if (authorisation.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
