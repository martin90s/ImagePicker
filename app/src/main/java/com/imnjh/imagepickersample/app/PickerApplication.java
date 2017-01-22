package com.imnjh.imagepickersample.app;

import android.app.Application;
import android.content.Context;

import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepickersample.R;
import com.imnjh.imagepickersample.imageloader.FrescoImageLoader;

/**
 * Created by Martin on 2017/1/17.
 */

public class PickerApplication extends Application {

  private static Application instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
        .setImageLoader(new FrescoImageLoader())
        .setToolbaseColor(getResources().getColor(R.color.colorPrimary)).build());
  }


  public static Context getAppContext() {
    return instance;
  }
}
