package com.vaadin.tutorial.crm.views.LoadView;

import ch.qos.logback.core.Layout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.views.main.MainView;
import com.vaadin.tutorial.crm.views.test1.Test1View;
import com.vaadin.tutorial.crm.views.test2.Test2View;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Route(value = "LoadView", layout = MainView.class)
@PageTitle("Загрузить из файла")
@CssImport("./styles/views/load/load-view.css")

public class LoadView extends VerticalLayout {
    private String[] line;
    private Label head = new Label("Выберите файл для расчета результата");
    Label label = new Label();
    Label label1 = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    Label label5 = new Label();
    Label label6 = new Label();
    Label label7 = new Label();
    Label label8 = new Label();
    Label label9 = new Label();
    FormLayout layout = new FormLayout();


    public LoadView()  {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        Div output = new Div();
        upload.addSucceededListener(event -> {
            Component component = createComponent(event.getMIMEType(),
                    buffer.getInputStream());
        });
        add(head, upload, output);

        layout.add(label, label1,label2,label3,label4,label5,label6,label7,label8, label9);
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
        add(layout);
    }

    private Component createComponent(String mimeType, InputStream stream) {
        if (mimeType.startsWith("text")) {
            try {
                createTextComponent(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;
    }

    private void createTextComponent(InputStream stream) throws Exception {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
            line = text.split("\r\n");
            if ((line[0].equals("Mass")) && (line.length == 3)){
                test1();
            }
            else if ((line[0].equals("Num")) && (line.length == 2)){
                test2();
            }
            else {
                label1.setText("Файл не подходит под задания");
                label2.setText(" ");
                label3.setText("");
                label4.setText("");
                label5.setText(" ");
                label6.setText("" );
                label7.setText("");
                label8.setText( "");
                label9 .setText("");

            }
        } catch (IOException e) {
            text = "exception reading stream";
        }

    }

    private void test1() {
        label.setText("");
        label1.setText("Задание 1 ");
        label2.setText("Массив 1:");
        label3.setText(line[1]);
        label4.setText("");
        label5.setText("Массив 2:");
        label6.setText(line[2]);
        label7.setText("");
        label8.setText("Результат:");
        String result = new Test1View().setTest1(line[1], line[2]);
        label9 .setText(result);
    }

    private void test2(){
        label.setText("");
        label1.setText("Задание 2 ");
        label2.setText("Число:");
        label3 .setText(line[1]);
        label4.setText("Результат:");
        String result = new Test2View().setTest2(line[1]);
        label5.setText(result);
        label6 .setText("");
        label7 .setText("");
        label8 .setText("");
        label9 .setText("");
    }


}
