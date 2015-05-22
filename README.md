#一个模仿淘宝秒杀商品页面的进度条

###Snapshot
![snapshot](https://github.com/liuhuibin/TaoBaoProgressBar/blob/master/.raw/snapshot.jpg)

##Usage

```XML
<com.liuhb.taobaoprogressbar.com.liuhb.taobaoprogressbar.view.CustomProgressBar
        android:layout_width="match_parent"
        android:layout_height="30dp"
        app:progress="20"
        app:max="100"
        app:progressRadius="8dp"
        app:progressDesc="已售"/>
```

###Advanced

```Java
mProgressBar = (CustomProgressBar) findViewById(R.id.cpb_progresbar);
        mProgressBar.setProgressDesc("剩余");
        mProgressBar.setMaxProgress(50);
        mProgressBar.setProgress(30);
        mProgressBar.setProgressColor(Color.parseColor("#F6CB82"));
```
