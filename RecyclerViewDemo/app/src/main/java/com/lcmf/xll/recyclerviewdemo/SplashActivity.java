package com.lcmf.xll.recyclerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechUtility;
import com.lcmf.xll.recyclerviewdemo.DefView.DefViewActivity;
import com.lcmf.xll.recyclerviewdemo.ScreenRecoder.ScreenRecoderActivity;
import com.lcmf.xll.recyclerviewdemo.fragment.FragmentNestActivity;
import com.lcmf.xll.recyclerviewdemo.fragment.GLFragment.GLFragmentActivity;
import com.lcmf.xll.recyclerviewdemo.xunfei.XunFeiActivity;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class SplashActivity extends Activity {
	//创建3个动画
	Animation mFadein;
	Animation mFadeInScale;
	Animation mFadeOut;

	ImageView mImageView;

	public static final String ACTION_INSTALL_SHORTCUT = "CNA";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		SpeechUtility.createUtility(SplashActivity.this, "appid=" + getString(R.string.app_id));

		//init Animation
		mImageView = (ImageView)findViewById(R.id.image);
		//先去判断是不是初次执行
		SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
		int times = pref.getInt("times", 0);
		if(Integer.compare(times, 2) != 0) {
			SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
			editor.putInt("times", 2);
			editor.apply();
		}else{
			Intent intent = new Intent(SplashActivity.this, ScreenRecoderActivity.class);
			startActivity(intent);
			finish();
		}
		initAnim();
		setListener();
	}

	private void initAnim()
	{
		mFadein = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
		mFadein.setDuration(500);
		mFadeInScale = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(2000);
		mFadeOut = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out);
		mFadeOut.setDuration(500);
		mImageView.startAnimation(mFadein);
	}

	public void setListener()
	{
		mFadein.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
					mImageView.startAnimation(mFadeInScale);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		mFadeInScale.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mImageView.startAnimation(mFadeOut);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		mFadeOut.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(SplashActivity.this, ScreenRecoderActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}
}
