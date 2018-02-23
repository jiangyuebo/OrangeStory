package com.orange.tabpages;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.orange.bean.BannerBean;
import com.orange.bean.MainPageDataJsonBean;
import com.orange.bean.SeriasBean;
import com.orange.bean.StoryBean;
import com.orange.global.RequestConstants;
import com.orange.orangestory.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //category image width
    private static final int CATEGORY_WIDTH = 160;

    //category image
    private static final int[] CATEGORY_IMAGE = {
            R.drawable.tonghua,R.drawable.yuyan,R.drawable.shuiqian,R.drawable.guoxue,R.drawable.shenhua,
            R.drawable.yingyu,R.drawable.baike,R.drawable.huiben,R.drawable.mingzhu,R.drawable.qita
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdapterViewFlipper adapterViewFlipper;
    private ArrayList<ImageView> arrayBanner;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //从后台获取数据
        fetchDataFromServer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        //AdapterViewFlipper
        adapterViewFlipper = homeFragmentView.findViewById(R.id.main_banner);
        //create adapter for flipper
        BaseAdapter flipperAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return arrayBanner.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                return arrayBanner.get(position);
            }
        };

        adapterViewFlipper.setAdapter(flipperAdapter);

        //reset view
        initView(homeFragmentView);

        return homeFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initView(View view){

        //collect category button
        LinearLayout category_first_line = view.findViewById(R.id.category_first_line_layout);
        LinearLayout category_second_line = view.findViewById(R.id.category_second_line_layout);

        ArrayList<Button> categoryButtonArray = new ArrayList<>();

        //first line
        for (int i = 0;i < category_first_line.getChildCount();i++){
            //get button item
            Button category_button = (Button) category_first_line.getChildAt(i);
            categoryButtonArray.add(category_button);
        }

        //second line
        for (int i = 0;i < category_second_line.getChildCount();i++){
            //get button item
            Button category_button = (Button) category_second_line.getChildAt(i);
            categoryButtonArray.add(category_button);
        }

        for (int i = 0;i < categoryButtonArray.size();i++){
            //resetting button image
            Drawable category_drawable = getContext().getResources().getDrawable(CATEGORY_IMAGE[i]);
            category_drawable.setBounds(0,0,CATEGORY_WIDTH,CATEGORY_WIDTH);

            //setting button image
            Button category_button = categoryButtonArray.get(i);
            category_button.setCompoundDrawables(null,category_drawable,null,null);
        }

        //search button resetting
        Drawable search = getContext().getResources().getDrawable(R.drawable.search);
        search.setBounds(0,0,70,70);

        Button searchButton = view.findViewById(R.id.main_search);
        searchButton.setCompoundDrawables(search,null,null,null);
    }

    private void fetchDataFromServer(){
        //banner's data
        arrayBanner = new ArrayList<>();
        for (int i = 0;i < 3;i++){
            ImageView bannerImageView = new ImageView(getContext());
            bannerImageView.setImageResource(R.drawable.banner);
            arrayBanner.add(bannerImageView);

            bannerImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Jerry","onClick ...");
                }
            });
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(RequestConstants.URL_REQUEST_STORY + RequestConstants.URL_REQUEST_STORY_GET_INDEX).build();
        Call call = client.newCall(request);
        //syn request
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Jerry","request failure le");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

                parseData(responseStr);
            }
        });
    }

    private void parseData(String dataStr){
        final Gson gson = new Gson();

        final MainPageDataJsonBean mainPageDataJsonBean = gson.fromJson(dataStr,MainPageDataJsonBean.class);
        ArrayList<BannerBean> bannerArray = mainPageDataJsonBean.getBanners();
        ArrayList<StoryBean> storyArray = mainPageDataJsonBean.getStories();
        ArrayList<SeriasBean> seriasArray = mainPageDataJsonBean.getSerias();
        Log.d("Jerry","banners count : " + bannerArray.size());
        Log.d("Jerry","storyArray count : " + storyArray.size());
        Log.d("Jerry","seriasArray count : " + seriasArray.size());

        //download banner image
    }
}
