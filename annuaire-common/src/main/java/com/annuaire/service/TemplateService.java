package com.annuaire.service;


import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

public class TemplateService {
    public static Map<String, String> getFinalTemplates(String location) {
        Map<String, StringTemplate> templates = getTemplates(location);

        return getGeneratedTemplates(templates);
    }

    public static Map<String, StringTemplate> getTemplates(String location) {
        StringTemplateGroup group = new StringTemplateGroup("email", location);

        Map<String, StringTemplate> templates = new HashMap<>();
        templates.put("birthday", group.getInstanceOf("birthday"));
        templates.put("christmas", group.getInstanceOf("christmas"));
        templates.put("default", group.getInstanceOf("default"));

        return templates;
    }

    public static Map<String, String> getGeneratedTemplates(Map<String, StringTemplate> templates) {
        Map<String, String> generatedTemplates = new HashMap<>();
        for (String key : templates.keySet()) {
            generatedTemplates.put(key, templates.get(key).toString());
        }

        return generatedTemplates;
    }

    public static StringTemplate parseTemplate(String location, String name) {
        Map<String, StringTemplate> templates = TemplateService.getTemplates(location);

        StringTemplate template = null;
        switch (name) {
            case "birthday":
                template = templates.get("birthday");
                break;
            case "christmas":
                template = templates.get("christmas");
                break;
            default:
                template = templates.get("default");
                break;
        }

        return template;
    }
}
