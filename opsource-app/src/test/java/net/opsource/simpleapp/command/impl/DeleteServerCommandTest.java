package net.opsource.simpleapp.command.impl;

import static net.opsource.simpleapp.util.ApplicationConstants.SERVER_ID;
import static net.opsource.simpleapp.util.ApplicationConstants.STATUS_CODE;
import static net.opsource.simpleapp.util.ApplicationConstants.STATUS_ERROR;
import static net.opsource.simpleapp.util.ApplicationConstants.STATUS_SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.opsource.simpleapp.command.CommandRequest;
import net.opsource.simpleapp.command.CommandResponse;
import net.opsource.simpleapp.service.ServerMonitoringService;

@RunWith(MockitoJUnitRunner.class)
public class DeleteServerCommandTest {
	@Mock
	private CommandRequest mockedCommandRequest;
	@Mock
	private CommandResponse mockedCommandResponse;
	@Mock
	private ServerMonitoringService mockedServerMonitoringService;

	@InjectMocks
	private DeleteServerCommand objectUnderTest;

	@Before
	public void setUp() throws Exception {
		final Map<String, Object> requestAttributes = new HashMap<>();
		requestAttributes.put(SERVER_ID, 1);
		when(mockedCommandRequest.getCommandRequest()).thenReturn(requestAttributes);
	}

	@Test
	public void execute_withCommandRequest_returnSuccessStatusWithCommandResponse() {
		final CommandResponse commandResponse = objectUnderTest.execute(mockedCommandRequest);
		final Map<String, Object> responseAttributes = commandResponse.getCommandResponse();
		assertEquals(2, responseAttributes.size());
		assertEquals(STATUS_SUCCESS, responseAttributes.get(STATUS_CODE));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void execute_withCommandRequest_returnErrorStatusWithCommandResponse() {
		when(mockedServerMonitoringService.delete((any(int.class)))).thenThrow(Exception.class);
		final CommandResponse commandResponse = objectUnderTest.execute(mockedCommandRequest);
		final Map<String, Object> responseAttributes = commandResponse.getCommandResponse();
		assertEquals(2, responseAttributes.size());
		assertEquals(STATUS_ERROR, responseAttributes.get(STATUS_CODE));
	}

}
