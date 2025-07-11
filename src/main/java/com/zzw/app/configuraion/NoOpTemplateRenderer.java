package com.zzw.app.configuraion;

import org.springframework.ai.template.TemplateRenderer;

import java.util.Map;

public class NoOpTemplateRenderer implements TemplateRenderer {
    @Override
    public String apply(String template, Map<String, Object> variables) {
        return template;  // 👈 原样返回，不做渲染
    }
}