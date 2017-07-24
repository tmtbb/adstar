package com.yundian.star;

import com.yundian.star.been.CircleFriendBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */

public class ComplaintActivityTest extends BaseRobolectricTestCase {
    @Test
    public void testMeth(){
        List mock = Mockito.mock(List.class);
        Mockito.when(mock.get(0)).thenReturn(1);
        int o = (int)mock.get(0);
        Assert.assertEquals(1,o);
        Mockito.verify(mock).get(0);
    }

    @Test
    public void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getAllCircleInfo(0, 10, new OnAPIListener<CircleFriendBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(CircleFriendBean circleFriendBean) {
                LogUtils.loge("圈子反馈" + circleFriendBean.toString());
            }
        });

    }



}
