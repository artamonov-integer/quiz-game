package com.integer.quiz.gui.app.question.edit;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.DatasourceListener;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.gui.utils.PictureHelper;
import com.integer.quiz.service.QuizImageService;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;
import java.util.UUID;

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

    @Inject
    private QuizImageService quizImageService;

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
                String ext = getFileExt(photoLoader.getFileName());
                if (ext.equals("jpg") || ext.equals("png")) {
                    FileUploadingAPI fileUploading = AppContext.getBean(FileUploadingAPI.NAME);
                    try {
                        FileDescriptor descriptor = fileUploading.getFileDescriptor(photoLoader.getFileId(), photoLoader.getFileName());
                        File file = fileUploading.getFile(photoLoader.getFileId());
                        if (file != null) {
                            //addNewImage(file);
                            //Boolean isUpdated = quizImageService.saveImage(file,file);
                            //fileUploading.putFileIntoStorage(photoLoader.getFileId(), descriptor);
                            //Image img =
                            //if (isUpdated) {
                            FileDescriptor imageLow = getNewImage(file, 10, ext);
                            questionDs.getItem().setImage(imageLow);
                            questionDs.getItem().setImageMid(getNewImage(file, 20, ext));
                            questionDs.getItem().setImageHigh(getNewImage(file, 30, ext));

                            emb.refreshPicture(imageLow);
                            //fileUploading.putFileIntoStorage(photoLoader.getFileId(), descriptor);
                            //}
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    showNotification(getMessage("Quiz.WrongImageExt"), NotificationType.WARNING);
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

    private String getFileExt(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > -1)
            return StringUtils.substring(fileName, i+1);
        else
            return "";
    }

    public FileDescriptor getNewImage(File file, Integer quality,String ext) {
        FileDescriptor newFd = null;
        try {
            FileUploadingAPI fileUploading = AppContext.getBean(FileUploadingAPI.NAME);
            UUID newFileId = fileUploading.createEmptyFile();
            File newFile = fileUploading.getFile(newFileId);
            quizImageService.saveImage(file, newFile, quality, ext);
            newFd = fileUploading.getFileDescriptor(newFileId, newFile.getName() + "." + ext);
            fileUploading.putFileIntoStorage(newFileId, newFd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFd;
    }
}
