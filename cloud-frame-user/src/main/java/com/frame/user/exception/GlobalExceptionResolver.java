package com.frame.user.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.exception.BaseRuntimeException;
import com.frame.user.constant.SystemConstant;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.SystemMsgResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * @author: duanchangqing90
 * @date: 2019/01/07
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        if (e instanceof BaseRuntimeException) {
            mv.addObject(ResponseBean.getInstance(((BaseRuntimeException) e).getErrorCode(), e.getMessage(), ((BaseRuntimeException) e).getShowMsg(), null));
        } else if (e.getCause() instanceof BaseRuntimeException) {
            mv.addObject(ResponseBean.getInstance(((BaseRuntimeException) e.getCause()).getErrorCode(), e.getCause().getMessage(), ((BaseRuntimeException) e.getCause()).getShowMsg(), null));
        } else {
            mv.addObject(ResponseBean.getInstance(SystemMsgResult.SYSTEM_ERROR));
        }
        mv.setView(view);
        return mv;
    }
}
