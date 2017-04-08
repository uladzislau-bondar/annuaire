package com.annuaire.service;


import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

public class TemplateService {
    public static Map<String, StringTemplate> getTemplates(StringTemplateGroup group){
        Map<String, StringTemplate> templates = new HashMap<>();
        templates.put("birthday", group.getInstanceOf("birthday"));
        templates.put("christmas", group.getInstanceOf("christmas"));
        templates.put("default", group.getInstanceOf("default"));

        return templates;
    }

    public static Map<String, String> getGeneratedTemplates(Map<String, StringTemplate> templates){
        Map<String, String> generatedTemplates = new HashMap<>();
        for (String key: templates.keySet()) {
            generatedTemplates.put(key, templates.get(key).toString());
        }

        return generatedTemplates;
    }

    public static StringTemplateGroup retrieveStringTemplateGroup(String location){
        return new StringTemplateGroup("email", location);
    }

    public static StringTemplate parseTemplate(String location, String name){
        StringTemplateGroup group = TemplateService.retrieveStringTemplateGroup(location);
        Map<String, StringTemplate> templates = TemplateService.getTemplates(group);

        StringTemplate template = null;
        switch (name){
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
