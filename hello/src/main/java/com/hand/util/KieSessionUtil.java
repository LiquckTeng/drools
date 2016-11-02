package com.hand.util;


import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.IOException;
import java.io.InputStream;

public class KieSessionUtil {

    private static String USERNAME = "tomcat";
    private static String PASSWORD = "tomcat";

    public static KieSession    getStatefulKieSession(String jarUrl) throws IOException {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(jarUrl);
        urlResource.setUsername(USERNAME);
        urlResource.setPassword(PASSWORD);
        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();
        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        return kContainer.newKieSession();
    }

    public static StatelessKieSession getStatelessKieSession(String jarUrl) throws IOException {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(jarUrl);
        urlResource.setUsername(USERNAME);
        urlResource.setPassword(PASSWORD);
        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();
        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        return kContainer.newStatelessKieSession("defaultStatelessKieSession");
    }
}
