package com.yundian.star;

import android.app.Application;
import android.content.Context;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by Administrator on 2017/7/10.
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowLog.class}, constants = BuildConfig.class, sdk = 23,application = TestAppliction.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.json.*", "sun.security.*", "javax.net.*"})
public abstract class BaseRobolectricTestCase {
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    private static boolean hasInited = false;

    public void setUp(){
        if (!hasInited){
            //需要什么初始化可以写在这里-。-
            hasInited = true ;
        }
        MockitoAnnotations.initMocks(this);
    }
    public Application getAppApplication(){
        return RuntimeEnvironment.application;
    }
    public Context getContext(){
        return RuntimeEnvironment.application;
    }


}
