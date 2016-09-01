package com.yx.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * jsonP跨域 推荐使用此方式 有问题
 */
@ControllerAdvice
public class JsonPAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonPAdvice() {
        super("callback", "callback");
    }
}