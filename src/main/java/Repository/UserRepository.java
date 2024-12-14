package Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	public List<User> findUserByRole(String role);
    public Optional<User> findById(Long id); 

}