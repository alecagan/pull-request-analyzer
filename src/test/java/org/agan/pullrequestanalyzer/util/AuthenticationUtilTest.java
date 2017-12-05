package org.agan.pullrequestanalyzer.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class AuthenticationUtilTest {

    @Test
    public void getBasicAuthHeadersTest() throws Exception {
        Map<String, String> generatedHeaders = AuthenticationUtil.getBasicAuthHeaders("mynameisalec", "myaccesstokenissecret");

        Assert.assertEquals(1, generatedHeaders.size());
        Assert.assertTrue(generatedHeaders.containsKey("Authorization"));
        Assert.assertEquals("Basic bXluYW1laXNhbGVjOm15YWNjZXNzdG9rZW5pc3NlY3JldA==",
                generatedHeaders.get("Authorization"));
    }
}
