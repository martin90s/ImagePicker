# <img src="http://7xpb9x.com1.z0.glb.clouddn.com/2017/01/19/c4cb41c69d38f0284c70ba32c2839983.png" width=48 />ImagePicker

[![](https://jitpack.io/v/martin90s/ImagePicker.svg)](https://jitpack.io/#martin90s/ImagePicker)  [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SImagePicker-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5129)


## 效果图
### 第一张头像模式，第二张选择多张图片（包括动画和顺畅的跳转），第三张是分片加载超大图（19.5M，10000*5000px）

![image](https://github.com/martin90s/ScreenShot/blob/master/choose_avatar.gif)
![image](https://github.com/martin90s/ScreenShot/blob/master/choose_image.gif)
![image](https://github.com/martin90s/ScreenShot/blob/master/huge_iamge.gif)

项目介绍请参考
* [Android-如何开发一个功能强大的图片选择器](http://www.jianshu.com/p/81aeb7e2eaa9)

致谢
* [subsampling-scale-image-view](https://github.com/davemorrissey/subsampling-scale-image-view) 
* y总的页面切换动画


# 如何添加
### Gradle
#### 1.在Project的build.gradle 中添加仓库地址
``` gradle
 // JitPack仓库地址
 maven { url "https://jitpack.io" }
```

示例：
``` gradle
allprojects {
    repositories {
        jcenter()
        // JitPack仓库地址
        maven { url "https://jitpack.io" }
    }
}
```
#### 2.在app目录下的build.gradle中添加依赖
```gradle
    //SImagePicker
	compile 'com.github.martin90s:ImagePicker:v1.3.2'
```

# 如何使用
#### 1.首先初始化（推荐在Application的oncreate中调用）
```java
 SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
 			.setImageLoader(new FrescoImageLoader())
			.setToolbaseColor(getColor(R.color.colorPrimary))
			.build());
```
#### 2.在需要选择图片的地方调用
```java
 SImagePicker
            .from(MainActivity.this)
            .maxCount(9)
            .rowCount(3)
            .pickMode(SImagePicker.MODE_IMAGE)
            .fileInterceptor(new SingleFileLimitInterceptor())
            .forResult(REQUEST_CODE_IMAGE);
```


# 可配置项
#### 1.全局配置（即初始化时传入的PickerConfig,此配置作用于SImagePicker整个使用过程）
| 配置参数  | 参数含义  |
| ------------ | ------------ |
| setImageLoader(ImageLoader)  |  使用的图片加载器。demo工程中实现了Fresco和Glide两种ImageLoader,可以参考 |
| setToolbarColor(int)  |  Picker的主色调，默认值是App的primaryColor |
| setAppContext(Context) |  Picker内部用到的Context，传入ApplicationContext即可 |

#### 2.单次配置（即每次调用SImagePicker时传入的参数，此参数只对这次调用生效）
|  配置参数  | 参数含义  |
| ------------ | ------------ |
|  from(Activity or Fragment) | 调用图片选择器可从Activity或者Fragment进入，最后的结果会在onActivityResult()返回，现在返回的结果有两个值，用户选择的图片的路径列表data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);用户是否选择了原图data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
|
| maxCount(int) | 此次选择允许的最大选择数量，默认是1.比如发朋友圈最多选择9张图就传9  |
| rowCount(int)  | 图片列表单排展示多少张图  |
| setSelected(List<String>) |  默认已经被选中的图片 |
|  pickMode(int) | 选图的模式，现在有头像模式和普通模式两种，头像模式选中图片后默认会跳到图片裁剪页面且默认只能选择一张  |
| cropFilePath(String)  | 头像模式下裁剪图片存放地址  |
| showCamera(boolen)  | 是否要展示拍照入口  |
| pickText(int)  | Picker里右下角展示的文字信息（比如配置选择，发送，完成）  |
| fileInterceptor(FileChooseInterceptor) |  图片过滤器，比如用户选择的单张图片大小有限制，即可写在这个拦截器中，当用户选择过大图片时可以提示并且过滤 |
|forResult(int requestCode) | 打开图片选择器，并且传入requestCode

##获取结果
### 在调用图片选择器的Fragment或者Activity中
```java
@Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE) {
      final ArrayList<String> pathList =
          data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
      final boolean original =
          data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
    }
  }
```

##TODO
* 增加一个普通模式。由于现在预览为了支持超大图所以选择了SubsamplingView。后续增加一个不支持超大图的模式，会更加流畅
* Glide下由于glide的缓存策略，跳转动画第一次播放会闪一下，下版修复此问题

##联系方式
* 邮箱地址： martinhi2016@gmail.com
