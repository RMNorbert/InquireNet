package com.rmnnorbert.InquireNet.controller.integration;

import com.rmnnorbert.InquireNet.controller.AnswerController;
import com.rmnnorbert.InquireNet.customExceptionHandler.NotFoundException;
import com.rmnnorbert.InquireNet.dto.answer.AnswerDTO;
import com.rmnnorbert.InquireNet.dto.answer.AnswerRequestDTO;
import com.rmnnorbert.InquireNet.dto.answer.VoteDTO;
import com.rmnnorbert.InquireNet.dto.delete.DeleteRequestDTO;
import com.rmnnorbert.InquireNet.dto.update.UpdateDTO;
import com.rmnnorbert.InquireNet.security.config.JwtService;
import com.rmnnorbert.InquireNet.service.AnswerService;
import com.rmnnorbert.InquireNet.service.VoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnswerController.class)
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private VoteService voteService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final static String MOCK_USERNAME = "testUser";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        /*User testUser = new User(1L, Role.USER, MOCK_USERNAME, "password", LocalDateTime.now());
        when(jwtService.extractUsername(anyString())).thenReturn(testUser.getUsername());
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        List<SimpleGrantedAuthority> authorities = Role.USER.getAuthorities();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                testUser.getUsername(),
                testUser.getPassword(),
                authorities
        );

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);*/
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersShouldReturnOkStatusAndAnswerDTOList() throws Exception {
    //    when(answerService.getAllAnswers()).thenReturn(expectedAnswerDTOs);
    //    this.mockMvc.perform(get("/answers/all")).andDo(print()).andExpect(status().isOk())// if
    //    this.mockMvc.perform(get("/answers/all"))).andExpect(status().isOk())
    //                .andExpect(jsonPath("$[0].answer_id", Matchers.is(1)))
    //                .andExpect(jsonPath("$[0].description", is("desc")));
        List<AnswerDTO> expectedAnswerDTOs = List.of(
                new AnswerDTO(1L,
                              1,
                              1,
                              "desc",
                              LocalDateTime.now(),
                              0,
                              "voted")
        );

        when(answerService.getAllAnswers()).thenReturn(expectedAnswerDTOs);

        mockMvc.perform(get("/answers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].answer_id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].description", is("desc")));

        verify(answerService,times(1)).getAllAnswers();
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersShouldReturnOkStatusAndEmptyAnswerDTOList() throws Exception {
        List<AnswerDTO> expectedAnswerDTOs = new ArrayList<>();

        when(answerService.getAllAnswers()).thenReturn(expectedAnswerDTOs);

        mockMvc.perform(get("/answers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.empty()));

        verify(answerService,times(1)).getAllAnswers();
    }

    @Test
    void getAllAnswersShouldReturnUnauthorizedStatus() throws Exception {
        mockMvc.perform(get("/answers/all"))
                .andExpect(status().isUnauthorized());

        verify(answerService,times(0)).getAllAnswers();
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAnswerByIdShouldReturnOkStatusAndAnswerDTO() throws Exception {
        long searchedId = 1;
        AnswerDTO expectedAnswerDTO = new AnswerDTO(1,
                                                    1,
                                                    1,
                                                    "desc",
                                                    LocalDateTime.now(),
                                                    1,
                                                    "vote");

        when(answerService.getAnswerById(searchedId)).thenReturn(expectedAnswerDTO);

        mockMvc.perform(get("/answers/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer_id", Matchers.is((int)searchedId)))
                .andExpect(jsonPath("$.description", is("desc")));

        verify(answerService,times(1)).getAnswerById(searchedId);
    }

    @Test
    void getAnswerByIdShouldReturnUnauthorizedStatus() throws Exception {
        long searchedId = 1;

        mockMvc.perform(get("/answers/" + searchedId))
                .andExpect(status().isUnauthorized());

        verify(answerService,times(0)).getAnswerById(searchedId);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAnswerByIdWhenServiceThrowsNotFoundExceptionShouldReturnNotFoundStatus() throws Exception {
        long searchedId = 1;

        when(answerService.getAnswerById(searchedId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/answers/" + searchedId))
                .andExpect(status().isNotFound());

        verify(answerService,times(1)).getAnswerById(searchedId);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getNumberOfAnswersByUserIdShouldReturnOkStatusAndIntegerNumber() throws Exception {
        long id = 1;
        int numberOfAnswers = 2;

        when(answerService.getNumberOfAnswersByUserId(id)).thenReturn(numberOfAnswers);

        mockMvc.perform(get("/answers/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(String.valueOf(numberOfAnswers))));

        verify(answerService,times(1)).getNumberOfAnswersByUserId(id);
    }
    @Test
    void getNumberOfAnswersByUserIdShouldReturnUnauthorizedStatusWithEmptyBody() throws Exception {
        long id = 1;

        mockMvc.perform(get("/answers/user/" + id))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(emptyString()));
        verify(answerService,times(0)).getNumberOfAnswersByUserId(id);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersByQuestionIdShouldReturnOkStatusAndAnswerDTOList() throws Exception {
        long searchedId = 1;
        List<AnswerDTO> expected = List.of(
          new AnswerDTO(1,1,1,"desc",LocalDateTime.now(),1,"vote"),
          new AnswerDTO(2,2,1,"description",LocalDateTime.now(),1,"vote")
        );

        when(answerService.getAllAnswersByQuestionId(searchedId)).thenReturn(expected);

        mockMvc.perform(get("/answers/q/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].answer_id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].description",Matchers.is("desc")))
                .andExpect(jsonPath("$[1].answer_id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].description",Matchers.is("description")));

        verify(answerService,times(1)).getAllAnswersByQuestionId(searchedId);
    }
    @Test
    @WithMockUser(username = MOCK_USERNAME)
    void getAllAnswersByQuestionIdShouldReturnOkStatusAndEmptyAnswerDTOList() throws Exception {
        long searchedId = 1;
        List<AnswerDTO> expected = new ArrayList<>();

        when(answerService.getAllAnswersByQuestionId(searchedId)).thenReturn(expected);

        mockMvc.perform(get("/answers/q/" + searchedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.empty()));

        verify(answerService,times(1)).getAllAnswersByQuestionId(searchedId);
    }
    @Test
    void getAllAnswersByQuestionIdShouldReturnUnauthorizedStatusAndEmptyBody() throws Exception {
        long searchedId = 1;

        mockMvc.perform(get("/answers/q/" + searchedId))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(Matchers.emptyString()));

        verify(answerService,times(0)).getAllAnswersByQuestionId(searchedId);
    }

    @Test
    void addNewAnswerShouldReturnForbiddenStatusAndEmptyBody() throws Exception {
        AnswerRequestDTO dto = new AnswerRequestDTO(1L,"desc",1L);


        mockMvc.perform(post("/answers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.emptyString()));

        verify(answerService,times(0)).addNewAnswer(dto);
    }

    /*@Test
    @WithMockUser(username = MOCK_USERNAME)
    void addNewAnswerSuccessfully() throws Exception {
        boolean expectedResponse = true;
        AnswerRequestDTO dto = new AnswerRequestDTO(1L,"desc",1L);

        when(answerService.addNewAnswer(dto)).thenReturn(expectedResponse);

        mockMvc.perform(post("/answers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));

        verify(answerService,times(0)).addNewAnswer(dto);
    }*/

    @Test
    public void deleteAnswerByIdShouldReturnForbiddenStatusAndEmptyBody() throws Exception {
        DeleteRequestDTO dto = new DeleteRequestDTO(1,1);

        mockMvc.perform(delete("/answers/"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.emptyString()));

        verify(answerService,times(0)).deleteAnswerById(dto);
    }

    /*@Test
    @WithMockUser(username = MOCK_USERNAME)
    public void deleteAnswerByIdShouldReturnOkStatusAndTrue() throws Exception {
        // Mock the user details service to return a user with the necessary role
        List<SimpleGrantedAuthority> authorities = Role.USER.getAuthorities();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                MOCK_USERNAME,
                "testpassword",
                authorities
        );

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        mockMvc.perform(delete("/answers/")
                .with(user("testuser").roles("USER")))
                .andExpect(status().isOk());
    }
*/
    @Test
    void voteOnAnswerByIdShouldReturnForbiddenStatusAndEmptyBody() throws Exception {
        VoteDTO dto = new VoteDTO("vote",1,1,1);

        mockMvc.perform(put("/answers/vote"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.emptyString()));
        verify(voteService,times(0)).vote(dto);
    }
/*
  @Test
    @WithMockUser(username = MOCK_USERNAME)
    void voteOnAnswerById() throws Exception {
        boolean expected = true;
        VoteDTO dto = new VoteDTO("vote",1,1,1);

        when(voteService.vote(dto)).thenReturn(expected);

        mockMvc.perform(put("/answers/vote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));
    }
*/

    @Test
    void updateAnswerShouldReturnForbiddenStatusAndEmptyBody() throws Exception {
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        mockMvc.perform(put("/answers/"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.emptyString()));

        verify(answerService,times(0)).update(dto);
    }
/*@Test
    @WithMockUser(username = MOCK_USERNAME)
    void updateAnswer() throws Exception {
        boolean expected = true;
        UpdateDTO dto = new UpdateDTO(1,1,"title","desc");

        when(answerService.update(dto)).thenReturn(expected);

        mockMvc.perform(put("/answers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));
    }*/
}
