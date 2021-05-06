package com.example.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Drools工具
 *
 * @author 李磊
 */
@Slf4j
@UtilityClass
public class DroolsUtil {

    private final Map<String, String> DECISION_TABLE_CACHE = new ConcurrentHashMap<>();

    /**
     * 从drl内容构建KieSession
     *
     * @param drlContent
     * @param verifyFlag 是否需要验证
     * @return
     */
    public KieSession createKieSessionByDrlContent(String drlContent, boolean verifyFlag) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drlContent, ResourceType.DRL);
        if (verifyFlag) {
            Results results = kieHelper.verify();
            if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
                List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
                StringBuilder errorContent = new StringBuilder();
                for (Message message : messages) {
                    errorContent.append(message.getText() + '\n');
                }
                throw new RuntimeException(errorContent.toString());
            }
        }
        return kieHelper.build().newKieSession();
    }

    public <T> void calcCommon(T r, String decisionTablePath) {
        String drlContent;
        boolean containsFlag = DECISION_TABLE_CACHE.containsKey(decisionTablePath);
        if (containsFlag) {
            drlContent = DECISION_TABLE_CACHE.get(decisionTablePath);
        } else {
            SpreadsheetCompiler compiler = new SpreadsheetCompiler();
            Resource resource = ResourceFactory.newClassPathResource(decisionTablePath + ".xlsx");
            drlContent = compiler.compile(resource, InputType.XLS);
            DECISION_TABLE_CACHE.put(decisionTablePath, drlContent);
        }
        KieSession kieSession = DroolsUtil.createKieSessionByDrlContent(drlContent, !containsFlag);
        // 将obj作为参数传入Drools执行
        kieSession.insert(r);
        // 加载规则
        kieSession.fireAllRules();
        // 关闭会话
        kieSession.dispose();
    }
}