package me.zy.sports.activitys.runningpage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

import me.zy.sports.R;
import me.zy.sports.map.impl.AmapImpl;


public class AMapShowFragment extends Fragment {

    private AMap aMap;
    private MapView MapView;
    private AmapImpl aMapImpl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_show_amap, container, false);
        MapView = (MapView)view.findViewById(R.id.aaamap);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        MapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {

            aMap = MapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        }
        aMapImpl = new AmapImpl(getActivity(), aMap);

        startLocation();
        return view;

    }


    public void startLocation(){

        aMapImpl.startLocation();

    }

    public void pauseLocation(){

        aMapImpl.pauseLocation();

    }

    public void closeLocationWithoutSaveRunData(){

        aMapImpl.closeLocation();

    }

    public void closeLocationWithSaveRunData(){

        aMapImpl.saveRunningData();
        aMapImpl.closeLocation();

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        MapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        MapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        MapView.onDestroy();

    }

}
