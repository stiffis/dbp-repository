package com.purrComplexity.TrabajoYa.User.Repository;

import com.purrComplexity.TrabajoYa.User.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);

    Long id(Long id);

    Long id(Long id);
}
