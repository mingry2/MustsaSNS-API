package com.mutsasns.finalproject_kimmingyeong.controller;

import com.mutsasns.finalproject_kimmingyeong.controller.restcontroller.HelloRestController;
import com.mutsasns.finalproject_kimmingyeong.service.AlgorithmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloRestController.class)
class HelloRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AlgorithmService algorithmService;

    @Test
    @WithMockUser
    @DisplayName("hello")
    void hello() throws Exception {

        mockMvc.perform(get("/api/v1/hello")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("김민경"));
    }

    @Test
    @WithMockUser
    @DisplayName("자릿수 합")
    void sumOfDigit() throws Exception {

        when(algorithmService.sumOfDigit(687))
                .thenReturn(21);

        mockMvc.perform(get("/api/v1/hello/687")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("21"));
    }

}