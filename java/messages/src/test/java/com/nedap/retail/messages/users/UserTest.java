package com.nedap.retail.messages.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedap.retail.messages.IdCloudObjectMapperFactory;

/**
 * Unit tests for User.
 */
public class UserTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = IdCloudObjectMapperFactory.create();
    }

    @Test
    public void test_parse_json_user_unknown_role_name() throws Exception {

        final StringBuilder json = new StringBuilder();
        json.append("{\"id\":\"1\",\"display_name\":\"Bob\"");
        json.append(",\"authorisations\":[{\"role\":\"cube_nedap_admin\"},{\"role\":\"cube_back_office\"},{\"role\":\"foo\"}]}");

        // Parse JSON into user object.
        final User user = mapper.readValue(json.toString(), User.class);

        // Test user object.
        assertEquals("1", user.id);
        assertEquals("Bob", user.displayName);

        // Number of authorisations should be 3, including role "foo"!
        final Set<Authorisation> authorisations = user.authorisations;
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

    @Test
    public void test_parse_json_user_expires_at() throws Exception {

        final StringBuilder json = new StringBuilder();
        json.append("{\"id\":\"1\",\"display_name\":\"emil kappert\"");
        json.append(",\"given_name\":\"emil\",\"family_name\":\"kappert\"");
        json.append(",\"gender\":\"m\",\"username\":\"emil.kappert\"");
        json.append(",\"email\":\"emil.kappert@nedap.com\",\"language\":\"en\"");
        json.append(",\"authorisations\":[{\"role\":\"cube_nedap_admin\"}");
        json.append(",{\"role\":\"cube_back_office\",\"expires_at\":\"2014-01-02T00:00:00Z\"}");
        json.append(",{\"role\":\"foo\",\"expires_at\":\"2015-02-03T00:00:00+01:00\"}]}");

        // Parse JSON into user object.
        final User user = mapper.readValue(json.toString(), User.class);
        assertEquals("1", user.id);
        assertEquals("emil kappert", user.displayName);
        assertEquals("m", user.gender);
        assertEquals("en", user.language);
        assertEquals("emil.kappert", user.username);

        // Number of authorisations should be 3, including role "foo"!
        final Set<Authorisation> authorisations = user.authorisations;
        assertEquals(3, authorisations.size());

        final Authorisation authorisation1 = getAuthorisation(authorisations, "cube_back_office");
        assertEquals(new DateTime("2014-01-02T00:00:00Z").getMillis(), authorisation1.expiresAt.getMillis());
        assertEquals("cube_back_office", authorisation1.role);
        final Authorisation authorisation2 = getAuthorisation(authorisations, "foo");
        assertEquals(new DateTime("2015-02-03T00:00:00+01:00").getMillis(), authorisation2.expiresAt.getMillis());
        assertEquals("foo", authorisation2.role);
        final Authorisation authorisation3 = getAuthorisation(authorisations, "cube_nedap_admin");
        assertEquals("cube_nedap_admin", authorisation3.role);
        assertNull(authorisation3.expiresAt);

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

    private Authorisation getAuthorisation(final Set<Authorisation> authorisations, final String role) {
        for (final Authorisation authorisation : authorisations) {
            if (authorisation.role.equals(role)) {
                return authorisation;
            }
        }
        return null;
    }

    private static boolean containsRole(final Set<Authorisation> authorisations, final String role) {
        for (final Authorisation authorisation : authorisations) {
            if (authorisation.role.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
