package application.services;

import application.config.JwtService;
import application.models.password_reset.PasswordReset;
import application.models.password_reset.PasswordResetRepository;
import application.models.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final MailService mailService;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetRepository passwordResetTokenRepository;

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, PasswordReset.EXPIRATION);
        return calendar.getTime();
    }

    public PasswordReset generateResetPasswordLink(User user) {
        String token = jwtService.generateToken(user);
        Date expiryDate = calculateExpiryDate();
        UUID userId = user.getId();

        PasswordReset passwordResetToken = PasswordReset.builder()
                .token(token)
                .userId(userId)
                .expiryDate(expiryDate)
                .build();

        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    public String generatePassword() {
        int PASSWORD_LENGTH = 8;
        StringBuilder passwordBuilder = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            String CHAR_SPECIAL = "<>,.?/{}[]()-=+_!@#$%^&*";
            String CHAR_NUMBERS = "0123456789";
            String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
            String CHAR_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String ALL_CHARS = CHAR_UPPERCASE + CHAR_LOWERCASE + CHAR_NUMBERS + CHAR_SPECIAL;
            passwordBuilder.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        return passwordEncoder.encode(passwordBuilder.toString());
    }

    public void sendPassword(String password, String email) {
        String emailBody = "Beste gebruiker,\n\n" +
                "Je tijdelijke wachtwoord is: " + password +
                "\nLet op, dit wachtwoord is een tijdelijk wachtwoord. Zodra je inlogt, wordt het verplicht gewijzigd.\n\n" +
                "Groetjes,\nSVDJ";

        mailService.sendMessage(email, "Tijdelijke wachtwoord", emailBody);
    }
}
