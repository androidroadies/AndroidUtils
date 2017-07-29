package com.common.chat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.common.activity.TrimmerActivity;
import com.common.adapter.AdapterChat;
import com.common.api.ApiFactory;
import com.common.api.ProgressRequestBody;
import com.common.api.RxApiCallHelper;
import com.common.api.RxApiCallback;
import com.common.api.responsemodel.FileUploadResponse;
import com.common.api.services.FileUpload;
import com.common.base.BaseConstants;
import com.common.base.BaseFragment;
import com.common.base.BaseFragmentChat;
import com.common.bottomsheet.SelectImageUsingBottomSheet;
import com.common.constant.Constant;
import com.common.fragment.Demo0Fragment;
import com.common.helper.ImagePicker;
import com.common.utilcode.util.FileUtils;
import com.common.utils.LogUtils;
import com.common.utils.NetworkUtils;
import com.common.utils.R;
import com.common.views.AnimButton;
import com.common.views.CustomAppCompatEditText;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.xmpp.library.database.DBChat;
import com.xmpp.library.model.ModelChat;
import com.xmpp.library.service.XMPPService;
import com.xmpp.library.utils.XmppConstants;
import com.xmpp.library.utils.XmppPreference;

import org.jivesoftware.smackx.chatstates.ChatState;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;

import static android.app.Activity.RESULT_OK;
import static com.common.constant.Constant.ARG_STORE_CHAT_ID;
import static com.common.constant.Constant.ARG_STORE_ID;
import static com.common.constant.Constant.EXTRA_VIDEO_PATH;
import static com.common.constant.Constant.MEDIA_TYPE.AUDIO;
import static com.common.constant.Constant.MEDIA_TYPE.IMAGE;
import static com.common.constant.Constant.MEDIA_TYPE.IMAGE_AND_VIDEO;
import static com.common.constant.Constant.MEDIA_TYPE.VIDEO;
import static com.common.constant.Constant.RC_RECORD_VIDEO;
import static com.common.constant.Constant.RC_SELECT_PICTURE;
import static com.common.constant.Constant.REQUEST_VIDEO_TRIMMER;
import static com.common.helper.ImagePicker.EXTRA_FILE;
import static com.common.utils.LogUtils.information;


public class GroupByAdminChatFragment extends BaseFragmentChat implements EasyPermissions.PermissionCallbacks, SelectImageUsingBottomSheet.OnSelectUsingListener {

    private static final String TAG = "GroupByAdminChatFragment";
    private static final int FROM_GALLERY = 1;
    private static final int FROM_CAMERA = 2;
    private static final int PLACE_PICKER_REQUEST = 999;
    private static final int REQUEST_RECORD_AUDIO = 2134;

    private final String MEDIA_FIL_PARAM_NAME = "media";

    public static String ARG_TITLE = "ARG_TITLE";
    public static String ARG_CONVERSATION_TYPE = "ARG_CONVERSATION_TYPE";
//    public static String ARG_CHAT_TO_ID = "ARG_CHAT_TO_ID";
//    public static String ARG_STORE_ID = "ARG_STORE_ID";

    @BindView(R.id.iv_group_chat_send)
    AnimButton imageSend;
    @BindView(R.id.edtSendMessage)
    CustomAppCompatEditText edtSendMessage;
    @BindView(R.id.recyclerViewChat)
    RecyclerView recyclerViewChat;
    MediaRecorder recorder = null;
    long startTimer;
    //    DashboardGroupListResponse.Datum mMainData;
    // Chat Related Values
    private IntentFilter intentFilter = null;
    private String chatType, storeId, oppositeChatId = "0", myChatId = "", chatValue, oppositeUsername = "";
    private AdapterChat mAdapterChat;
    private ArrayList<ModelChat> mChatArrayList = new ArrayList<>();
    private LocalBroadcastManager mLocalBroadcastManager;
    private DBChat mDBChat;
    private ChatState chatState = ChatState.gone;
    private ModelChat modelChat = null;
    Vibrator mVibrator; // vibrate phone while recording audio
    private String userId;
    // File Upload related Values
    private String[] audioPermissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] cameraGalleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private String[] cameraPermissions = {Manifest.permission.CAMERA};
    private int mRequestCode;
    private int mResultCode;
    private Intent mData;
    private int CAPTURE_MEDIA_TYPE = IMAGE;
    //Some things we only have to set the first time.
    private boolean firstTime = true;
    //For recording audio
    private Animation animBlink;
    private Handler handlerRecord = new Handler();
    private boolean isForRecording = false;

    private String dataInJson = "";
    private String mToChatId;
    private File mImageFile = null;
    private String CONVERSATION_TYPE = XmppConstants.CHAT.CONVERSATION_SINGLE;

    public static GroupByAdminChatFragment newInstance() {
        Bundle args = new Bundle();
        GroupByAdminChatFragment fragment = new GroupByAdminChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private BroadcastReceiver xmppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.LOGD(TAG, "onReceive() called with: action = [" + action + "]");
            switch (action) {
                case XmppConstants.ACTION.CONNECT:
                    LogUtils.LOGE(TAG, "Connection successfully");
                    //getContext().startService(XMPPService.getAuthenticationIntent(getContext(), String.valueOf(userId), XmppConstants.CONFIGURATION.XMPP_USER_PASSWORD));
                    break;
                case XmppConstants.ACTION.DISCONNECT:
                    XmppPreference.getInstance(getContext()).clear();
                    LogUtils.LOGE(TAG, "Connection disconnect!");
                    break;
                case XmppConstants.ACTION.AUTHENTICATE:
                    LogUtils.LOGE(TAG, "Authentication successfully");
                    //callAfterLogin();
                    XmppPreference.getInstance(getContext()).setUserId(userId);
                    XmppPreference.getInstance(getContext()).setPassword(XmppConstants.CONFIGURATION.XMPP_USER_PASSWORD);
                    myChatId = XmppPreference.getInstance(getContext()).getUserId();
                    initializeChat();
                    break;
                case XmppConstants.ACTION.AUTHENTICATE_FAILED:
                    LogUtils.LOGE(TAG, "Authentication failed!");
                    showAToast(getString(R.string.error_something_went_wrong));
                    getActivity().finish();
                    break;

            }
        }
    };

    private BroadcastReceiver xmppChatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.LOGD(TAG, "onReceive() called with: action = [" + action + "]");
            switch (action) {
                case XmppConstants.ACTION.UPDATE:
                    mChatArrayList.clear();
                    //one to one
                    mChatArrayList = mDBChat.getByConversationId(myChatId, oppositeChatId, CONVERSATION_TYPE);
                    //Group Chat
                    //mChatArrayList = mDBChat.getGroupHistory(myChatId, oppositeChatId, CONVERSATION_TYPE);
                    mAdapterChat.addItems(mChatArrayList);
                    if (mAdapterChat.getItemCount() > 1) {
                        recyclerViewChat.smoothScrollToPosition(mAdapterChat.getItemCount() - 1);
                    }
                    break;

                case XmppConstants.ACTION.INITIATE_CHAT:
                    LogUtils.LOGD("chat", "INITIATE_CHAT");
                    break;

                case XmppConstants.ACTION.SEND_MESSAGE:
                    LogUtils.LOGD("Message", "SENT");
                    mAdapterChat.addItem(modelChat);
                    recyclerViewChat.smoothScrollToPosition(mAdapterChat.getItemCount() - 1);
                    break;

                case XmppConstants.ACTION.RECEIVE_MESSAGE:
                    ModelChat modelChat = (ModelChat) intent.getSerializableExtra(XmppConstants.EXTRA.DATA);
                    modelChat.setUserName(getUserNameFromID(modelChat.getFromUser()));
                    mDBChat.updateUserName(modelChat);
                    mAdapterChat.addItem(modelChat);
                    recyclerViewChat.smoothScrollToPosition(mAdapterChat.getItemCount() - 1);
                    break;

                case XmppConstants.ACTION.TYPING:
                    ChatState chatState = ChatState.valueOf(intent.getStringExtra(XmppConstants.EXTRA.DATA));
                    String chatUser = intent.getStringExtra(XmppConstants.CHAT.CHAT_DATA);

                    switch (chatState) {
                        case active:
                            LogUtils.LOGD("state", "active");
                            break;
                        case composing:
                            LogUtils.LOGD("state", "composing");
                            if (chatUser.equals(oppositeChatId)) {
//                                ((MainActivity) getActivity()).getSupportActionBar().setTitle(oppositeUsername + " is typing...");
                            }
                            break;
                        case paused:
                            LogUtils.LOGD("state", "paused");
                            if (chatUser.equals(oppositeChatId)) {
//                                ((MainActivity) getActivity()).getSupportActionBar().setTitle(oppositeUsername);
                            }
                        case inactive:
                            LogUtils.LOGD("state", "inactive");
                        default:
                            LogUtils.LOGD("state", "gone");
                            if (chatUser.equals(oppositeChatId)) {
//                                ((MainActivity) getActivity()).getSupportActionBar().setTitle(oppositeUsername);
                            }

                            break;
                    }
                    break;
            }
        }
    };

    Runnable runnableRecord = new Runnable() {
        @Override
        public void run() {
            Log.e("Running", "Audio Record");
            long timeRecord = (System.currentTimeMillis() - startTimer);
            int minutes = (int) ((timeRecord / 1000) / 60);
            int seconds = (int) ((timeRecord / 1000) % 60);

            edtSendMessage.startAnimation(animBlink); // Start blink animation
            edtSendMessage.setText("Recording: " + String.format("%02d:%02d", minutes, seconds));
            //long[] pattern = {0, 100, 1000};

            handlerRecord.postDelayed(runnableRecord, 1000);
            if (timeRecord / 1000 == Constant.AUDIO_MAX_LENGTH + 1) { // Stop timer after Audio max length limit
                stopRecording();
                mVibrator.cancel();
            }
        }
    };

    public GroupByAdminChatFragment() {
    }

    public static GroupByAdminChatFragment newInstance(String conversationType, String toId, String storeId) {
        GroupByAdminChatFragment fragment = new GroupByAdminChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONVERSATION_TYPE, conversationType);
        args.putString(ARG_STORE_CHAT_ID, toId);
        args.putString(ARG_STORE_ID, storeId);

        fragment.setArguments(args);
        return fragment;
    }

    private String getUserNameFromID(String fromUser) {
        String userName = "";
//        for (DashboardGroupListResponse.NumberOfMembersList numberOfMembersList : mMainData.getNumberOfMembersList()) {
//
//            if (numberOfMembersList.getUserId().toString().equals(fromUser)) {
//                userName = numberOfMembersList.getFullName();
//                break;
//            }
//
//        }
        return userName;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_group_by_admin_private_chat;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dataInJson = getArguments().getString(Constant.EXTRA_DEALS_DATA);
//        mMainData = new Gson().fromJson(dataInJson, DashboardGroupListResponse.Datum.class);
        oppositeChatId = "pratik_shah";

        Bundle bundle = getArguments();
        if (bundle != null){
            oppositeChatId = bundle.getString(ARG_STORE_CHAT_ID);
            storeId = bundle.getString(ARG_STORE_ID);
            information(getClass(), "Opposite Chat Id : "+ oppositeChatId +" > StoreID :" + storeId);
        }

        /*Bundle bundle = getArguments();
        if (bundle != null){
            CONVERSATION_TYPE = bundle.getString(ARG_CONVERSATION_TYPE);
        }*/

        initVibrator();
        initXMPPConnection();

        animBlink = AnimationUtils.loadAnimation(getContext(),
                R.anim.blink_animation);
    }

    private void initVibrator() {
        mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //GROUP CHAT

        setTextWatcher();
        initializeComponents();
    }

    private void initXMPPConnection() {
        if (NetworkUtils.checkNetwork(getContext())) {
//            userId = SharedPreferenceUtils.getInstance(getActivity())
//                    .getLongValue(Constant.userId, 0);
//            LoginResponse.Data loginData = UserPreferenceUtils.getInstance(getContext()).getLoginClass();

            userId = "mayank_8cd";

            //if (!isAuthenticated()) {
            getContext().startService(XMPPService.getAuthenticationIntent(getContext(), userId, XmppConstants.CONFIGURATION.XMPP_USER_PASSWORD));
            /*} else {
                myChatId = XmppPreference.getInstance(getContext()).getUserId();
            }*/

            initializeChat();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        actionRegister();
        getContext().startService(XMPPService.getConnectIntent(getContext()));

    }

    private void actionRegister() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        // Connection Broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction(XmppConstants.ACTION.CONNECT);
        intentFilter.addAction(XmppConstants.ACTION.DISCONNECT);
        intentFilter.addAction(XmppConstants.ACTION.AUTHENTICATE);
        intentFilter.addAction(XmppConstants.ACTION.AUTHENTICATE_FAILED);
        mLocalBroadcastManager.registerReceiver(xmppReceiver, intentFilter);

        // Chat Broadcast
        intentFilter.addAction(XmppConstants.ACTION.SEND_MESSAGE);
        intentFilter.addAction(XmppConstants.ACTION.INITIATE_CHAT);
        intentFilter.addAction(XmppConstants.ACTION.RECEIVE_MESSAGE);
        intentFilter.addAction(XmppConstants.ACTION.TYPING);
        intentFilter.addAction(XmppConstants.ACTION.UPDATE);
        mLocalBroadcastManager.registerReceiver(xmppChatReceiver, intentFilter);

    }

    private void actionUnregister() {
        mLocalBroadcastManager.unregisterReceiver(xmppReceiver);
        mLocalBroadcastManager.unregisterReceiver(xmppChatReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        actionUnregister();
    }

    private void initializeChat() {
        getContext().startService(XMPPService.getIntializeChatIntent(getContext(),
                XmppConstants.getJabberId(oppositeChatId),
                XmppConstants.CHAT.CONVERSATION_SINGLE, userId));
        /*getContext().startService(XMPPService.getIntializeChatIntent(getContext(),
                oppositeChatId,
                XmppConstants.CHAT.CONVERSATION_GROUP, String.valueOf(userId)));*/
    }

    private void setTextWatcher() {
        edtSendMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!isForRecording) { // button state not changing if user want to upload Audio
                    imageSend.goToState(text.length() == 0 ? AnimButton.FIRST_STATE : AnimButton.SECOND_STATE);
                }

                if (edtSendMessage.getText().toString().length() == 0 && chatState != ChatState.inactive) {
                    chatState = ChatState.paused;
                    getContext().startService(XMPPService.getTypingIntent(getContext(), chatState));
                } else if (chatState != ChatState.composing) {
                    chatState = ChatState.composing;
                    getContext().startService(XMPPService.getTypingIntent(getContext(), chatState));
                }
            }
        });
        imageSend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (imageSend.getButtonImageState() == AnimButton.SECOND_STATE
                        && (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                        ) { // if button is send event is up and cancel
                    chatType = XmppConstants.CHAT_TYPE.CHAT_TEXT;
                    callSendMessage();
                    return true;
                } else {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && imageSend.getButtonImageState() == AnimButton.FIRST_STATE) {
                        chatType = XmppConstants.CHAT_TYPE.CHAT_AUDIO;
                        if (EasyPermissions.hasPermissions(getContext(), audioPermissions)) {
                            mImageFile = createAudioFile();
                            if (mImageFile != null) {
                                isForRecording = true;
                                startRecording(mImageFile);
                            }
                        } else {
                            EasyPermissions.requestPermissions(GroupByAdminChatFragment.this, "Access for MIC",
                                    REQUEST_RECORD_AUDIO, audioPermissions);
                        }

                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                            || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        Log.e("Recording", "Stop");
                        edtSendMessage.clearAnimation();
                        CAPTURE_MEDIA_TYPE = AUDIO;
                        stopRecording();
                    }
                    return true;
                }
            }
        });


        edtSendMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    callSendMessage();
                }
                return false;
            }
        });
    }

    private void callSendMessage() {
        if (TextUtils.isEmpty(edtSendMessage.getText().toString())) {
            return;
        }
        hideKeyBoard();
        chatType = XmppConstants.CHAT_TYPE.CHAT_TEXT;
        //chatValue = edtSendMessage.getText().toString().trim();
        sendMessage(edtSendMessage.getText().toString().trim(), "");
        edtSendMessage.setText("");
    }

    @OnClick({R.id.iv_group_chat_send, R.id.iv_select_attachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_group_chat_send:
                callSendMessage();
                break;
            case R.id.iv_select_attachment:
                checkStoragePermission();
                break;
        }
    }

    /**
     * Attachment bottom dialog for showing different attachment options : Image, Video & Location
     */
    private void showAttachmentBottomSheet() {
        SelectImageUsingBottomSheet selectImageUsingBottomSheet = new SelectImageUsingBottomSheet();
        selectImageUsingBottomSheet.setmOnSelectUsingListener(this);
        showDialogFragment(selectImageUsingBottomSheet);
    }

    /**
     * As per user selection open Media and location sharing option
     *
     * @param buttonId : clicked button ID
     */
    private void performButtonClick(int buttonId) {
        switch (buttonId) {
            /*case R.id.imageButtonCamera:
                checkForPermission(FROM_CAMERA);
                break;
            case R.id.imageButtonAudio:
                checkForPermission(FROM_GALLERY);
                break;
            case R.id.imageButtonVideo:
                checkForPermission(FROM_GALLERY);
                break;
            case R.id.imageButtonLocation:
                break;
            case R.id.imageButtonGallery:
                checkForPermission(FROM_GALLERY);
                break;*/
            case R.id.tvTakePicture:
                chatType = XmppConstants.CHAT_TYPE.CHAT_IMAGE;
                CAPTURE_MEDIA_TYPE = IMAGE; // Capture Image
                checkForPermission(FROM_CAMERA);
                break;
            case R.id.tvTakeVideo:
                chatType = XmppConstants.CHAT_TYPE.CHAT_VIDEO;
                CAPTURE_MEDIA_TYPE = VIDEO; // Capture Video
                checkForPermission(FROM_CAMERA);
                break;
            case R.id.tvAudio:
                chatType = XmppConstants.CHAT_TYPE.CHAT_AUDIO;
                CAPTURE_MEDIA_TYPE = AUDIO; // Select Audio
                checkForPermission(FROM_GALLERY);
                break;
            case R.id.tvLocation:
                chatType = XmppConstants.CHAT_TYPE.CHAT_LOCATION;
                //Select Location
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvChooseFromGallery:
                CAPTURE_MEDIA_TYPE = IMAGE_AND_VIDEO; // SELECT ONLY IMAGE AND VIDEO FROM GALLERY
                checkForPermission(FROM_GALLERY);
                break;
        }
    }

    private void sendMessage(String chatValue, String extraValue) {
//        LoginResponse.Data loginData = UserPreferenceUtils.getInstance(getContext()).getLoginClass();
        try {
            modelChat = new ModelChat();
            modelChat.setConversationId(XmppConstants.getConversionId(myChatId, oppositeChatId));
            //modelChat.setConversationId( oppositeChatId);
            modelChat.setConversationType(CONVERSATION_TYPE);
            modelChat.setToUser(oppositeChatId);
            modelChat.setFromUser(myChatId);
            modelChat.setTime(System.currentTimeMillis());
            modelChat.setType(chatType);
            modelChat.setValue(chatValue);
            modelChat.setSender(true);
            modelChat.setStoreID(storeId);
            modelChat.setLatlong(extraValue);
            modelChat.setUserName("Mayank"); //
            modelChat.setStatus(XmppConstants.CHAT.STATUS_PENDING);
            sendToXmpp(modelChat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(FileUploadResponse fileUploadResponse) {
//        LoginResponse.Data loginData = UserPreferenceUtils.getInstance(getContext()).getLoginClass();
        try {
            modelChat = new ModelChat();
            modelChat.setConversationId(XmppConstants.getConversionId(myChatId, oppositeChatId));
//            modelChat.setConversationId(XmppConstants.getGroupID(oppositeChatId));
            //modelChat.setConversationId( oppositeChatId);
            modelChat.setConversationType(CONVERSATION_TYPE);
            modelChat.setToUser(oppositeChatId);
            modelChat.setFromUser(myChatId);
            modelChat.setTime(System.currentTimeMillis());
            modelChat.setType(chatType);
            modelChat.setValue(fileUploadResponse.getChatMedia());
            modelChat.setSender(true);
            modelChat.setStoreID(storeId);
            modelChat.setUserName("Mayank"); //
            modelChat.setThumb(fileUploadResponse.getChatMediaThumb());
            modelChat.setStatus(XmppConstants.CHAT.STATUS_PENDING);
            sendToXmpp(modelChat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToXmpp(ModelChat modelChat) {
        getContext().startService(XMPPService.getMessageIntent(getContext(), modelChat));
    }

    private void initializeComponents() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);

        if (!TextUtils.isEmpty(XmppPreference.getInstance(getContext()).getUserId())) {
            myChatId = XmppPreference.getInstance(getContext()).getUserId();
        }

        mDBChat = new DBChat(getContext());
        //one to one
        mChatArrayList = mDBChat.getByConversationId(myChatId, oppositeChatId, CONVERSATION_TYPE);
        //Group Chat
        // mChatArrayList = mDBChat.getGroupHistory(myChatId, oppositeChatId, CONVERSATION_TYPE);

        mAdapterChat = new AdapterChat(getContext(), mChatArrayList);
        recyclerViewChat.setAdapter(mAdapterChat);

        //imageSend.setEnabled(false);
        //imageSend.setClickable(false);
    }

    /**
     * check permission for taking picture from gallery or camera
     *
     * @param isForGalleryOrCamera pass 1 for gallery and 2 for camera
     */
    private void checkForPermission(int isForGalleryOrCamera) {
        if (isForGalleryOrCamera == FROM_GALLERY) {

            if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                pickImageFromGallery();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.rationale_message_storage),
                        Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_STORAGE, galleryPermissions);
            }
        } else {
            if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)
                    && EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
                captureImage();
            } else {
                if (!EasyPermissions.hasPermissions(getContext(), galleryPermissions)
                        && !EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
                    EasyPermissions.requestPermissions(this, getString(R.string.rationale_message_storage_camera),
                            Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_STORAGE_CAMERA, cameraGalleryPermissions);
                } else if (!EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                    EasyPermissions.requestPermissions(this, getString(R.string.rationale_message_storage_camera),
                            Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_STORAGE_CAMERA, galleryPermissions);
                } else if (!EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
                    EasyPermissions.requestPermissions(this, getString(R.string.rationale_message_storage_camera),
                            Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_STORAGE_CAMERA, cameraPermissions);
                }
            }
        }
    }

    private void pickImageFromGallery() {
        Intent intent;
        switch (CAPTURE_MEDIA_TYPE) {
            case IMAGE_AND_VIDEO:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(getString(R.string.str_type_image_and_video));
                break;
            case IMAGE:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(getString(R.string.str_type_image));
                break;
            case VIDEO:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(getString(R.string.str_type_video));
                break;
            case AUDIO:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType(getString(R.string.str_type_audio));
                break;
            default:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(getString(R.string.str_type_image_and_video));
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_PICK_IMAGE_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mImageFile = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );
        return mImageFile;*/
        String FILE_EXT = CAPTURE_MEDIA_TYPE == IMAGE ? ".jpeg" : ".mp4";
        String FILE_PREFIX = CAPTURE_MEDIA_TYPE == IMAGE ? "IMAGE_" : "VIDEO_";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = FILE_PREFIX + timeStamp;
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        mImageFile = File.createTempFile(
                imageFileName,   //prefix
                FILE_EXT,          //suffix
                storageDir       //directory
        );
        //File.createTempFile()
        return mImageFile;
    }

    /*private void captureImage() {

        Intent takePictureIntent;
        switch (CAPTURE_MEDIA_TYPE) {
            case Constant.MEDIA_TYPE.IMAGE:
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
            case Constant.MEDIA_TYPE.VIDEO:
                takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                takePictureIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, Constant.VIDEO_DURATION);
                break;
            default:
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        getActivity().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_PICK_IMAGE_FROM_CAMERA);
            }
        }
    }*/

    /*private void initSelectedImage(int requestCode, int resultCode, Intent data) {
        final MediaType MEDIA_TYPE_VIDEO = MediaType.parse("video/mp4");
        final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/png");
        if (requestCode == Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_PICK_IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {

            mImageFile = FileUtils.getFile(getContext(), data.getData());
            if (CAPTURE_MEDIA_TYPE == AUDIO) // If user want to upload audio
            {
                chatType = XmppConstants.CHAT_TYPE.CHAT_AUDIO;
                callMediaUploadFile();
            } else {
                // IF user select image from gallery then crop image
                if (!isVideoFile(mImageFile.getAbsolutePath())) {
                    CropImage.activity(Uri.fromFile(mImageFile))
                            .setAllowFlipping(false)
                            .setAllowRotation(false)
                            .setAspectRatio(1, 2)
                            .setAutoZoomEnabled(true)
                            .start(getContext(), this);
                } else {
                    startVideoCroppingActivity(mImageFile.getAbsolutePath());
                }
            }
        } else if (requestCode == Constant.PERMISSIONS_REQUEST_CODE.REQUEST_CODE_PICK_IMAGE_FROM_CAMERA && resultCode == RESULT_OK) {
            // IF user Capture image then crop image
            if (CAPTURE_MEDIA_TYPE == Constant.MEDIA_TYPE.IMAGE) {
                CropImage.activity(Uri.fromFile(mImageFile))
                        .setAllowFlipping(false)
                        .setAllowRotation(false)
                        .setAutoZoomEnabled(true)
                        .setAspectRatio(1, 2)
                        .start(getContext(), this);
            } else {
                if (CAPTURE_MEDIA_TYPE == Constant.MEDIA_TYPE.VIDEO) {
                    startVideoCroppingActivity(mImageFile.getAbsolutePath());
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //After cropping image
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                chatType = XmppConstants.CHAT_TYPE.CHAT_IMAGE;
                Uri resultUri = result.getUri();
                mImageFile = FileUtils.getFile(getContext(), resultUri);
                callMediaUploadFile();
            }

        } else if (requestCode == REQUEST_VIDEO_TRIMMER) {

            mImageFile = FileUtils.getFile(getContext(), Uri.parse(data.getStringExtra(CROP_VIDEO_URI)));

            if (mImageFile != null) {
                String fileName = String.valueOf(System.currentTimeMillis());
                //if (CAPTURE_MEDIA_TYPE == Constant.MEDIA_TYPE.VIDEO) { // Check for video
                Bitmap mVideoBitmap = ThumbnailUtils.createVideoThumbnail(mImageFile.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                // Create video thumbnail and send as image in API as as video file name
                //requestBody = RequestBody.create(MEDIA_TYPE_VIDEO, createFileFromBitmap(mVideoBitmap, mImageFile.getName()));
                //MultipartBody.Part partVideoImg = MultipartBody.Part.createFormData("video[]", fileName + ".jpeg", requestBody);

                //requestBody = RequestBody.create(MEDIA_TYPE_VIDEO, mImageFile);
                //MultipartBody.Part partVideo = MultipartBody.Part.createFormData("video[]", fileName + ".mp4", requestBody);
                chatType = XmppConstants.CHAT_TYPE.CHAT_VIDEO;

                ProgressRequestBody progressRequestBodyThumb = new ProgressRequestBody(createFileFromBitmap(mVideoBitmap, mImageFile.getName()), new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int total, int percentage) {
                        LogUtils.LOGE("PERCENTAGE->", String.valueOf(percentage));

                        if (firstTime) {
                            firstTime = false;
                        }

                    }

                    @Override
                    public void onError() {
                        firstTime = true;
                    }

                    @Override
                    public void onFinish() {
                        firstTime = true;
                    }
                }, "image*//*");
                MultipartBody.Part partVideoImg = MultipartBody.Part.createFormData("video[]", fileName + ".jpeg", progressRequestBodyThumb);

                ProgressRequestBody progressRequestBodyVideo = new ProgressRequestBody(mImageFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int total, int percentage) {
                        LogUtils.LOGE("PERCENTAGE->", String.valueOf(percentage));
                        if (percentage == 0) {

                        }

                    }

                    @Override
                    public void onError() {
                    }

                    @Override
                    public void onFinish() {
                    }
                }, "video*//*");
                MultipartBody.Part partVideo = MultipartBody.Part.createFormData("video[]", fileName + ".mp4", progressRequestBodyVideo);
                callMediaUploadFile(partVideoImg, partVideo);
            }
        }

        // reset
        mResultCode = 0;
        mResultCode = Activity.RESULT_CANCELED;
        mData = null;
    }*/

    private void startVideoCroppingActivity(String absolutePath) {
        Intent intent = new Intent(getContext(), TrimmerActivity.class);
        intent.putExtra(EXTRA_VIDEO_PATH, absolutePath);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_VIDEO_TRIMMER);
    }

    public boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    private File createFileFromBitmap(Bitmap mBitmap, String fileName) {
        File f = new File(getContext().getCacheDir(), fileName);
        try {
            f.createNewFile();
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * As per user selection Upload Medial
     */
    private void callMediaUploadFile() {
        showProgressDialog();
        FileUpload fileUpload = ApiFactory.createService(FileUpload.class);

        String fileType;
        /*if (CAPTURE_MEDIA_TYPE == AUDIO) {
            fileType = "audio";
        } else {
            fileType = "image";
        }*/

        //fileType = "media";

        ProgressRequestBody progressRequestBodyImage = new ProgressRequestBody(mImageFile, new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int total, int percentage) {
            }

            @Override
            public void onError() {
            }

            @Override
            public void onFinish() {
            }
        }, "image/*");

        MultipartBody.Part mediaFile = MultipartBody.Part.createFormData(MEDIA_FIL_PARAM_NAME,
                mImageFile.getName(), progressRequestBodyImage);
        Observable<FileUploadResponse> observable = fileUpload.fileUpload(mediaFile);
        RxApiCallHelper.call(observable, new RxApiCallback<FileUploadResponse>() {
            @Override
            public void onSuccess(FileUploadResponse fileUploadResponse) {
                hideProgressDialog();
                // Store Media File URL in Chat Db and update chat
                sendMessage(fileUploadResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                hideProgressDialog();
                showToast(errorMsg);
            }
        });
    }

    /**
     * For Video upload with video and thumbnail
     *
     * @param partVideo    : video file
     * @param partVideoImg : thumb File
     */
    private void callMediaUploadFile(MultipartBody.Part partVideo, MultipartBody.Part partVideoImg) {
        showProgressDialog();
        FileUpload fileUpload = ApiFactory.createService(FileUpload.class);
        Observable<FileUploadResponse> observable = fileUpload.fileVideoUpload(partVideo, partVideoImg);
        RxApiCallHelper.call(observable, new RxApiCallback<FileUploadResponse>() {
            @Override
            public void onSuccess(FileUploadResponse fileUploadResponse) {
                hideProgressDialog();
                // Store Media File URL in Chat Db and update chat
                sendMessage(fileUploadResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                showToast(errorMsg);
            }

//            @Override
//            public void onFailed(Throwable throwable) {
//                hideProgressDialog();
//                showToast(throwable.getLocalizedMessage());
//            }
        });
    }

    private void checkStoragePermission() {
        if (EasyPermissions.hasPermissions(getContext(), cameraGalleryPermissions)) {
            // Already have permission, do the thing
            showAttachmentBottomSheet();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage), BaseConstants.REQUEST_CODE_PERMISSION_STORAGE, cameraGalleryPermissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == cameraGalleryPermissions.length) {
            showAttachmentBottomSheet();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_never_ask_storage))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.title_settings_button_setting))
                    .setNegativeButton(getString(R.string.title_settings_button_cancel), null /* click listener */)
                    .setRequestCode(BaseConstants.REQUEST_CODE_SETTINGS_PERMISSION_STORAGE)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SELECT_PICTURE: {
                if (resultCode == RESULT_OK) {
                    imageSelected(data);
                }
            }
            break;

            case RC_RECORD_VIDEO: {
                if (resultCode == RESULT_OK) {
                    startVideoCroppingActivity(mImageFile.getAbsolutePath());
                }
            }
            break;

            case REQUEST_VIDEO_TRIMMER: {
                if (resultCode == RESULT_OK) {
                    // upload video file to server
                    chatType = XmppConstants.CHAT_TYPE.CHAT_VIDEO;
                    uploadVideo();
                }
            }
            break;

            case com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();

                    mImageFile = new File(resultUri.getPath());
                    chatType = XmppConstants.CHAT_TYPE.CHAT_IMAGE;
                    callMediaUploadFile();
                    //mFileImageList.add(imageFile);
                    //mPhotosRecyclerAdapter.addItem(imageFile);
                } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    if (result != null) {
                        Exception error = result.getError();
                        showAToast(error.getMessage());
                    } else {
                        showAToast(getString(R.string.error_something_went_wrong));
                    }
                }
            }
            break;
        }
    }

    private void uploadVideo() {

        /*MediaRecorder mediaRecorder = new MediaRecorder();

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncodingBitRate(690000);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setVideoSize(640, 480);*/

        if (mImageFile != null) {
            String fileName = String.valueOf(System.currentTimeMillis());
            //if (CAPTURE_MEDIA_TYPE == Constant.MEDIA_TYPE.VIDEO) { // Check for video
            Bitmap mVideoBitmap = ThumbnailUtils.createVideoThumbnail(mImageFile.getAbsolutePath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            // Create video thumbnail and send as image in API as as video file name
            //requestBody = RequestBody.create(MEDIA_TYPE_VIDEO, createFileFromBitmap(mVideoBitmap, mImageFile.getName()));
            //MultipartBody.Part partVideoImg = MultipartBody.Part.createFormData("video[]", fileName + ".jpeg", requestBody);

            //requestBody = RequestBody.create(MEDIA_TYPE_VIDEO, mImageFile);
            //MultipartBody.Part partVideo = MultipartBody.Part.createFormData("video[]", fileName + ".mp4", requestBody);
            chatType = XmppConstants.CHAT_TYPE.CHAT_VIDEO;

            ProgressRequestBody progressRequestBodyThumb = new ProgressRequestBody(createFileFromBitmap(mVideoBitmap, mImageFile.getName()), new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(int total, int percentage) {
                    LogUtils.LOGE("PERCENTAGE->", String.valueOf(percentage));

                }

                @Override
                public void onError() {
                    firstTime = true;
                }

                @Override
                public void onFinish() {
                    firstTime = true;
                }
            }, "image/*");
            MultipartBody.Part partVideoImg = MultipartBody.Part.createFormData("video[]", fileName + ".jpeg", progressRequestBodyThumb);

            ProgressRequestBody progressRequestBodyVideo = new ProgressRequestBody(mImageFile, new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(int total, int percentage) {
                    LogUtils.LOGE("PERCENTAGE->", String.valueOf(percentage));
                }

                @Override
                public void onError() {
                }

                @Override
                public void onFinish() {
                }
            }, "video/*");
            MultipartBody.Part partVideo = MultipartBody.Part.createFormData(MEDIA_FIL_PARAM_NAME,
                    fileName + ".mp4", progressRequestBodyVideo);
            callMediaUploadFile(partVideoImg, partVideo);
        }
    }

    /**
     * Image got selected either from camera or gallery
     *
     * @param data intent data
     */
    private void imageSelected(Intent data) {
        Uri selectImageUri;
        if (data != null) { // GALLERY
            mImageFile = com.common.utils.FileUtils.getFile(getContext(), data.getData());
            selectImageUri = data.getData();
        } else { // CAMERA
            selectImageUri = ImagePicker.getFileProviderUri(getContext(), mImageFile);
        }

        // Crop Selected Image
        com.theartofdev.edmodo.cropper.CropImage.activity(selectImageUri)
                .setAllowFlipping(false)
                .setMaxCropResultSize(5000, 5000)
                .start(getContext(), this);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            Place place = PlacePicker.getPlace(getActivity(), data);
            String snapLocation = "https://maps.googleapis.com/maps/api/staticmap?";
            snapLocation += "&zoom=13";
            snapLocation += "&size=600x300";
            snapLocation += "&maptype=roadmap";
            snapLocation += "&markers=color:red%7C" + place.getLatLng().latitude + ", " + place.getLatLng().longitude;
            snapLocation += "&key=" + getString(R.string.api_google_key);

            sendMessage(snapLocation, place.getLatLng().latitude + "," + place.getLatLng().longitude);
        }
        else {
            //checkPermissionOnActivityResult(requestCode, resultCode, data);
        }
    }*/

    @Override
    public void onCameraClick() {
        captureImage();
    }

    @Override
    public void onGalleryClick() {
        pickImage();
    }

    @Override
    public void onRecordVideoClick() {
        captureVideo();
    }

    @Override
    public void onLocationClick() {

    }

    private void pickImage() {
        Intent intent = ImagePicker.getImageIntent(getContext());
        if (intent == null) {
            showAToast(getString(R.string.error_something_went_wrong));
            return;
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, RC_SELECT_PICTURE);
    }

    private void captureVideo() {
        Intent intent = ImagePicker.getVideoIntent(getContext());
        if (intent == null) {
            showAToast(getString(R.string.error_something_went_wrong));
            return;
        }
        mImageFile = (File) intent.getExtras().get(EXTRA_FILE);
        startActivityForResult(intent, RC_RECORD_VIDEO);
    }

    private void captureImage() {
        Intent takePictureIntent = ImagePicker.getCaptureIntent(getContext());
        if (takePictureIntent == null) {
            showAToast(getString(R.string.error_something_went_wrong));
            return;
        }
        mImageFile = (File) takePictureIntent.getExtras().get(EXTRA_FILE);
        startActivityForResult(takePictureIntent, RC_SELECT_PICTURE);
    }

    /**
     * Create Audio file for recoding storage
     *
     * @return
     * @throws IOException
     */
    private File createAudioFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "audio_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        try {
            return File.createTempFile(
                    imageFileName,  /* prefix */
                    ".mp3",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void startRecording(File file) {

        if (recorder != null) {
            recorder.release();
        }

        startTimer = System.currentTimeMillis();
        handlerRecord.postDelayed(runnableRecord, 1000);
        mVibrator.vibrate(400);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(file.getAbsolutePath());
        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                LogUtils.LOGE("RECORD->", String.valueOf(what));
                if (what == Constant.AUDIO_MAX_LENGTH + 1) {
                    edtSendMessage.clearAnimation();
                    CAPTURE_MEDIA_TYPE = AUDIO;
                    stopRecording();
                }
            }
        });
        try {
            recorder.setMaxDuration(Constant.AUDIO_MAX_LENGTH * 1000);
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("giftlist", "io problems while preparing [" +
                    file.getAbsolutePath() + "]: " + e.getMessage());
        }


    }


    private void stopRecording() {
        mVibrator.cancel();
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
            } catch (RuntimeException stopException) {
                //handle cleanup here
            }

            if (handlerRecord != null && runnableRecord != null) {
                handlerRecord.removeCallbacks(runnableRecord);
            }

            edtSendMessage.setText("");
            isForRecording = false;
            long timeRecord = (System.currentTimeMillis() - startTimer);
            int seconds = (int) ((timeRecord / 1000) % 60);

            if (seconds > 1) {
                callMediaUploadFile();
            } /*else {
                showToast("Recording is not started!");
            }*/
        }

    }
}
