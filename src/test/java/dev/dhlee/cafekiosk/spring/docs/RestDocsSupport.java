package dev.dhlee.cafekiosk.spring.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

	protected MockMvc mockMvc;
	protected ObjectMapper objectMapper;

	@BeforeEach
	void setUp(RestDocumentationContextProvider provider) {

		this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
			.apply(MockMvcRestDocumentation.documentationConfiguration(provider))
			.build();

		this.objectMapper = new ObjectMapper();
	}

	protected abstract Object initController();
}
