/*
 * Copyright (c) 2013 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.integer.quiz.gui.app.score.edit;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.IFrame;
import com.haulmont.cuba.core.entity.Entity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: developer
 * Date: 27.08.13
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class ScoreEditor extends AbstractEditor {

    public ScoreEditor(IFrame frame) {
        super(frame);
    }

    @Override
    public void setItem(Entity item) {
        super.setItem(item);
        //
    }

    @Override
    public void init(Map<String, Object> params) {
        //
    }
}
