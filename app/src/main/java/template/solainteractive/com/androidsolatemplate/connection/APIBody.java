package template.solainteractive.com.androidsolatemplate.connection;

import java.util.HashMap;
import java.util.Map;

import template.solainteractive.com.androidsolatemplate.Constants;

/**
 * Created by BillySaputra on 23-Aug-17.
 */

public class APIBody {

    public static Map<String, String> uploadBody(String id, double latitude, double longitude, String name, String address, String metatag, int typeID,

                                                 String openTime, String closeTime, String networkType, String postalCode, String descripton,
                                                 String activeStatus, int terminalRate, String avatar){
        Map<String, String> body = new HashMap<>();
        body.put(Constants.APIObject.TERMINAL_ID, id);
        body.put(Constants.APIObject.TERMINAL_LATITUDE, Double.toString(latitude));
        body.put(Constants.APIObject.TERMINAL_LONGITUDE, Double.toString(longitude));
        body.put(Constants.APIObject.TERMINAL_NAME, name);
        body.put(Constants.APIObject.TERMINAL_ADDRESS, address);
        body.put(Constants.APIObject.METADATA, metatag);
        body.put(Constants.APIObject.TERMINAL_TYPE_ID, Integer.toString(typeID));
        body.put(Constants.APIObject.TERMINAL_OPEN_TIME, openTime);
        body.put(Constants.APIObject.TERMINAL_CLOSED_TIME, closeTime);
        body.put(Constants.APIObject.NETWORK_TYPE, networkType);
        body.put(Constants.APIObject.POSTAL_CODE, postalCode);
        body.put(Constants.APIObject.DESCRIPTION, descripton);
        body.put(Constants.APIObject.TERMINAL_ACTIVE_STATUS, activeStatus);
        body.put(Constants.APIObject.RATE_ID, Integer.toString(terminalRate));
        body.put(Constants.APIObject.AVATAR_PICTURE, avatar);

        return body;
    }

    public static Map<String, String> loginBody(String email, String password, String notification_token){
        Map<String, String> login = new HashMap<>();
        login.put(Constants.APIObject.EMAIL, email);
        login.put(Constants.APIObject.PASSWORD, password);
        login.put(Constants.APIObject.NOTIFICATION_TOKEN, notification_token);
        return login;
    }

    public static Map<String, String> logoutBody(String email, String password){
        Map<String, String> logout = new HashMap<>();
        logout.put(Constants.APIObject.EMAIL, email);
        logout.put(Constants.APIObject.PASSWORD, password);
        return logout;
    }

    public static Map<String, String> deleteBody(String terminalId){
        Map<String, String> delete = new HashMap<>();
        delete.put(Constants.APIObject.TERMINAL_ID, terminalId);
        return delete;
    }

    public static Map<String, String> changePassword(String oldPass, String newPass){
        Map<String, String> body = new HashMap<>();
        body.put(Constants.APIObject.OLD_PASSWORD,oldPass);
        body.put(Constants.APIObject.NEW_PASSWORD,newPass);

        return body;
    }

    public static Map<String, String> forgetPassword(String email) {
        Map<String, String> body = new HashMap<>();
        body.put(Constants.APIObject.EMAIL, email);
        return body;
    }

    public static Map<String, String> searchTerminal(String SEARCH_KEYWORDS){
        Map<String, String> body = new HashMap<>();
        body.put(Constants.APIObject.SEARCH_KEYWORDS,SEARCH_KEYWORDS);
        return body;
    }
}
