package com.corelogic.hazardbot.subscriber;

import com.corelogic.hazardbot.HazardbotApplication;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HazardbotApplication.class)
@Transactional
public class SubscriberControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void createSubscriber() throws Exception {
        mockMvc.perform(
                post("/subscribers")
                        .param("phoneNumber", "512-555-1212")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void removeSubscriber() throws Exception {
        final SubscriberEntity subscriberEntity =
                subscriberRepository.saveAndFlush(new SubscriberEntity("512-111-1111"));

        mockMvc.perform(
                delete("/subscribers")
                        .param("phoneNumber", "512-111-1111")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        BDDAssertions.then(subscriberRepository.findOne(subscriberEntity.getId())).isNull();
    }
}