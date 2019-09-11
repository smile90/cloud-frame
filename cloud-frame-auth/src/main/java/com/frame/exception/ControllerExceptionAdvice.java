package com.frame.exception;

import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.exception.BaseRuntimeException;
import com.frame.user.enums.SystemMsgResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller异常处理
 * @author: duanchangqing90
 * @date: 2019/02/22
 */
@Controller
@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {


    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseBean bindExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        List<String> errors = new ArrayList<>();
        try {
            if (e instanceof BindException) {
                errors = ((BindException) e).getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            } else if (e instanceof MethodArgumentNotValidException) {
                errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            }

            if (log.isDebugEnabled()) {
                log.warn("bind exception. errors:{}", errors, e);
            } else {
                log.warn("bind exception. errors:{}", errors);
            }
        } catch (Exception ex) {
            log.error("get errors error", ex);
        }
        return ResponseBean.error(SystemMsgResult.SYSTEM_PARAM_ERROR).setShowMsg(errors.toString());
    }

    /**
     * 自定义业务异常捕获
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BaseRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseBean baseRuntimeExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("not process base runtime exception.", e);
        BaseRuntimeException be = (BaseRuntimeException) e;
        return ResponseBean.error().setCode(be.getErrorCode()).setMsg(be.getMessage()).setShowMsg(be.getShowMsg());
    }

    /**
     * 系统默认异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseBean defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("not process exception.", e);
        return ResponseBean.error(SystemMsgResult.SYSTEM_ERROR);
    }
}
