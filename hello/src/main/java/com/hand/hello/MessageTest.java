package com.hand.hello;


import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.io.impl.UrlResource;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageTest {

    //无状态的调用
    @Test
    public void testHello() throws IOException {
        String url = "http://localhost:8080/kie-drools-wb/maven2/com/hand/hello/1.0/hello-1.0.jar";
        ReleaseIdImpl releaseId = new ReleaseIdImpl("org.sky.drools", "hello", "LATEST");
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);
        urlResource.setUsername("tomcat");
        urlResource.setPassword("tomcat");
        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();
        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        StatelessKieSession kSession = kContainer.newStatelessKieSession("defaultStatelessKieSession");
        try {

            List<Message> messages = new ArrayList<Message>();
            for(int i=0; i<3; i++){
                Message message = new Message();
                message.setMessage("Hello World");
                message.setStatus(Message.HELLO);
                messages.add(message);
            }
            // go !
            for (Message message: messages) {
               kSession.execute(message);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //有状态的调用
    @Test
    public void testStatsful() throws IOException {
        String url = "http://localhost:8080/kie-drools-wb/maven2/com/hand/hello/1.0/hello-1.0.jar";

        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);
        urlResource.setUsername("tomcat");
        urlResource.setPassword("tomcat");
        urlResource.setBasicAuthentication("enabled");
        InputStream is = urlResource.getInputStream();
        KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
        KieSession kieSession = kContainer.newKieSession();
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
        try{
            kieSession.insert(message);
            kieSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }finally{
            kieSession.dispose();
        }
    }
    }
