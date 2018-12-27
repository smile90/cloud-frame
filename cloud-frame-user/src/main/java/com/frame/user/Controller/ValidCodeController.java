package com.frame.user.controller;

import com.frame.user.service.ValidCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Slf4j
@Controller
@RequestMapping("/validCode")
public class ValidCodeController {

    @Autowired
    private ValidCodeService validCodeService;

    private void setValidCodeResponse(HttpServletResponse resp) {
        // Set to expire far in the past.
        resp.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        resp.setHeader("Pragma", "no-cache");
        // return a jpeg
        resp.setContentType("image/jpeg");
    }

    @RequestMapping("/login")
    public void loginValidCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置响应结果
        setValidCodeResponse(resp);

        // 生成验证码
        String capText = validCodeService.createValidCode(req.getSession().getId());

        // 响应图片
        ServletOutputStream out = null;
        try {
            out = resp.getOutputStream();
            ImageIO.write(validCodeService.validCodeToImg(capText), "jpg", out);
        } catch (IOException e) {
            log.error("produce valid code error.", e);
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("produce valid code error.", e);
                }
            }
        }
    }
}
