//package com.ji.jichat.user.test;
//
//import com.ji.jichat.user.entity.Device;
//import com.ji.jichat.user.service.IDeviceService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DeviceServiceTest {
//
//    @Autowired
//    private IDeviceService deviceService;
//
//
//    @Test
//    public void testMyService() {
//        // Your test logic here
//        final List<Device> onlineDevices = deviceService.getOnlineDevices(1747905091907751936L);
//        System.out.println(onlineDevices);
//        Assert.assertNotNull(onlineDevices);
//        // Assert the result
////        assertEquals("Expected Result", result);
//    }
//}