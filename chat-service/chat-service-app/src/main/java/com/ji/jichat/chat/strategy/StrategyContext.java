package com.ji.jichat.chat.strategy;


import com.ji.jichat.chat.strategy.receive.NoCommandProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 根据CommandCode自动加入到ProcessorContext中，实现策略模式
 */
@Slf4j
@Component
public class StrategyContext {

    private Map<String, CommandStrategy> commandStrategyMap;

    // spring中，在使用@Autowired注解注入list集合的时候，并不会根据List类型去容器中查找，而是根据list集合的元素类型，从spring容器中找到所有的实现类，放在list集合中，然后注入到bean中
    @Resource
    private List<CommandStrategy> commandStrategyList;

    @Resource
    private NoCommandProcessor noCommandProcessor;


    @PostConstruct
    public void buildMap() {
//        将来完善下，可以加入是否禁用等
        commandStrategyMap = commandStrategyList.stream()
            .collect(Collectors.toMap(operationStrategy -> operationStrategy.getCommandCode().getCode(), Function.identity()));
    }

    public CommandStrategy getProcessor(String code) {
        return commandStrategyMap.getOrDefault(code, noCommandProcessor);
    }


}
