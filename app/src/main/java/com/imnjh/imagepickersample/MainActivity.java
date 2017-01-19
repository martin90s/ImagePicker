package com.imnjh.imagepickersample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.imnjh.imagepickersample.adapter.PickAdapter;
import com.imnjh.imagepickersample.cache.CacheManager;

public class MainActivity extends AppCompatActivity {

  public static final String AVATAR_FILE_NAME = "avatar.png";
  public static final int REQUEST_CODE_AVATAR = 100;
  public static final int REQUEST_CODE_IMAGE = 101;

  private Button pickHeadBtn;
  private Button pickImageBtn;
  private Button pickImageWithLimitBtn;
  private GridView gridView;
  private PickAdapter pickAdapter;
  private TextView originalView;
  private CheckBox showCamera;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initUI();
  }

  private void initUI() {
    pickAdapter = new PickAdapter(this);
    gridView = (GridView) findViewById(R.id.image_grid);
    gridView.setAdapter(pickAdapter);
    showCamera = (CheckBox) findViewById(R.id.show_camera);
    originalView = (TextView) findViewById(R.id.original);
    pickHeadBtn = (Button) findViewById(R.id.pick_head_icon);
    pickImageBtn = (Button) findViewById(R.id.pick_image);
    pickImageWithLimitBtn = (Button) findViewById(R.id.pick_image_with_limit);
    pickHeadBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SImagePicker
            .from(MainActivity.this)
            .pickMode(SImagePicker.MODE_AVATAR)
            .showCamera(showCamera.isChecked())
            .cropFilePath(
                CacheManager.getInstance().getImageInnerCache()
                    .getAbsolutePath(AVATAR_FILE_NAME))
            .forResult(REQUEST_CODE_AVATAR);
      }
    });
    pickImageBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SImagePicker
            .from(MainActivity.this)
            .maxCount(9)
            .rowCount(3)
            .showCamera(showCamera.isChecked())
            .pickMode(SImagePicker.MODE_IMAGE)
            .forResult(REQUEST_CODE_IMAGE);
      }
    });
    pickImageWithLimitBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SImagePicker
            .from(MainActivity.this)
            .maxCount(9)
            .rowCount(3)
            .pickMode(SImagePicker.MODE_IMAGE)
            .fileInterceptor(new SingleFileLimitInterceptor())
            .forResult(REQUEST_CODE_IMAGE);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      final ArrayList<String> pathList =
          data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
          data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
      pickAdapter.setNewData(pathList);
      originalView.setText("原图：" + original);
    }
  }
}
