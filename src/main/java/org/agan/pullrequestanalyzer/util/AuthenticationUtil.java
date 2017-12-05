package org.agan.pullrequestanalyzer.util;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

public class AuthenticationUtil {

    public static Map<String, String> getBasicAuthHeaders(String username, String privateAccessToken)
    {
        String userPass = username + ":" + privateAccessToken;
        String encodedUserPass = Base64.getEncoder().encodeToString(userPass.getBytes());

        return Collections.singletonMap("Authorization", "Basic " + encodedUserPass);
    }
}