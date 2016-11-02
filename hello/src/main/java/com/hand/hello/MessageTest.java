package com.hand.hello;


import com.hand.util.KieSessionUtil;

import org.junit.Test;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class MessageTest {

    //无状态的调用
    @Test
    public void testHello() throws IOException {
        String url = "http://localhost:8080/kie-drools-wb/maven2/com/hand/hello/1.0/hello-1.0.jar";

        StatelessKieSession kSession = KieSessionUtil.getStatelessKieSession(url);
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

        KieSession kieSession = KieSessionUtil.getStatefulKieSession(url);
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
