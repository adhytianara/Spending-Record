package com.tkap11.spendingrecord.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class BotTemplate {


    public FlexMessage createFlexMenu() {
        FlexMessage flexMessage=new FlexMessage("Action Menu", null);
        try {
            ClassLoader classLoader=getClass().getClassLoader();
            String encoding=StandardCharsets.UTF_8.name();
            String flexTemplate=IOUtils.toString(Objects.requireNonNull(
                    classLoader.getResourceAsStream("menu.json")), encoding);
            ObjectMapper objectMapper=ModelObjectMapper.createNewObjectMapper();
            FlexContainer flexContainer=objectMapper.readValue(flexTemplate, FlexContainer.class);
            flexMessage=new FlexMessage("Action Menu", flexContainer);
        } catch (IOException e){
            e.printStackTrace();
        }
        return flexMessage;
    }

    public FlexMessage createFlexSisa() {
        FlexMessage flexMessage=new FlexMessage("Sisa Pengeluaran", null);
        try {
            ClassLoader classLoader=getClass().getClassLoader();
            String encoding=StandardCharsets.UTF_8.name();
            String flexTemplate=IOUtils.toString(Objects.requireNonNull(
                    classLoader.getResourceAsStream("sisaBudget.json")), encoding);

            ObjectMapper objectMapper=ModelObjectMapper.createNewObjectMapper();
            FlexContainer flexContainer=objectMapper.readValue(flexTemplate, FlexContainer.class);
            flexMessage=new FlexMessage("Sisa Pengeluaran", flexContainer);
        } catch (IOException e){
            e.printStackTrace();
        }
        return flexMessage;
    }
}
