package com.integer.quiz.gui.app.question.edit;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.DatasourceListener;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.gui.utils.PictureHelper;

import javax.inject.Inject;
import java.util.Map;

public class QuestionEditor extends AbstractEditor {

    @Inject
    private FileUploadField photoLoader;

    @Inject
    private BoxLayout photo;

    @Inject
    private Datasource<Question> questionDs;

    @Inject
    private CollectionDatasource fileDescriptorDs;

    @Inject
    private FieldGroup questionFieldGroup;

    @Inject
    private TextField answerTextField;

    @Inject
    private Button answerBtn;

    @Inject
    private CollectionDatasource answerDs;

    private PictureHelper emb;

    public QuestionEditor(IFrame frame) {
        super(frame);
    }

    @Override
    public void setItem(Entity item) {
        super.setItem(item);
        if (questionDs.getItem() != null && questionDs.getItem().getImage() != null) {
            emb.refreshPicture(questionDs.getItem().getImage());
        }
        //
    }

    @Override
    public void init(Map<String, Object> params) {

        //fileDescriptorDs.setQuery("select fd from sys$FileDescriptor fd");

        emb = new PictureHelper(photo, 300, 200);
        emb.initPictureControls();
        //emb.refreshPicture(questionDs.getItem().getImage());

        photoLoader.addListener(new FileUploadField.ListenerAdapter() {
            @Override
            public void uploadSucceeded(Event event) {
                FileUploadingAPI fileUploading = AppContext.getBean(FileUploadingAPI.NAME);
                try {
                    FileDescriptor descriptor = fileUploading.getFileDescriptor(photoLoader.getFileId(), photoLoader.getFileName());
                    fileUploading.putFileIntoStorage(photoLoader.getFileId(), descriptor);
                    questionDs.getItem().setImage(descriptor);
                    emb.refreshPicture(descriptor);
                } catch (FileStorageException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        questionDs.addListener(new DatasourceListener<Question>() {
            @Override
            public void itemChanged(Datasource<Question> ds, Question prevItem, Question item) {
            }

            @Override
            public void stateChanged(Datasource<Question> ds, Datasource.State prevState, Datasource.State state) {
            }

            @Override
            public void valueChanged(Question source, String property, Object prevValue, Object value) {
                if (value != null && property.equals("image")) {
                    //questionDs.getItem().setImage((FileDescriptor)value);
                    emb.refreshPicture((FileDescriptor) value);
                }
            }
        });

        answerBtn.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                if (answerTextField.getValue() != null) {
                    Answer answer = new Answer();
                    answer.setContent((String) answerTextField.getValue());
                    answerDs.addItem(answer);
                    answerDs.commit();
                    answerDs.refresh();
                }
            }
        });
    }
}
