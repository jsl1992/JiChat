//package com.ji.jichat.user.test;
//
//import com.ji.jichat.user.controller.DeviceController;
//import com.ji.jichat.user.controller.UserController;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DeviceControllerTest {
//
//    @Autowired
//    private DeviceController deviceController;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
//    }
//
//    @Test
//    public void hello() throws Exception {
////        mockMvc.perform(MockMvcRequestBuilders.get("user-api/device/getOnlineDevices?userId={userId}", 1747905091907751936L))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("长安"));
//    }
//}