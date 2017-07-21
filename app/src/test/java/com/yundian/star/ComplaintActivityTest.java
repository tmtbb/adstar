package com.yundian.star;

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



}
