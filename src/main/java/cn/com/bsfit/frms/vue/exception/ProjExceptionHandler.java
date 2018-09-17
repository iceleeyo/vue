package cn.com.bsfit.frms.vue.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bsfit.frms.vue.dao.ErrorInfo;

@ControllerAdvice
public class ProjExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        ModelAndView mav = new ModelAndView();

        mav.addObject("exception", e);

        mav.addObject("url", req.getRequestURL());

        mav.setViewName("error");

        return mav;
    }

    @ExceptionHandler(value = ProjException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, ProjException e) throws Exception {

        ErrorInfo<String> r = new ErrorInfo<>();

        r.setMessage(e.getMessage());

        r.setCode(ErrorInfo.ERROR);

        r.setData("Some Data");

        r.setUrl(req.getRequestURL().toString());

        return r;
    }

}