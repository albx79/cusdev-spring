package it.albx79.telepass.cusdev;

import it.albx79.telepass.cusdev.api.CustomersApiDelegate;
import it.albx79.telepass.cusdev.api.DevicesApiDelegate;
import it.albx79.telepass.cusdev.devices.DevicesRepo;
import it.albx79.telepass.cusdev.error.PreconditionFailedException;
import it.albx79.telepass.cusdev.error.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	private UUID deviceId;
	@Autowired
	private DevicesRepo devicesRepo;

	@BeforeEach
	void setup() {
		deviceId = UUID.randomUUID();
	}

	@Test
	void updateCustomerAddress_callsMethodOnDelegate() throws Exception {
		when(customers.updateCustomerAddress(eq(123), any()))
						.thenReturn(ResponseEntity.noContent().build());
		mockMvc.perform(put("/customers/{customerId}/address", 123)
				.contentType(APPLICATION_JSON)
				.content("""
						{ "address": "via montenapoleone 1, 20120 milano" }
						"""))
				.andExpect(status().is(SC_NO_CONTENT));
	}

	@Test
	void updateDeviceStatus_callsMethodOnDelegate() throws Exception {
		when(devices.updateDeviceStatus(eq(deviceId), any()))
						.thenReturn(ResponseEntity.noContent().build());
		mockMvc.perform(put("/devices/{deviceId}/status", deviceId)
				.contentType(APPLICATION_JSON)
				.content("""
						{ "status": "INACTIVE" }
						"""))
				.andExpect(status().is(SC_NO_CONTENT));
	}

	@Test
	void when_ResourceNotFoundException_then_status_is_404() throws Exception {
		when(devices.getDevice(any())).thenThrow(new ResourceNotFoundException("foo"));
		mockMvc.perform(get("/devices/{deviceId}", deviceId))
				.andExpect(status().is(SC_NOT_FOUND));
	}

	@Test
	void when_PreconditionFailedException_then_status_is_412() throws Exception {
		when(devices.getDevice(any())).thenThrow(new PreconditionFailedException("foo"));
		mockMvc.perform(get("/devices/{deviceId}", deviceId))
				.andExpect(status().is(SC_PRECONDITION_FAILED));
	}
}
