package Servlet;

import DB.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


@WebServlet("/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //创建验证码类
        VerifyCode vc = new VerifyCode();
        // 获取随机图片
        BufferedImage image = vc.getImage();
        //保存图片上的文本到session域中
        request.getSession().setAttribute("vCode", vc.getText());
        //把图片响应给客户端
        VerifyCode.output(image, response.getOutputStream());
    }
}
