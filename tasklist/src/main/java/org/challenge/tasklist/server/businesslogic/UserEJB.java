package org.challenge.tasklist.server.businesslogic;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.challenge.tasklist.model.dto.UserDTO;
import org.challenge.tasklist.model.jpa.User;
import org.challenge.tasklist.server.dao.UserDAO;
import org.challenge.tasklist.server.rest.exception.FieldRequiredException;

@Stateless
public class UserEJB {

	private UserDAO userDAO;
	private DozerMapperEJB dozerMapperEJB;
	
	protected UserEJB() {}
	
	@Inject
	public UserEJB(UserDAO userDAO, DozerMapperEJB dozerMapperEJB) {
		this.userDAO = userDAO;
		this.dozerMapperEJB = dozerMapperEJB;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void checkRequiredUserFields(List<UserDTO> usersDTO) throws FieldRequiredException {
		if(usersDTO != null && !usersDTO.isEmpty()) {
			usersDTO.parallelStream()
				.filter(user -> user.getUserId() == null 
						&& StringUtils.isNotBlank(user.getFirstName()) 
						&& StringUtils.isNotBlank(user.getLastName()))
				.findFirst()
				.orElseThrow(() -> new FieldRequiredException("user fields required are missing"));
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> usersEntityToUpdate(List<UserDTO> usersDTO) {
		return usersDTO.parallelStream()
					.filter(user -> user.getUserId() != null)
					.map(user -> userDAO.findByUserId(user.getUserId()))
					.filter(user -> user != null)
					.collect(Collectors.toList());
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<User> usersToInsert(List<UserDTO> usersDTO) {
		return usersDTO.parallelStream()
				.filter(user -> user.getUserId() == null)
				.map(user -> dozerMapperEJB.map(user, User.class))
				.collect(Collectors.toList());
	}
}
