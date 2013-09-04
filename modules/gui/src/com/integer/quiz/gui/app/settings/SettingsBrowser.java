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
    private Button postBtn;

    @Inject
    private Button refreshAnswersBtn;

    @Inject
    private TextField hostTextField;

    @Inject
    private TextField portTextField;

    @Inject
    private TextField imagePortTextField;

    @Inject
    private TextField emailTextField;

    @Inject
    private TextField passwordTextField;

    @Inject
    private TextField postHostTextField;

    @Inject
    private TextField postPortTextField;

    @Inject
    private TextField postSubjectTextField;

    @Inject
    private TextField postTextField;

    @Inject
    private QuizDataService quizDataService;

    @Override
    public void init(Map<String, Object> params) {

        HashMap<String,String> connectionMap = quizDataService.getConnectionSettings();
        HashMap<String,String> mailMap = quizDataService.getMailSettings();

        hostTextField.setValue(connectionMap.get("host"));
        portTextField.setValue(connectionMap.get("port"));
        imagePortTextField.setValue(connectionMap.get("imagePort"));

        emailTextField.setValue(mailMap.get("email"));
        passwordTextField.setValue(mailMap.get("password"));
        postHostTextField.setValue(mailMap.get("host"));
        postPortTextField.setValue(mailMap.get("port"));
        postSubjectTextField.setValue(mailMap.get("subject"));
        postTextField.setValue(mailMap.get("text"));

        csBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                String host = (String)hostTextField.getValue();
                String port = (String)portTextField.getValue();
                String imagePort = (String)imagePortTextField.getValue();
                quizDataService.setConnectionSettings(host,port,imagePort);

            }
        });

        postBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                String mail = (String)emailTextField.getValue();
                String password = (String)passwordTextField.getValue();
                String host = (String)postHostTextField.getValue();
                String port = (String)postPortTextField.getValue();
                String subject = (String)postSubjectTextField.getValue();
                String text = (String)postTextField.getValue();
                quizDataService.setMailSettings(mail,password,host,port,subject,text);

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
