package me.zy.sports.activitys.runningpage;


import me.zy.sports.map.interfacer.BasePresenter;
import me.zy.sports.map.interfacer.BaseView;


public class RuningContract {

    interface View extends BaseView<Presenter> {

        void showMapFragment();

        void showDataFragment();

        void showRunningStateControl();

        void closeRunningStateControl();

    }

    interface Presenter extends BasePresenter {

        void stopRunning();

        void starRunning();

        void pauseRunning();

        void playMusic();

        void takePhoto();

        void openAblum();
    }

}
