package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.entity.*;
import ru.gb.repository.RoleRepository;
import ru.gb.repository.TimesheetRepository;
import ru.gb.repository.UserRepository;
import ru.gb.repository.UserRoleRepository;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		TimesheetRepository timesheetRepository = ctx.getBean(TimesheetRepository.class);

		timesheetRepository.save(new Timesheet());

		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);

		Role roleAdmin = new Role("admin");
		Role roleUser = new Role("user");
		Role roleRest = new Role("rest");

		roleAdmin = roleRepository.save(roleAdmin);
		roleUser = roleRepository.save(roleUser);
		roleRest = roleRepository.save(roleRest);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("hashed_admin");
		admin.setRoles(List.of(roleAdmin, roleUser));

		admin = userRepository.save(admin);

		User user = new User();
		user.setLogin("user");
		user.setPassword("hashed_user");
		user.setRoles(List.of(roleUser));

		user = userRepository.save(user);

		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("hashed_rest");
		rest.setRoles(List.of(roleRest));

		rest = userRepository.save(rest);

		UserRoleId adminId = new UserRoleId();

		adminId.setUserId(admin.getUserId());
		adminId.setRoleId(roleAdmin.getRoleId());

		UserRoleId userId = new UserRoleId();

		userId.setUserId(user.getUserId());
		userId.setRoleId(roleUser.getRoleId());

		UserRoleId restId = new UserRoleId();

		restId.setUserId(rest.getUserId());
		restId.setRoleId(roleRest.getRoleId());

		UserRole userRoleAdmin = new UserRole();

		userRoleAdmin.setId(adminId);
		userRoleAdmin.setUser(admin);
		userRoleAdmin.setRole(roleAdmin);

		UserRole userRoleUser = new UserRole();

		userRoleUser.setId(userId);
		userRoleUser.setUser(user);
		userRoleUser.setRole(roleUser);

		UserRole userRoleRest = new UserRole();

		userRoleRest.setId(restId);
		userRoleRest.setUser(rest);
		userRoleRest.setRole(roleRest);

		userRoleRepository.save(userRoleAdmin);
		userRoleRepository.save(userRoleUser);
		userRoleRepository.save(userRoleRest);
	}

}
