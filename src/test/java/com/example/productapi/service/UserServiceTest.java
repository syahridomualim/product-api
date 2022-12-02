package com.example.productapi.service;

import com.example.productapi.entity.User;
import com.example.productapi.repository.UserRepository;
import com.example.productapi.service.user.UserService;
import com.example.productapi.service.user.UserServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Autowired
        private UserRepository userRepository;

        @Bean
        public UserService userService() {
            return new UserServiceImp(userRepository, new BCryptPasswordEncoder());
        }
    }

    @MockBean
    UserRepository userRepository;
    @Autowired
    UserService userService;

    private List<User> users = Arrays.asList(
            new User("mualim@mail.com", "12345678"),
            new User("syahrido@mail.com", "12345678"),
            new User("adi@mail.com", "12345678")
    );

    @Before
    public void setUp() {
        User mualim = users.get(0);
        User syahrido = users.get(1);
        User adi = users.get(2);

        userRepository.saveAll(users);

        Mockito.when(userRepository.findByEmail(mualim.getEmail())).thenReturn(Optional.of(mualim));
        Mockito.when(userRepository.findByEmail(syahrido.getEmail())).thenReturn(Optional.of(syahrido));
        Mockito.when(userRepository.findByEmail(adi.getEmail())).thenReturn(Optional.of(adi));
        Mockito.when(userRepository.findByEmail("wrong@mail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }


    @Test
    public void whenValidIdThenUserShouldBeFoundTest() {
        String id = "mualim@mail.com";
        User user = userService.findUserByEmail(id).orElseThrow(() -> new NoSuchElementException(":("));

        Assertions.assertEquals(id, user.getUsername());
        verifyFindByIdIsCalledOnce(id);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenInValidIdThenUserShouldBeFoundTest() {
        String id = "wrong@mail.com";
        boolean existUser = userService.findUserByEmail(id).isPresent();
        Assertions.assertFalse(existUser);

        verifyFindByIdIsCalledOnce(id);
    }

    @Test
    public void giveThreeUsersTest() {
        List<User> threeUsers = userService.getUsers();
        Assertions.assertEquals(users, threeUsers);
        Assertions.assertTrue(threeUsers.contains(users.get(0)));
        verifyFindAllEmployeeCalledOnce();
    }

    private void verifyFindByIdIsCalledOnce(String id) {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByEmail(id);
        Mockito.reset(userRepository);
    }

    private void verifyFindAllEmployeeCalledOnce() {
        Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(userRepository);
    }

}
