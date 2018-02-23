package com.orange.orangestory;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orange.tabpages.FindFragment;
import com.orange.tabpages.HomeFragment;
import com.orange.tabpages.PlayFragment;

public class MainActivity extends AppCompatActivity {

    RadioGroup mainTabRadioGroup;

    //TAB页签图标宽度
    public static final int TAB_BUTTON_WIDTH = 100;
    //TAB's fragments
    //Home
    HomeFragment homeFragment;
    //Play
    PlayFragment playFragment;
    //find
    FindFragment findFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        initView();
        //初始化内容界面
        initPage();
    }

    //int
    private void init(){
        //init image loader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
    }

    //设置TAB页 图片大小和位置
    private void initView(){
        //home
        Drawable drawable_home = getResources().getDrawable(R.drawable.main_tab_home_selector);
        drawable_home.setBounds(0,0,TAB_BUTTON_WIDTH,TAB_BUTTON_WIDTH);

        RadioButton homeRadioButton = findViewById(R.id.tab_home);
        homeRadioButton.setCompoundDrawables(null,drawable_home,null,null);

        //play
        Drawable drawable_play = getResources().getDrawable(R.drawable.main_tab_play_selector);
        drawable_play.setBounds(0,0,TAB_BUTTON_WIDTH,TAB_BUTTON_WIDTH);

        RadioButton playRadioButton = findViewById(R.id.tab_play);
        playRadioButton.setCompoundDrawables(null,drawable_play,null,null);

        //find
        Drawable drawable_find = getResources().getDrawable(R.drawable.main_tab_find_selector);
        drawable_find.setBounds(0,0,TAB_BUTTON_WIDTH,TAB_BUTTON_WIDTH);

        final RadioButton findRadioButton = findViewById(R.id.tab_find);
        findRadioButton.setCompoundDrawables(null,drawable_find,null,null);

        //user
        Drawable drawable_user = getResources().getDrawable(R.drawable.main_tab_find_selector);
        drawable_user.setBounds(0,0,TAB_BUTTON_WIDTH,TAB_BUTTON_WIDTH);

        RadioButton userRadioButton = findViewById(R.id.tab_user);
        userRadioButton.setCompoundDrawables(null,drawable_user,null,null);

        //设置TAB页签点击监听
        mainTabRadioGroup = findViewById(R.id.tab_radio_group);
        mainTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page_content,homeFragment).commit();
                        break;
                    case R.id.tab_play:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page_content,playFragment).commit();
                        break;
                    case R.id.tab_find:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page_content,findFragment).commit();
                        break;
                    case R.id.tab_user:

                        break;
                }

            }
        });
    }

    //初始化HOME页为首页
    private void initPage(){
        //home
        homeFragment = HomeFragment.newInstance(null,null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_page_content,homeFragment).commit();

        //play
        playFragment = PlayFragment.newInstance(null,null);
        //find
        findFragment = FindFragment.newInstance(null,null);

    }
}
