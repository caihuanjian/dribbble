package rain.com.dribbble_client.mvp.model;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class UserToken {

    /**
     * access_token : 29ed478ab86c07f1c069b1af76088f7431396b7c4a2523d06911345da82224a0
     * token_type : bearer
     * scope : public write
     */

    private String access_token;
    private String token_type;
    private String scope;

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }


    public String getScope() {
        return scope;
    }
}
