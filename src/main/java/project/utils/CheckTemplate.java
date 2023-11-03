package project.utils;

import project.entity.Template;
import project.exceptions.TemplateNotFoundException;

import java.util.Optional;

public class CheckTemplate {

    private CheckTemplate() {}

    public static void checkTemplate(Optional<Template> template) { //CheckTemplate
            if(template.isEmpty()) {
                throw new TemplateNotFoundException("Такого шаблона нет id : " + template.get().getTemplateId());
            }
        }
}
