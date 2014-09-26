package com.nedap.retail.messages.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for User.
 */
public class UserTest {

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
        final User user = mapper.readValue(json.toString(), User.class);

        "expires_at":"2049-01-01T00:00:00Z"
        
        // {"id":"1","display_name":"emil kappert",
        // "name":{"given_name":"emil","family_name":"kappert"},"gender":"m","sitegroup_id":1,
        // "username":"emil.kappert","email":"emil.kappert@nedap.com","language":"en",
        // "authorisations":[{"role":"cube_nedap_admin","expires_at":"2049-01-01T00:00:00Z"},
        // {"role":"cube_back_office","expires_at":"2049-01-01T00:00:00Z"},{"role":"cube_user_manager","expires_at":null},
        // {"role":"access_viewer","expires_at":null},{"role":"access_admin","expires_at":null},{"role":"access_admin_plus","expires_at":null},{"role":"access_super_admin","expires_at":null},{"role":"eas_viewer","expires_at":null},{"role":"visitor_viewer","expires_at":null},{"role":"light_energy_viewer","expires_at":null},{"role":"light_energy_super_admin","expires_at":null},{"role":"visuals_viewer","expires_at":null},{"role":"access_tech_level1","expires_at":"2049-01-01T00:00:00Z"},{"role":"access_tech_level2","expires_at":"2049-01-01T00:00:00Z"},{"role":"eas_tech_level1","expires_at":"2049-01-01T00:00:00Z"},{"role":"eas_tech_level2","expires_at":"2049-01-01T00:00:00Z"},{"role":"storeid_tech_level1","expires_at":"2049-01-01T00:00:00Z"},{"role":"storeid_tech_level2","expires_at":"2049-01-01T00:00:00Z"},{"role":"storeid_system_integrator","expires_at":"2049-01-01T00:00:00Z"},{"role":"storeid_system_admin","expires_at":null},{"role":"storeid_customer","expires_at":null},{"role":"storeid_customer_admin","expires_at":null},{"role":"isense_tech_level1","expires_at":"2049-01-01T00:00:00Z"},{"role":"isense_tech_level2","expires_at":"2049-01-01T00:00:00Z"},{"role":"isense_system_integrator","expires_at":"2049-01-01T00:00:00Z"},{"role":"isense_system_admin","expires_at":null},{"role":"isense_customer","expires_at":null},{"role":"isense_customer_admin","expires_at":null},{"role":"tweet_viewer","expires_at":null},{"role":"tweet_super_admin","expires_at":null},{"role":"cube_beta","expires_at":null},{"role":"multi_store","expires_at":null},{"role":"expert_mode","expires_at":null},{"role":"access_pin_and_block","expires_at":null}]}
        
        
        
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
