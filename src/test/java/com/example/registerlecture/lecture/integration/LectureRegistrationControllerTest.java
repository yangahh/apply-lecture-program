package com.example.registerlecture.lecture.integration;

import com.example.registerlecture.lecture.application.facade.LectureRegistrationFacade;
import com.example.registerlecture.lecture.domain.dto.LectureRegistrationResult;
import com.example.registerlecture.lecture.interfaces.api.controller.LectureRegistrationController;
import com.example.registerlecture.lecture.interfaces.api.dto.LectureRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LectureRegistrationController.class)
class LectureRegistrationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    LectureRegistrationFacade lectureRegistrationFacade;


    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("잘못된 형식의 lectureId로 특강 신청에 실패한다.")
    @Test
    void shouldFailWhenIdIsInvalid() throws Exception {
        // given
        String uri = "/lectures/{id}/registrations";
        long lectureId = -1L;
        LectureRegistrationRequest request = new LectureRegistrationRequest(1L);

        // when  // then
        mockMvc.perform(MockMvcRequestBuilders.post(uri, lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @DisplayName("잘못된 형식의 userId로 특강 신청에 실패한다.")
    @Test
    void shouldFailWhenUserIdIsNull() throws Exception {
        // given
        String uri = "/lectures/{id}/registrations";
        long lectureId = 1L;
        LectureRegistrationRequest request = new LectureRegistrationRequest();

        // when  // then
        mockMvc.perform(MockMvcRequestBuilders.post(uri, lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("특강 신청에 성공한다.")
    @Test
    void shouldSuccessWhenRegisterLecture() throws Exception {
        // given
        String uri = "/lectures/{id}/registrations";
        long lectureId = 1L;
        long userId = 1L;
        LectureRegistrationRequest request = new LectureRegistrationRequest(userId);
        LectureRegistrationResult result = mock(LectureRegistrationResult.class);

        given(lectureRegistrationFacade.registerLecture(request.getUserId(), lectureId)).willReturn(result);

        // when  // then
        mockMvc.perform(MockMvcRequestBuilders.post(uri, lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @DisplayName("userId 파라미터 없이 특강 신청 목록을 조회할 경우 실패한다.")
    @Test
    void shouldFailWhenWithoutUserId() throws Exception {
        // given
        String uri = "/lectures/registrations";

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("특정 사용자가 신청한 특강 목록을 정상적으로 조회한다.")
    @Test
    void shouldSuccessWhenGetLectureRegistration() throws Exception {
        // give
        String uri = "/lectures/registrations";

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
