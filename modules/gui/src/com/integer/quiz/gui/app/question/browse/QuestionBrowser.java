package com.integer.quiz.gui.app.question.browse;

import java.util.Map;

import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.*;

import javax.inject.Inject;


public class QuestionBrowser extends AbstractLookup {

    @Inject
    private Table table;

    public QuestionBrowser(IFrame frame) {
        super(frame);
    }

    @Override
    public void init(Map<String, Object> params) {
        table.addAction(new CreateAction(table));
        table.addAction(new EditAction(table));
        table.addAction(new RefreshAction(table));
        table.addAction(new RemoveAction(table));
    }
}
