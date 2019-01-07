package com.frame.user.exception;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.exception.BaseRuntimeException;
import com.frame.user.constant.SystemConstant;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.SystemMsgResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 全局异常处理
 * @author: duanchangqing90
 * @date: 2019/01/07
 */
@Slf4j
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        ResponseBean responseBean = null;
        if (e instanceof BaseRuntimeException) {
            responseBean = ResponseBean.getInstance(((BaseRuntimeException) e).getErrorCode(), e.getMessage(), ((BaseRuntimeException) e).getShowMsg(), null);
        } else if (e.getCause() instanceof BaseRuntimeException) {
            responseBean = ResponseBean.getInstance(((BaseRuntimeException) e.getCause()).getErrorCode(), e.getCause().getMessage(), ((BaseRuntimeException) e.getCause()).getShowMsg(), null);
        } else {
            responseBean = ResponseBean.getInstance(SystemMsgResult.SYSTEM_ERROR);
        }

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(responseBean));
        } catch (Exception ioe) {
            log.error("system error.", ioe);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
