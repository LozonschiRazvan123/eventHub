package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	
}