package in.codesquad.bepositive;


public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://u4ia.in/bp/addUser.php";
    public static final String URL_LOGIN = "http://u4ia.in/bp/checkLogin.php";
    public static final String URL_NOTIFICATIONS = "http://u4ia.in/bp/getNotifications.php";
    public static final String URL_REQUEST = "http://u4ia.in/bp/getRequests.php";
    public static final String URL_MESSAGE = "http://u4ia.in/bp/getMessages.php";
    public static final String URL_USERS_LIST = "http://u4ia.in/bp/getUsers.php";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_NAME     = "name";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_EMAIL    = "email";
    public static final String KEY_EMP_MOBILE   = "mobile";
    public static final String KEY_EMP_PINCODE  = "pincode";
    public static final String KEY_EMP_BLOOD    = "blood";
    public static final String KEY_EMP_PASSWORD    = "password";
    public static final String KEY_EMP_FIREBASEID    = "firebaseid";

    //JSON Tags
    public static final String TAG_JSON_ARRAY   ="result";
    public static final String TAG_NAME         = "name";
    public static final String TAG_PLACE        = "place";
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MOBILE       = "mobile";
    public static final String TAG_PINCODE      = "pincode";
    public static final String TAG_BLOOD        = "blood";

    public static final String TAG_TITLE        = "title";
    public static final String TAG_CONTENT      = "content";
    public static final String TAG_MESSAGE      = "message";

    //employee id to pass with intent
    public static final String email = "email";

    public static final String SHARED_PREF_NAME = "bp";

    public  static final String NAME_SHARED_PREF = "name";
    public  static final String USERNAME_SHARED_PREF = "username";
    public  static final String PASSWORD_SHARED_PREF = "password";
    public  static final String EMAIL_SHARED_PREF = "email";
    public  static final String MOBILE_SHARED_PREF = "mobile";
    public  static final String PINCODE_SHARED_PREF = "pincode";
    public  static final String BLOOD_SHARED_PREF = "blood";
    public  static final String LOGGEDIN_SHARED_PREF = "loggedin";

}
