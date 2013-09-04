/*
 * Copyright (c) 2013 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.integer.quiz.gui.app.scoreHistory.browse;

import java.util.Map;

import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.*;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: developer
 * Date: 04.09.13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class ScoreHistoryBrowser extends AbstractLookup {

    @Inject
    private Table table;

    public ScoreHistoryBrowser(IFrame frame) {
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
