/*
 * Copyright (c) 2013 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.integer.quiz.gui.app.score.browse;

import java.util.Map;

import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.*;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: developer
 * Date: 27.08.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class ScoreBrowser extends AbstractLookup {

    @Inject
    private Table table;

    public ScoreBrowser(IFrame frame) {
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
