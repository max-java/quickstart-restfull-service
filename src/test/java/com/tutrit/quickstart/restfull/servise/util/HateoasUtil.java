package com.tutrit.quickstart.restfull.servise.util;

import org.springframework.hateoas.RepresentationModel;

import java.net.URI;

public class HateoasUtil {

    public static URI extractHrefAsUri(RepresentationModel model, String relation) {
        String link = model.getRequiredLink(relation).getHref();
        if (link.contains("{")) {
            return URI.create(link.substring(0, link.indexOf("{")));
        }
        return URI.create(link);
    }
}
