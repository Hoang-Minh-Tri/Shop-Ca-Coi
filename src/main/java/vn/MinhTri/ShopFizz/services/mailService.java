package vn.MinhTri.ShopFizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;

@Service
public class mailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        try {
            // Tạo MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Thiết lập thông tin email
            helper.setFrom("2251120058@ut.edu.vn");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // content có thể chứa HTML

            // Gửi email
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailRequest(Product pr) {
        User user = pr.getUser();
        String email = user.getEmail();
        String text = "Yêu cầu phê duyệt sản phẩm " + pr.getName()
                + " của bạn đã được phê duyệt, hãy kiểm tra lại sản phẩm của bạn" + "<br> Xin cám ơn";
        this.sendEmail(email, "Phê duyệt thành công", text);
    }

    public void sendEmailDelete(Product pr) {
        User user = pr.getUser();
        String email = user.getEmail();
        String text = "Yêu cầu phê duyệt sản phẩm " + pr.getName()
                + " của bạn bị từ chối, hãy kiểm tra lại sản phẩm của bạn" + "<br> Xin cám ơn";
        this.sendEmail(email, "Phê duyệt thất bại", text);
    }

    public void sendEmailOrder(User user) {
        String email = user.getEmail();
        String text = "Đơn hàng của bạn đã được xác nhận <br>Cám ơn bạn đã tin tưởng shop.";
        this.sendEmail(email, "Xác nhận đơn hàng", text);
    }
}
