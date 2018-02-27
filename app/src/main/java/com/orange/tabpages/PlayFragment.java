package com.orange.tabpages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
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
 * {@link PlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, container, false);
        ImageView testImage = view.findViewById(R.id.test_image);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.nobanner)
                .showImageOnFail(R.drawable.nobanner)
                .showImageForEmptyUri(R.drawable.nobanner)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("http://cw-1253116201.cossh.myqcloud.com/banner/app%E4%B8%8A%E7%BA%BF.jpg",testImage,options);


//        fetchDataFromServer();


        return view;
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

    private void fetchDataFromServer(){

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

        //operate banner
//        final ImageLoader imageLoader = ImageLoader.getInstance();
//        final DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.nobanner)
//                .showImageOnFail(R.drawable.nobanner)
//                .showImageForEmptyUri(R.drawable.nobanner)
//                .build();

        //download banner image
        ArrayList<ImageView> bannerImageViewArray = new ArrayList<>();
        final ArrayList bannerImageURLStrArray = new ArrayList();
        final ArrayList bannerTextArray = new ArrayList();

        for (int i = 0;i < bannerArray.size();i++){
            BannerBean bannerBean = bannerArray.get(i);
            String logoURL = bannerBean.getLogoUrl();
            Log.d("Jerry","banner image url : " + logoURL);
            bannerImageURLStrArray.add(logoURL);
            bannerTextArray.add("test");

//            ImageView bannerImageView = new ImageView(getContext());
//            imageLoader.displayImage(bannerBean.getLogoUrl(),bannerImageView,options);
//            bannerImageViewArray.add(bannerImageView);

        }
    }
}
