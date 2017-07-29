package com.common.constant;

/**
 * Created by ln-138 on 21/2/17.
 */

public class Constant {

    public static String JOB_PROFILE_BASE_URL = "http://52.66.190.52:5151/#/front/user/";

    public static String ANDROID = "1";

    public static final String CONSTANT_FCM_TOKEN = "CONSTANT_FCM_TOKEN";
    public static int LOGIN_TYPE_FB = 1;
    public static int LOGIN_TYPE_GOOGLE = 2;
    public static int LOGIN_TYPE_EMAIL = 3;
    public static int LOGIN_TYPE_SOCIAL = 4;

    public static String QUOTES_TITLE = "quotes_title";
    public static String QUOTES_DATE = "quotes_date";
    public static String QUOTES_IMAGE = "quotes_image";
    public static String QUOTES_OFFER_PRICE = "quotes_price";
    public static String QUOTES_OFFER_ID = "quotes_id";

    public static String USER_NAME = "chat_user_name";
    public static String USER_ID = "chat_user_id";
    public static String EXTRA_DEALS_DATA = "extra_deals_data";

    public static String FACEBOOK = "facebook";
    public static String GOOGLE = "googleplus";
    public static String TWITTER = "twitter";
    public static String EMAIL = "email";

    public static final int CHAT_TYPE_TEXT = 1;
    public static final int CHAT_TYPE_IMAGE = 2;
    public static final int CHAT_TYPE_VIDEO = 3;
    public static final int CHAT_TYPE_AUDIO = 4;

    // Span Constant for GridView in RecyclerView
    public static int SPAN_SINGLE = 1;
    public static int SPAN_DOUBLE = 2;

    // API ProfileResponse check result
    public static final int API_SUCCESS = 1;
    public static final int API_FAIL = 0;
    public static final int API_IS_ALREADY_CHECKED = 2;
    public static final int API_IS_APPLY_LINK = 2;

    // Shared Preference Keys
    public static final String isUserLoggedIn = "isUserLoggedIn";
    public static final String userId = "userId";
    public static final String userEmail = "userEmail";
    public static final String isUserEmailVerified = "isUserEmailVerified";
    public static final String loginType = "loginType";
    public static final String userToken = "userToken";
    public static final String initialCategoryId = "initialCategoryId";
    public static final String initialCategoryName = "initialCategoryName";
    public static final String categoryId = "categoryId";
    public static final String categoryName = "categoryName";
    public static final String subCategory = "subCategory";
    public static final String isCategorySelected = "isCategorySelected";
    public static final String countryId = "countryId";
    public static final String stateId = "stateId";
    public static final String cityId = "cityId";
    public static final String currencySymbol = "currencySymbol";
    public static final String isUserFirstTime = "isUserFirstTime";
    public static final String socialType = "socialType";
    public static final String socialId = "socialId";
    public static final String userImage = "userImage";
    public static final String deviceLat = "deviceLat";
    public static final String deviceLong = "deviceLong";
    public static final String commonFields = "commonFields";
    public static final String loginClass = "loginClass";
    public static final String loginResultId = "loginResultId";
    public static final String loginResultStatus = "loginResultStatus";
    public static final String checkBoxType = "checkBoxType";
    public static final String cbStatus = "cbStatus";
    public static String cbHeartStatus = "cbHeartStatus";
    public static String cbWatchStatus = "cbWatchStatus";
    public static String position = "position";
    public static String isGrabAction = "isGrabAction";
    public static String isLoginFromAdapter = "isLoginFromAdapter";
    public static String isLoginFromDetail = "isLoginFromDetail";
    public static final String isQuantity = "isQuantity";
    public static final String isUnitPrice = "isUnitPrice";
    public static final String isUnitPriceTemp = "isUnitPriceTemp";
    public static String callingScreen = "callingScreen";
    public static final String bundleExtras = "bundleExtras";

    private static final String GetstateURL = "";
    public static final String isFromSplashJoin = "isFromSplashJoin";
    public static final int VIDEO_DURATION = 30; // VIDEO DURATION LIMIT

    public static final int CLICK_CLOSE_MENU = 0;
    public static final int CLICK_QUOTES = 1;
    public static final int CLICK_LOYALTY_OFFERS = 2;
    public static final int CLICK_WATCH_LIST = 3;
    public static final int CLICK_FEATURED_OFFER = 4;
    public static final int CLICK_GRABBED_HISTORY = 5;

    public static int NAME_MAX_LENGTH = 50;
    public static int ADDRESS_MAX_LENGTH = 30;
    public static int PHONE_NUMBER_MAX_LENGTH = 15;
    public static int MAX_SEARCH_TEXT_LENGTH = 55;

    public static final String sellImageCount = "sellImageCount";

    public static final String updateImageCount = "updateImageCount";
    public static String updateImageAdapterSize = "updateImageAdapterSize";

    public static final String updateConsumedImageCount = "updateConsumedImageCount";
    public static final String updateTotalImageCount = "updateTotalImageCount";

    public static final String isFromUpdateProduct = "isFromUpdateProduct";

    public static final String isSellAdvertService = "isSellAdvertService";
    public static final String isUpdateAdvertService = "isUpdateAdvertService";

    public static String EXTRA_OFFER_ID = "redirect_id";

    public static final String name = "name";

    public static String countryList = "countryList";
    public static String videoUrl = "videoUrl";

    public static String ARG_POSITION = "ARG_POSITION";

    // Content, ad, featured offer OR Loading - RecyclerView
    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    public static final int VIEW_TYPE_AD = 2;
    public static final int VIEW_TYPE_FEATURED = 3;
    public static final int VIEW_TYPE_HISTORY = 4;
    public static final int VIEW_TYPE_QUOTES = 5;
    public static final int VIEW_STORE_MALL = 6;

    // Calling Screen type for Login Result
    public static final int OFFER_DETAIL = 111;
    public static final int OFFER_STORE_INFO = 222;
    public static final int OFFER_MORE_INFO = 333;

    public static int VIEW_TYPE_SUB_CATEGORY_NORMAL = 0;
    public static int VIEW_TYPE_SUB_CATEGORY_FAV = 1;

    public static int VIDEO_MAX_LENGTH = 10;
    public static int AUDIO_MAX_LENGTH = 10;

    // API ProfileResponse check result
    public static final int TOTAL_FREE_IMAGES = 5;
    public static final int MAX_IMG_COUNT = 10;
    public static String isChatLoginInitiate = "isChatLoginInitiate";

    public interface PERMISSIONS_REQUEST_CODE {
        int REQUEST_CODE_STORAGE = 5;
        int REQUEST_CODE_STORAGE_CAMERA = 2;
        int REQUEST_CODE_PICK_IMAGE_FROM_GALLERY = 3;
        int REQUEST_CODE_PICK_IMAGE_FROM_CAMERA = 4;
    }

    // Request Code
    public static final int RC_SELECT_PICTURE = 101;
    public static final int RC_CAPTURE_PICTURE = 102;
    public static final int RC_RECORD_VIDEO = 103;
    public static final int RC_ENABLE_GPS = 104;
    public static final int RC_LOCATION = 105;
    public static final int RC_LOGIN_RESULT = 106;
    public static final int RC_OFFER_DETAIL = 107;
    public static final int RC_CROP_PICTURE = 108;
    public static final int RC_REQUEST_QUOTE = 109;
    public static final int RC_QUOTES_DETAIL = 110;

    // Common Fields
    public static final int FIELD_STORE = 1;
    public static final int FIELD_MALL = 2;
    public static final int FIELD_QUANTITY = 3;
    public static final int CB_TYPE_FAV = 1;
    public static final int CB_TYPE_WATCH = 2;
    public static final int CB_TYPE_STORE = 3;
    public static final int CB_TYPE_MALL = 4;
    public static final int CB_TYPE_LIKE = 5;

    public static final String CROP_VIDEO_URI = "cropping_video_uri";
    public interface DISPLAY_TYPE {
        int TYPE_ACTIVE = 1, TYPE_REC_CLOSED = 2, TYPE_CHAT = 5, TYPE_WATCH = 6, TYPE_POINTS = 7, TYPE_JOINED_GROUP = 8;
    }
    public static final int REQUEST_VIDEO_TRIMMER = 0x01;
    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";

    public static String ARG_IS_CALL_API = "ARG_IS_CALL_API";
    public static final String ARG_TAB_API_TAG = "ARG_TAB_API_TAG";
    public static final String ARG_FILTER_TYPE = "ARG_FILTER_TYPE";
    public static final String ARG_OFFER_ENDPOINT = "ARG_OFFER_ENDPOINT";
    public static String ARG_STORE_ID = "ARG_STORE_ID";
    public static String ARG_STORE_CHAT_ID = "ARG_STORE_CHAT_ID";

    public static String AD_TYPE_EXTERNAL = "external_link";
    public static String AD_TYPE_INTERNAL = "internal_link";

    public interface MEDIA_TYPE {
        int IMAGE_AND_VIDEO = 1;
        int IMAGE = 2;
        int VIDEO = 3;
        int AUDIO = 4;
    }

    public interface FILTER_API_TYPE{
        int CATEGORY_TYPE = 0;
        int FILTER_TYPE = 1;
        int LOCATION_TYPE = 2;
        int SORT_TYPE = 3;
        int MAP_TYPE = 4;
    }

    public interface SCREEN_API_TYPE {
        int HOME_TYPE = 0;
        int OOD_TYPE = 1;
        int FAVOURITE_OFFER = 2;
        int STORE_MALL_OFFER = 3;
        int QUOTE_OFFER = 4;
        int LOYALTY_OFFER_TYPE = 5;
        int WATCHLIST_OFFER = 6;
        int FEATURED_OFFER = 7;
        int HISTORY_OFFER = 8;
        int GLOBAL_SEARCH = 9;
    }

    public interface SEARCH_TYPE {
        String OFFER = "offers";
        String QUOTE = "quotes";
        String GRAB = "grab";
        String MALL = "malls";
        String STORE = "stores";
    }

    public interface SEARCH_TAG {
        String LOYALTY = "loyalty";
        String WATCHLIST = "watchlist";
        String FEATURED = "featured";
        String EMPTY = "";
    }

    public interface FILTER_TYPE {
        String OFFER = "offer";
        String MALL = "mall";
        String STORE = "store";
        String FAVOURITE = "favorite";
        String EMPTY = "";
    }

    public interface HEADER_TAB_FILTER {
        int CATEGORY = 0;
        int FILTER = 1;
        int LOCATION = 2;
        int SORT = 3;
        int MAP = 4;
    }

    public interface FILTER_VIEW_TYPE{
        String TypeTextBox = "textbox";
        String TypeCheckBox = "checkbox";
        String TypeDropDown = "radio";
        String TypeRadio = "dropdown";
        String TypeDate = "date";
        String TypeDateRange = "daterange";
        String TypeNumber = "number";
        String TypePercentage = "percentage";
    }

    public interface SCREEN_REDIRECT{
        String OFFER = "offers";
        String MALL = "malls";
        String STORE = "stores";
        String OFFER_TYPE = "offer_type";
        String REFERRAL = "referral";
        String MY_PROFILE = "my_profile";
        String MY_JOB_PROFILE = "my_job_profile";
        String QUOTE = "quotes";
        String OOD = "ood";
        String LOYALTY_OFFER = "loyalty_offer";
        String FEATURED_OFFER = "featured_offer";
    }

    public interface GRAB_TYPE {
        String WITH_CODE = "with_code";
        String WITH_QR_QUOTE = "with_qr_code";
        String CALL = "call";
        String VIEW = "view";
        String APPLY = "apply";
    }
}
