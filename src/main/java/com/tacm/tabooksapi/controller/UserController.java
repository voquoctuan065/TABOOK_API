package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ChangePasswordDto;
import com.tacm.tabooksapi.domain.dto.UserDto;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private JavaMailSender javaMailSender;

    @Autowired
    public UserController(UserService userService,
                          JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileByJwt(@RequestHeader("Authorization") String token) throws UserException {
        Users users = userService.findUserProfileByJwt(token);
        return new ResponseEntity<>(UserDto.fromEntity(users), HttpStatus.OK);
    }

    @PostMapping("/forgot_password/{email}")
    public ResponseEntity<String> processForgotPasswordForm(@PathVariable String email) throws UserException, MessagingException, UnsupportedEncodingException {
        String token = RandomString.make(45);

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = "http://localhost:3000/reset_password?token=" + token;

        sendEmail(email, resetPasswordLink);

        return ResponseEntity.ok("Link reset sent to email");
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("20t1020624@husc.edu.vn", "TaBook support");
        helper.setTo(email);

        String subject = "Here's link to reset your password";
        String content = "<p>Hello, <p>"
                + "You have requested to reset your password <br/>"
                + "Click the link below to change your password"
                +"<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                +"Please ignore this email if you do remember your password ";
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @PostMapping("/user_by_reset_token")
    public ResponseEntity<UserDto> getUserByResetToken(@RequestParam("token") String token) throws UserException {
        Users users = userService.get(token);
        return new ResponseEntity<>(UserDto.fromEntity(users), HttpStatus.OK);
    }

    @PutMapping("/reset_password")
    public ResponseEntity<String> processResetPasswordForm(@RequestParam("token") String token,
                                                            @RequestBody ChangePasswordDto changePasswordDto) throws UserException {
        Users users = userService.get(token);
        if(users == null) {
            return ResponseEntity.ok("-1");
        }

        if(!Objects.equals(changePasswordDto.getPassword(), changePasswordDto.getRePassword())) {
            return ResponseEntity.ok("0");
        } else {
            userService.updatePassword(users, changePasswordDto.getPassword());
            return ResponseEntity.ok("1");
        }
    }

}
