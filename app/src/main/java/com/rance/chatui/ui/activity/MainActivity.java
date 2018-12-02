package com.rance.chatui.ui.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.androidadvance.topsnackbar.TSnackbar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.rance.chatui.R;
import com.rance.chatui.adapter.ChatAdapter;
import com.rance.chatui.adapter.CommonFragmentPagerAdapter;
import com.rance.chatui.bean.CommonResponse;
import com.rance.chatui.bean.HistoryBean;
import com.rance.chatui.bean.HistoryMsgBean;
import com.rance.chatui.bean.IMBean;
import com.rance.chatui.bean.LoginBean;
import com.rance.chatui.enity.FullImageInfo;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.ui.fragment.ChatEmotionFragment;
import com.rance.chatui.ui.fragment.ChatFunctionFragment;
import com.rance.chatui.util.Constants;
import com.rance.chatui.util.GlobalOnItemClickManagerUtils;
import com.rance.chatui.util.MediaManager;
import com.rance.chatui.widget.EmotionInputDetector;
import com.rance.chatui.widget.NoScrollViewPager;
import com.rance.chatui.widget.StateButton;
import com.aking.websocket.AbsWebSocketActivity;
import com.aking.websocket.ErrorResponse;
import com.aking.websocket.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**  remove(int position) 单独删除其中的一条数据
 * 主聊天界面
 */
public class MainActivity extends AbsWebSocketActivity {
    private CountDownTimer countDownTimer;//15秒轮询
    private CountDownTimer downTimer;//3秒轮询
    private static final String TAG = "AppResponseDispatcher";
    private Unbinder unbinder;
    @BindView(R.id.chat_list)
    EasyRecyclerView chatList;
//    @BindView(R.id.emotion_voice)
//    ImageView emotionVoice;
    @BindView(R.id.edit_text)
    EditText editText;//输入框,获取发送的内容
    @BindView(R.id.voice_text)
    TextView voiceText;
    @BindView(R.id.emotion_button)//表情按钮
            ImageView emotionButton;
    @BindView(R.id.emotion_add)
    ImageView emotionAdd;
    @BindView(R.id.emotion_send)
    StateButton emotionSend;//发送按钮
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.emotion_layout)
    RelativeLayout emotionLayout;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //进度条
        initWidget();
        initDownTimer();

    }


    private void initWidget() {
        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatList)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionButton)
                .bindToAddButton(emotionAdd)
                .bindToSendButton(emotionSend)
                .bindCoordinator(coordinator)
//                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .bindToConnectManager(mConnectManager)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
        chatList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
//        LoadData();
    }

    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position) {
            Toast.makeText(MainActivity.this, "onHeaderClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onImageClick(View view, int position) {
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            FullImageInfo fullImageInfo = new FullImageInfo();
            fullImageInfo.setLocationX(location[0]);
            fullImageInfo.setLocationY(location[1]);
            fullImageInfo.setWidth(view.getWidth());
            fullImageInfo.setHeight(view.getHeight());
            fullImageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
            EventBus.getDefault().postSticky(fullImageInfo);
            startActivity(new Intent(MainActivity.this, FullImageActivity.class));
            overridePendingTransition(0, 0);
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.mipmap.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.mipmap.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animView.setImageResource(res);
                }
            });
        }
    };

    /**  同时判断是否是左边布局还是右边布局,根据返回的Uer
     * 构造聊天数据
     * @param
     */
    private void LoadData(List<HistoryBean> beans) {
        messageInfos = new ArrayList<>();
        if (beans==null && beans.size()==0){
            return;
        }
        for (int i = 0; i < beans.size(); i++) {
            HistoryBean historyBean = beans.get(i);
            String user_id = historyBean.getUser().getUser_id();


            if("957144".equals(user_id)){

                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setContent(historyBean);
                messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                //设置头像
                messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
                messageInfos.add(messageInfo);

            }else{

                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setContent(historyBean);
                messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
                messageInfos.add(messageInfo);
            }

        }
        chatAdapter.addAll(messageInfos);
    }
//显示发送的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        //右边的消息
        messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getCount() - 1);
        new Handler().postDelayed(new Runnable() {//这里改写,如果没有返回错误码,则发送成功!
            public void run() {
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                chatAdapter.notifyDataSetChanged();
            }
        }, 1000);

    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
    }

    /**
     * 请求成功的回调
     *  禁止发言  恢复发言 和 删除的处理bean
     * @param message
     */
    @Override
    public void onMessageResponse(String message) {
        if (message != null && message.contains("getHistory")) {
            HistoryMsgBean msgBean = JSON.parseObject(message, new TypeReference<HistoryMsgBean>() {});
            LoadData(msgBean.getHistory());//加载数据
        } else if (message != null && message.contains("login")) {
            LoginBean bean = JSON.parseObject(message, new TypeReference<LoginBean>() {});
           //不知道要怎么处理这个
        }else if(message != null && message.contains("message")){
            HistoryBean imBean = JSON.parseObject(message, new TypeReference<HistoryBean>() {});
            LoadRightData(imBean);
        }
    }
    //实时更新数据
    private void LoadRightData(final HistoryBean imBean) {
        //把数据添加到adapter中
      final MessageInfo messageInfo = new MessageInfo();
       messageInfo.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        messageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(messageInfo);
        chatAdapter.add(messageInfo);
        chatList.scrollToPosition(chatAdapter.getCount() - 1);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                messageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                chatAdapter.notifyDataSetChanged();
            }
        }, 2000);

        //更新数据
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MessageInfo message = new MessageInfo();
                message.setContent(imBean);
                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                message.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
                messageInfos.add(message);
                chatAdapter.add(message);
                chatList.scrollToPosition(chatAdapter.getCount() - 1);
            }
        }, 3000);
    }

    /**
     * 请求失败的回调
     *
     * @param error
     */
    @Override
    public void onSendMessageError(ErrorResponse error) {
        Toast.makeText(this, error.getData(), Toast.LENGTH_SHORT).show();
    }


    private void initDownTimer() {
        countDownTimer = new CountDownTimer(15 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                sendMindJumpBag();
            }
        };
        countDownTimer.start();

        downTimer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                initLoginChatRoom();
                getHistory();
            }
        };
        downTimer.start();

    }

    //发送心跳包
    private void sendMindJumpBag() {
        JSONObject params = new JSONObject();
        params.put("cmd", "ping");
        sendText(params.toJSONString());
        countDownTimer.cancel();
        countDownTimer.start();
    }


    //重新登录
    private void initLoginChatRoom() {
        JSONObject params = new JSONObject();
        //第一次发送
        params.put("cmd", "login");
        params.put("token", "NGhLdTg3eDA3ZnVNQklEQi9SWFExK3czS1c1Zll1VlY4djkxTWtJZmFUST06OtSmsMsizFsgVNbQ/Jlt1l0=");
        params.put("rank", 0);
        sendText(params.toJSONString());
        Log.e(TAG, "------------" + params.toJSONString());
        //第二次发送
        params.clear();
        //{"cmd":"getHistory"}

    }

    public void getHistory() {
        JSONObject params = new JSONObject();
        params.put("cmd", "getHistory");
        sendText(params.toJSONString());
        Log.e(TAG, "------------" + params.toJSONString());
        downTimer.cancel();
    }

    @Override
    public void onServiceBindSuccess() {
        super.onServiceBindSuccess();
    }

    @Override
    public void onConnected() {
        super.onConnected();
        initLoginChatRoom();
        downTimer.cancel();
        downTimer.start();
    }

    /**
     * WebSocket 连接断开事件
     */
    @Override
    public void onDisconnected() {
        super.onDisconnected();
        TSnackbar.make(coordinator, "我是普通的Snackbar", TSnackbar.LENGTH_SHORT).show();
        downTimer.cancel();
        downTimer.start();
    }

    /**
     * WebSocket 连接出错事件
     * //重试也要几秒钟
     *
     * @param cause 出错原因
     */
    @Override
    public void onConnectError(Throwable cause) {
        super.onConnectError(cause);
        downTimer.cancel();
        downTimer.start();
    }
}
