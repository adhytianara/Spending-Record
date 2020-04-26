package com.tkap11.spendingrecord.service;

import com.linecorp.bot.model.message.FlexMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTemplateTest {
    private final BotTemplate botTemplate = new BotTemplate();

    @Test
    void createFlexMenu() {
        FlexMessage flexMessage = botTemplate.createFlexMenu();
        assertTrue(flexMessage instanceof FlexMessage);
    }

    void createFlexSisa() {
        FlexMessage flexMessage = botTemplate.createFlexSisa();
        assertTrue(flexMessage instanceof FlexMessage);
    }
}