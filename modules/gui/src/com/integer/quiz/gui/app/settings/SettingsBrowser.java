package com.integer.quiz.gui.app.settings;

import com.haulmont.cuba.gui.components.*;
import com.integer.quiz.service.QuizDataService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SettingsBrowser extends AbstractLookup {

    @Inject
    private Button csBtn;

    @Inject
    private Button refreshAnswersBtn;

    @Inject
    private TextField hostTextField;

    @Inject
    private TextField portTextField;

    @Inject
    private TextField imagePortTextField;

    @Inject
    private QuizDataService quizDataService;

    @Override
    public void init(Map<String, Object> params) {

        HashMap<String,String> connectionMap = quizDataService.getConnectionSettings();
        hostTextField.setValue(connectionMap.get("host"));
        portTextField.setValue(connectionMap.get("port"));
        imagePortTextField.setValue(connectionMap.get("imagePort"));
        csBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                String host = (String)hostTextField.getValue();
                String port = (String)portTextField.getValue();
                String imagePort = (String)imagePortTextField.getValue();
                quizDataService.setConnectionSettings(host,port,imagePort);

            }
        });
        refreshAnswersBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                quizDataService.refreshAnswerList();
            }
        });
    }
}
