package lithium.community.android.sdk.auth;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

/**
 * Created by saiteja.tokala on 12/5/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class LiSSOAuthorizationRequestTest {

    public static final String TEST_URI = "testUri";
    public static final String TEST_SSO_TOKEN = "testSSOToken";
    public static final String TEST_CLIENT_ID = "testClientId";
    public static final String TEST_STATE = "testState";
    public static final String TEST_REDIRECT_URI = "testRedirectUri";

    private LiSSOAuthorizationRequest liSSOAuthorizationRequest;
    @Before
    public void setupRequest() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUriTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setUri(TEST_URI);
        assertNotEquals(null, liSSOAuthorizationRequest.getUri());
        assertEquals(TEST_URI, liSSOAuthorizationRequest.getUri().toString());
    }

    @Test
    public void getSsoTokenTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setSsoToken(TEST_SSO_TOKEN);
        assertNotEquals(null, liSSOAuthorizationRequest.getSsoToken());
        assertEquals(TEST_SSO_TOKEN, liSSOAuthorizationRequest.getSsoToken());
    }

    @Test
    public void getClientIdTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setClientId(TEST_CLIENT_ID);
        assertNotEquals(null, liSSOAuthorizationRequest.getClientId());
        assertEquals(TEST_CLIENT_ID, liSSOAuthorizationRequest.getClientId());
    }

    @Test
    public void getStateTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setState(TEST_STATE);
        assertNotEquals(null, liSSOAuthorizationRequest.getState());
        assertEquals(TEST_STATE, liSSOAuthorizationRequest.getState());
    }

    @Test
    public void getRedirectUriTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setRedirectUri(TEST_REDIRECT_URI);
        assertNotEquals(null, liSSOAuthorizationRequest.getRedirectUri());
        assertEquals(TEST_REDIRECT_URI, liSSOAuthorizationRequest.getRedirectUri());
    }

    @Test
    public void toUriTest(){
        liSSOAuthorizationRequest=new LiSSOAuthorizationRequest();
        liSSOAuthorizationRequest.setUri(TEST_URI);
        liSSOAuthorizationRequest.setClientId(TEST_CLIENT_ID);
        liSSOAuthorizationRequest.setRedirectUri(TEST_REDIRECT_URI);
        liSSOAuthorizationRequest.setState(TEST_STATE);
        assertNotEquals(null,liSSOAuthorizationRequest.toUri());
        assertEquals(TEST_URI+"?redirect_uri="+TEST_REDIRECT_URI+"&client_id="+TEST_CLIENT_ID+"&response_type=code&state="+TEST_STATE,liSSOAuthorizationRequest.toUri().toString());
    }

}
