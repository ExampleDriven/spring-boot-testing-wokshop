package org.exampledriven.springboottestingworkshop.solution.service;

import org.exampledriven.springboottestingworkshop.client.PostClient;
import org.exampledriven.springboottestingworkshop.domain.PersonAndPost;
import org.exampledriven.springboottestingworkshop.domain.Post;
import org.exampledriven.springboottestingworkshop.service.PostService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Peter Szanto on 7/3/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PostServiceStubTestConfigurationTest {

    private static final String STUBBED_TITLE = "stubbed";
    private static final String JOHN = "John";
    private static final String DOE = "Doe";

    @Autowired
    private PostService postService;

    @Test
    public void readPersonAndPost() {

        PersonAndPost actual = postService.readPersonAndPost(JOHN, DOE);

        assertEquals(JOHN, actual.getPerson().getFirstName());
        assertEquals(DOE, actual.getPerson().getLastName());

        assertEquals(1, actual.getPosts().size());

        Post firstPost = actual.getPosts().get(0);
        assertEquals(STUBBED_TITLE, firstPost.getTitle());
        assertEquals(100, firstPost.getId());

    }

    @TestConfiguration
    public static class NestedConfig {

        @Bean
        public PostClient postClient() {
            return new PostClient(new RestTemplateBuilder()) {

                @Override
                public List<Post> readPosts(int userId) {

                    List<Post> stubResponse = new LinkedList<>();

                    Post post = new Post();
                    post.setId(100);
                    post.setTitle(STUBBED_TITLE);

                    stubResponse.add(post);

                    return stubResponse;
                }
            };
        }

    }

}