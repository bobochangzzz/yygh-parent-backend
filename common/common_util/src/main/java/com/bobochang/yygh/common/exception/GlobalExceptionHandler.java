package com.bobochang.yygh.common.exception;

import com.bobochang.yygh.common.result.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author bobochang
 * @description
 * @created 2022/6/29-20:23
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.fail();
    }
}
