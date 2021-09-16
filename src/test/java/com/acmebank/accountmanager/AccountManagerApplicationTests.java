package com.acmebank.accountmanager;

import com.acmebank.accountmanager.model.TransferRequest;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class AccountManagerApplicationTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private AccountRepository accountRepository;

	@Test
	void testGetAccountSuccess() throws Exception {

		this.mvc.perform(MockMvcRequestBuilders.get("/account/12345678"))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(10000000.00))
				.andReturn();

	}
	@Test
	void testGetAccountNotFound() throws Exception {

		this.mvc.perform(MockMvcRequestBuilders.get("/account/999"))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Account not found"))
				.andReturn();

	}

	@Test
	void testTransferSuccess() throws Exception {

		TransferRequest transferRequest = TransferRequest.builder().fromAccountId(12345678L).toAccountId(88888888L).amount(new BigDecimal(1)).build();

		this.mvc.perform(MockMvcRequestBuilders.post("/transfer")
						.content(asJsonString(transferRequest))
						.contentType(MediaType.APPLICATION_JSON)
					)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Transferred successfully"))
				.andReturn();

	}

	@Test
	void testTransferNotEnoughBalance() throws Exception {

		TransferRequest transferRequest = TransferRequest.builder().fromAccountId(12345678L).toAccountId(88888888L).amount(new BigDecimal(99999999)).build();

		this.mvc.perform(MockMvcRequestBuilders.post("/transfer")
						.content(asJsonString(transferRequest))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Not enough balance from account 12345678"))
				.andReturn();

	}

	@Test
	void testTransferAccountNotFound() throws Exception {

		TransferRequest transferRequest = TransferRequest.builder().fromAccountId(1234568L).toAccountId(88888888L).amount(new BigDecimal(1)).build();

		this.mvc.perform(MockMvcRequestBuilders.post("/transfer")
						.content(asJsonString(transferRequest))
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Account "+1234568L+" not found"))
				.andReturn();

	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
