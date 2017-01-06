package com.hjz.exception.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjz.exception.ServiceException;
import com.hjz.exception.utils.ExceptionUtils;

@ControllerAdvice(basePackages="com.xxx")
@ResponseBody
public class ExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e 异常对象
     * @return 异常提示
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        //不需要再记录ServiceException，因为在service异常切面中已经记录过
        if (!(e instanceof ServiceException)) {
            LOGGER.error(ExceptionUtils.getExcTrace(e));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "text/plain;charset=UTF-8");
        headers.add("icop-content-type", "exception");
        String message = StringUtils.isEmpty(e.getMessage()) ? "系统异常!!" : e.getMessage();
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
