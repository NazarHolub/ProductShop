package app.mail;

public interface IEmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendMyMessage(String to,
                       String subject,
                       String text);
}
