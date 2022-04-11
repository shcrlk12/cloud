package com.kjwon.cloud.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FileSetControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("파일 조회 성공 테스트")
    void getFileList() throws Exception {
        String url = "/api/v1/fileList";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("path" , "test")
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("파일 업로드 성공 테스트")
    void contextLoads(){

    }
}
