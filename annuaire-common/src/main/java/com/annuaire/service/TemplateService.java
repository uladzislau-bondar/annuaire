package com.annuaire.service;


import org.antlr.stringtemplate.StringTemplateGroup;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.compiler.CompiledST;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class TemplateService {
    public static Map<String, String> getFinalTemplates(String location) {
        Map<String, ST> templates = getTemplates(location);
        return getGeneratedTemplates(templates);
    }

    public static Map<String, ST> getTemplates(String location) {
        STGroup group = new STGroupFile(location, "UTF-8");

        Map<String, ST> templates = new HashMap<>();
        Set<String> templateNames = group.getTemplateNames();
        for (String name : templateNames) {
            name = name.substring(1);
            templates.put(name, group.getInstanceOf(name));
        }

        return templates;
    }

    public static Map<String, String> getGeneratedTemplates(Map<String, ST> templates) {
        Map<String, String> generatedTemplates = new HashMap<>();
        for (String key : templates.keySet()) {
            generatedTemplates.put(key, templates.get(key).render());
        }

        return generatedTemplates;
    }

    public static ST parseTemplate(String location, String name) {
        Map<String, ST> templates = TemplateService.getTemplates(location);

        return templates.get(name);
    }
}
