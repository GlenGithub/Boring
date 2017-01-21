package com.susion.boring.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.susion.boring.mainui.MainActivity;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/1/17.
 */
public class SplashPresenter implements ISplashPresenter{


    ISplashView mSplashView;

    public SplashPresenter(ISplashView mSplashView) {
        this.mSplashView = mSplashView;
    }

    @Override
    public void setImageAndDescText(int imageId, int textId) {
        mSplashView.setCenterImageAndDescText(imageId, textId);
    }

    @Override
    public void skipToMainActivity(final Activity context, View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(context);
                context.finish();
            }
        }, 3000);
    }



    @Override
    public void initConfig() {

    }


    /*
    * -1 means no icon
    * */
    @Override
    public void setAuthorInfo(Context context, int iconId, int textId) {
        if (iconId != -1) {
            Drawable drawable = context.getResources().getDrawable(iconId);
            drawable.setBounds(0, 0, UIUtils.dp2Px(20), UIUtils.dp2Px(20));
            mSplashView.setAuthorInfo(drawable, textId);
            return;
        }

        mSplashView.setAuthorInfo(null, textId);
    }

}
