package com.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.util.List;

/**
 * drools工具
 *
 * @author 李磊
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class DroolsUtil {

    /**
     * 从drl内容构建KieSession
     *
     * @param drlContent
     * @return
     */
    public KieSession createKieSessionByDrlContent(String drlContent) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drlContent, ResourceType.DRL);
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            StringBuilder errorContent = new StringBuilder();
            for (Message message : messages) {
                errorContent.append(message.getText() + '\n');
            }
            throw new RuntimeException(errorContent.toString());
        }
        return kieHelper.build().newKieSession();
    }
}