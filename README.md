#一个模仿淘宝秒杀商品页面的进度条

###Snapshot
![snapshot](https://github.com/liuhuibin/TaoBaoProgressBar/blob/master/.raw/snapshot2.jpg)

##Usage

```XML
<com.liuhb.taobaoprogressbar.com.liuhb.taobaoprogressbar.view.CustomProgressBar
        android:layout_width="match_parent"
        android:layout_height="30dp"
        app:progress="20"
        app:max="100"
        app:progressRadius="8dp"
        app:progressDesc="已售"
        app:isShowDesc="true"/>
```

###Advanced

```Java
mProgressBar = (CustomProgressBar) findViewById(R.id.cpb_progresbar);
        mProgressBar.setProgressDesc("剩余");
        mProgressBar.setMaxProgress(100);
        mProgressBar.setProgressColor(Color.parseColor("#F6CB82"));
        mProgressBar2.setCurProgress(70,2000);
```
