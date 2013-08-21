package com.integer.quiz.gui.app.answer.browse;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.TimeProvider;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.integer.quiz.entity.Answer;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;

public class AnswerBrowser extends AbstractLookup {

    @Inject
    private Table table;

    @Inject
    private Button saveBtn;

    @Inject
    private FileUploadField answerLoader;

    @Inject
    private CollectionDatasource answerDs;

    public AnswerBrowser(IFrame frame) {
        super(frame);
    }

    @Override
    public void init(Map<String, Object> params) {
        table.addAction(new CreateAction(table));
        table.addAction(new EditAction(table));
        table.addAction(new RefreshAction(table));
        table.addAction(new RemoveAction(table));

        saveBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                answerDs.commit();
                answerDs.refresh();
            }
        });

        answerLoader.addListener(new FileUploadField.Listener() {

            @Override
            public void uploadStarted(Event event) {

            }

            @Override
            public void uploadFinished(Event event) {

            }

            @Override
            public void uploadSucceeded(Event event) {
                String ext = getFileExt(answerLoader.getFileName());
                if (ext.equals("txt")) {
                    FileUploadingAPI fileUploading = AppContext.getBean(FileUploadingAPI.NAME);
                    FileDescriptor descriptor = fileUploading.getFileDescriptor(answerLoader.getFileId(), answerLoader.getFileName());
                    try {
                        File file = fileUploading.getFile(answerLoader.getFileId());
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file), "UTF8"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            Answer answer = new Answer();
                            answer.setContent(line);
                            answerDs.addItem(answer);
                        }
                        reader.close();
                        answerDs.commit();
                        answerDs.refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showNotification(getMessage("Quiz.WrongExt"), NotificationType.WARNING);
                }
            }

            @Override
            public void uploadFailed(Event event) {
            }
        });
    }

    private String getFileExt(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > -1)
            return StringUtils.substring(fileName, i + 1, i + 20);
        else
            return "";
    }
}
