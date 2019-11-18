package org.challenge.tasklist;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;


import org.challenge.tasklist.model.dto.UserDTO;
import org.challenge.tasklist.server.businesslogic.DozerMapperEJB;
import org.challenge.tasklist.server.businesslogic.UserEJB;
import org.challenge.tasklist.server.dao.UserDAO;
import org.challenge.tasklist.server.rest.exception.FieldRequiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

class UserEJBTest extends AbstractTest {

	private UserEJB userEJB;
	private UserDAO userDAO;
	
	@BeforeEach
	void setUp() throws Exception {
		DozerMapperEJB dozerMapper = new DozerMapperEJB(LoggerFactory.getLogger(DozerMapperEJB.class));
		dozerMapper.initializeMapper();
		userDAO = Mockito.mock(UserDAO.class);
		userEJB = new UserEJB(userDAO, dozerMapper);
	}

	@Test
	void testCheckRequiredUserFieldsIfNullOrSizeZero() {
		List<UserDTO> usersDTO = null;
		assertDoesNotThrow(() -> userEJB.checkRequiredUserFields(usersDTO));
		
		List<UserDTO> usersDTOZero = new ArrayList<>();
		assertDoesNotThrow(() -> userEJB.checkRequiredUserFields(usersDTOZero));
	}
	
	@Test
	void testCheckRequiredUserFieldsIfEmpty() {
		List<UserDTO> usersDTO = new ArrayList<>() ;
		usersDTO.add(new UserDTO());
		
		assertThrows(FieldRequiredException.class, () -> userEJB.checkRequiredUserFields(usersDTO));
	}
	
	@Test
	void testCheckRequiredUserFieldsIfFirstNameNull() {
		List<UserDTO> usersDTO = new ArrayList<>();
		UserDTO userDTO = createUserDTO();
		userDTO.setFirstName(null);
		usersDTO.add(userDTO);
		
		assertThrows(FieldRequiredException.class, () -> userEJB.checkRequiredUserFields(usersDTO));
	}

	@Test
	void testCheckRequiredUserFieldsIfLastNameNull() {
		List<UserDTO> usersDTO = new ArrayList<>();

		UserDTO userDTO = createUserDTO();
		userDTO.setLastName(null);
		usersDTO.add(userDTO);
		
		assertThrows(FieldRequiredException.class, () -> userEJB.checkRequiredUserFields(usersDTO));
	}
	
	@Test
	void testExistUsersToInsert() {
		List<UserDTO> usersDTO = new ArrayList<>();

		UserDTO userDTO = createUserDTO();
		userDTO.setUserId(null);
		usersDTO.add(userDTO);
		
		assertEquals(userEJB.usersToInsert(usersDTO).size(), 1);
	}
	
	@Test
	void testNotExistUsersToInsert() {
		List<UserDTO> usersDTO = new ArrayList<>();

		UserDTO userDTO = createUserDTO();
		usersDTO.add(userDTO);
		
		assertEquals(userEJB.usersToInsert(usersDTO).size(), 0);
	}
	
	@Test
	void testExistUsersToUpdate() {
		List<UserDTO> usersDTO = new ArrayList<>();

		UserDTO userDTO = createUserDTO();
		usersDTO.add(userDTO);

		userDTO = createUserDTO();
		userDTO.setUserId(2L);
		usersDTO.add(userDTO);
		
		Mockito.when(userDAO.findByUserId(1L)).thenReturn(createUser());
		Mockito.when(userDAO.findByUserId(2L)).thenReturn(null);
		
		assertEquals(userEJB.usersEntityToUpdate(usersDTO).size(), 1);
	}
}
