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

    @Test
    void createFlexChooseCategory() {
        FlexMessage flexMessage = botTemplate.createFlexChooseCategory();
        assertTrue(flexMessage instanceof FlexMessage);
    }
}