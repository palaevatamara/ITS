package com.vaadin.tutorial.crm.views.test1;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Frame;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.views.main.MainView;
import com.vaadin.tutorial.crm.views.test2.Test2View;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Route(value = "Test1", layout = MainView.class)
@PageTitle("Задание 1")
@CssImport("./styles/views/test1/test1-view.css")
@RouteAlias(value = "", layout = MainView.class)

public class Test1View extends VerticalLayout {
    private TextField a1;
    private  TextField a2;
    private Button count;
    private Button save;

    private Label label;

    private Set<String> result = new HashSet<>();

    public Test1View() {
        addClassName("test1-view");

        a1 = new TextField("Введите первый массив слов");
        a2 = new TextField("Введите второй массив слов");
        a1.setClearButtonVisible(true);
        a2.setClearButtonVisible(true);
        count = new Button("Подсчитать");
        save = new Button("Сохранить");

        label = new Label();

        add(    createTextField(),
                createButtons(),
                label
        );

    }


    public String addlabel() {
        String scrin = "";

        for (String element : result){
            scrin += " " + element;
        }

        return scrin;

    }

    private Component createTextField() {

        return new VerticalLayout(a1,a2);
    }

    private Component createButtons() {
        count.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        count.addClickShortcut(Key.ENTER);
        count.addClickListener(click -> countResult());
        save.addClickListener(click -> {
            try {
                saveButton();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HorizontalLayout createButton = new HorizontalLayout(count,save) ;
        createButton.addClassName("create-Button");
        return new VerticalLayout(createButton);
    }

    private void saveButton() throws IOException {
        String text1 = a1.getValue();
        String text2 = a2.getValue();
        Date date = new Date();
        String dateStr = String.valueOf(date.getTime());
        List<String> lines = new ArrayList<>();
        lines.add("Mass");
        lines.add(text1);
        lines.add(text2);

        String filename = "test1" + dateStr + ".txt";
        String dir = "d://ITS//";
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        path = Paths.get(dir+filename);
        Files.write(path, lines, StandardCharsets.UTF_8);


    }

    public void countResult() {
        String text1 = a1.getValue();
        String text2 = a2.getValue();

        String[] a1 = text1.split("\\p{Punct}");
        String[] a2 = text2.split("\\p{Punct}");

        List<String> list1 = new ArrayList<>();

        //Проверяем отсутствие пустых строк для а1
        for (int i = 0; i < a1.length; i++) {
            if (!a1[i].equals("")) list1.add(a1[i]);
        }
        //Удаляем лишние пробелы list1
        List<String> A1 = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            String a = list1.get(i);
            A1.add(a.replaceAll(" ",""));
        }

        List<String> list2 = new ArrayList<>();
        //Проверяем отсутствие пустых строк для а2
        for (int i = 0; i < a2.length; i++) {
            if (!a2[i].equals("")) list2.add(a2[i]);
        }
        //Удаляем лишние пробелы list2
        List<String> A2 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            String a = list2.get(i);
            A2.add(a.replaceAll(" ",""));
        }

        result.clear();
        for (int i = 0; i < A1.size(); i++) {
            for (int j = 0; j < A2.size(); j++) {
                if (A2.get(j).contains(A1.get(i))){
                    result.add(A1.get(i));
                }
            }
        }


        String res = addlabel();

        label.setText(res);
        label.setWidth(null);
        VerticalLayout verticalLayout = new VerticalLayout(label);

        add(verticalLayout);

    }

    public String setTest1(String param1, String param2){
        this.a1.setValue(param1);
        this.a2.setValue(param2);
        countResult();

        return addlabel();
    }



}
