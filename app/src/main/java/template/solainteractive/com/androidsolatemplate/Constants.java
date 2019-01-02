package template.solainteractive.com.androidsolatemplate;

/**
 * Created by BillySaputra on 22-Aug-17.
 */

public class Constants {
    public static final long TIMEOUT_CONNECTION = 15;
    /**
     * Fetch Firebase = 0 ->Development Mode
     * Fetch Firebase = 3600 ->Release Mode
     * **/
    public static final int FETCH_FIREBASE = 0;
    public static final int SPLASH = 2000;

    public interface SharedPreference {
        String EMAIL = "EMAIL";
        String NAME = "NAME";
        String PASSWORD = "PASSWORD";
        String ACCESS_TOKEN = "ACCESS_TOKEN";
        String LOGIN_STATUS = "LOGIN_STATUS";
        String AB_ACTIVE = "AB_ACTIVE";
        String AB_ALL_DEVICES = "AB_ALL_DEVICES";
        String DEVICES_ARRAY = "DEVICES_ARRAY";
        String LOGIN_ARRAY = "LOGIN_ARRAY";
    }

    public interface URL_API {
//        String BASE_URL = "https://apidev.recharge.id/backend_recharge_terminalbook/public/api/mobile/";
        String BASE_URL = "http://192.168.87.230/backend_recharge_terminalbook/public/api/mobile/";
//        String BASE_URL = BuildConfig.BASE_URL;
        //String BASE_URL_RELEASE = "https://apiterminalbook.recharge.id/api/mobile/";
        String UPLOAD = "terminal/upload";
        String POST_LOGIN = "login";
        String POST_LOGOUT = "logout";
        String UPDATE = "terminal/update";
        String GET_TERMINAL_URL = "terminal/list";
        String GET_RATE_LIST = "rate/list";
        String CHANGE_PASSWORD = "changePassword";
        String TERMINAL_SEARCH = "terminal/search";
        String FORGET_PASSWORD = "forgetPassword";
        String DELETE = "terminal/delete";
    }
    public interface APIObject {
        String STATUS = "STATUS";
        String MESSAGE = "MESSAGE";
        String TOKEN = "TOKEN";
        String TERMINAL_ID = "TERMINAL_ID";
        String TERMINAL_LATITUDE = "TERMINAL_LATITUDE";
        String TERMINAL_LONGITUDE = "TERMINAL_LONGITUDE";
        String TERMINAL_ADDRESS = "TERMINAL_ADDRESS";
        String TERMINAL_NAME = "TERMINAL_NAME";
        String TERMINAL_TYPE_ID = "TERMINAL_TYPE_ID";
        String TERMINAL_OPEN_TIME = "TERMINAL_OPEN_TIME";
        String TERMINAL_CLOSED_TIME = "TERMINAL_CLOSED_TIME";
        String NETWORK_TYPE = "NETWORK_TYPE";
        String POSTAL_CODE = "POSTAL_CODE";
        String DESCRIPTION = "DESCRIPTION";
        String METADATA = "METADATA";
        String NOTIFICATION_TOKEN = "NOTIFICATION_TOKEN";
        String EMAIL = "EMAIL";
        String PASSWORD = "PASSWORD";
        String TERMINAL_ACTIVE_STATUS = "TERMINAL_ACTIVE_STATUS";
        String RATE_ID = "RATE_ID";
        String HOURLY_RATE = "HOURLY_RATE";
        String DAILY_CAP = "DAILY_CAP";
        String AVATAR_PICTURE = "AVATAR_PICTURE";

        String SEARCH_KEYWORDS= "SEARCH_KEYWORDS";
        String OLD_PASSWORD = "OLD_PASSWORD";
        String NEW_PASSWORD = "NEW_PASSWORD";
    }

    public interface LogInStatus {
        String LOGIN = "LOGIN";
        String NOT_LOGIN = "NOT_LOGIN";
    }
    public interface FirebaseRemoteConfig {
        String IS_MAINTENANCE = "is_maintenance";
        String CURRENT_VERSION = "current_version";
        String MIN_VERSION = "min_version";
        String TEST_AB = "test_ab";
        String WELCOME_MESSAGE = "welcome_message";
    }
    public interface ObjectJSON {
        String ACTIVE = "ACTIVE";
        String ALL_DEVICE = "ALL_DEVICES";
        String DEVICES = "DEVICES";
        String LOGIN = "LOGIN";
        String TITLE = "TITLE";
        String MESSAGE = "MESSAGE";
        String IMAGE_URL = "IMAGE_URL";
    }
    public interface Result {
        String SUCCESS = "success";
        String FAILED = "failed";
        String MAINTENANCE = "maintenance";
        String CRITICAL_UPDATE = "critical update";
        String INVALID_TOKEN = "invalid token";
        String TOKEN_EXPIRED = "token expired";
        String PERMISSION_DENIED = "permission denied";
    }

    public static final String DATE_FORMAT = "dd MMMM yyyy";
}
