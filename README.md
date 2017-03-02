# simple android lock screen ads
simple android lock screen apps, show ads image and slide to unlock

# Config
Simple configure to add lockscreen in apps, see example in MainActivity
    
    init
        LockScreen.getInstance().init(this);

    init with disable home button
        LockScreen.getInstance().init(this,true);

    check is active 
        LockScreen.getInstance().isActive()

    active lock screen
        LockScreen.getInstance().active();
    
    deactive lock screen
        LockScreen.getInstance().deactivate();

# Preview
![Preview ](https://raw.githubusercontent.com/SeptiyanAndika/simple-lockscreen-ads/master/preview/setting.png)
![Preview ](https://raw.githubusercontent.com/SeptiyanAndika/simple-lockscreen-ads/master/preview/lockscreen.png)

# Sample APK
[LockScreenTest.apk](https://raw.githubusercontent.com/SeptiyanAndika/simple-lockscreen-ads/master/apk/LockScreenTest.apk)
