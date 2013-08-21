package com.integer.quiz.gui.app.settings;

import com.haulmont.cuba.gui.components.*;
import com.integer.quiz.service.QuizDataService;

import javax.inject.Inject;
import java.util.Map;

public class SettingsBrowser extends AbstractLookup {

    @Inject
    private Button csBtn;

    @Inject
    private TextField connectionStringTextField;

    @Inject
    private QuizDataService quizDataService;

    @Override
    public void init(Map<String, Object> params) {

        connectionStringTextField.setValue(quizDataService.getConnectionString());
        csBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                if (connectionStringTextField.getValue() != null) {
                    quizDataService.setConnectionString((String)connectionStringTextField.getValue());
                }
            }
        });
    }
}
