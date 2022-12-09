package it.albx79.telepass.cusdev;

import it.albx79.telepass.cusdev.api.CustomersApiDelegate;
import it.albx79.telepass.cusdev.api.DevicesApiDelegate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TelepassCusdevServerApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CustomersApiDelegate customers;
	@MockBean
	private DevicesApiDelegate devices;

	@Test
	void updateCustomerAddress_callsMethodOnDelegate() throws Exception {
		when(customers.updateCustomerAddress(eq("foo"), anyString()))
						.thenReturn(ResponseEntity.noContent().build());
		mockMvc.perform(put("/customers/{customerId}/address", "foo")
				.contentType(APPLICATION_JSON)
				.content("""
						"via montenapoleone 1, 20120 milano"
						"""))
				.andExpect(status().is(SC_NO_CONTENT));
	}

	@Test
	void updateDeviceStatus_callsMethodOnDelegate() throws Exception {
		var deviceId = UUID.randomUUID();
		when(devices.updateDeviceStatus(eq(deviceId), anyString()))
						.thenReturn(ResponseEntity.noContent().build());
		mockMvc.perform(put("/devices/{deviceId}/status", deviceId)
				.contentType(APPLICATION_JSON)
				.content("""
						"INACTIVE"
						"""))
				.andExpect(status().is(SC_NO_CONTENT));
	}
}
