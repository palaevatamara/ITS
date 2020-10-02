package com.vaadin.tutorial.crm.views.test2;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.views.main.MainView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(value = "Test2", layout = MainView.class)
@PageTitle("Задание 2")
@CssImport("./styles/views/test2/test2-view.css")
public class Test2View extends HorizontalLayout {

    private TextField num =new TextField("Введите число");;
    private Button count= new Button("Подсчитать");
    private Button save= new Button("Сохранить");
    private Label label= new Label();

    private String result = "";

    public Test2View() {
        setId("test2-view");


        add(
          num,
          createButton(),
          label
        );

    }

    private Component createButton() {
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
        String text1 = num.getValue();
        Date date = new Date();
        String dateStr = String.valueOf(date.getTime());
        List<String> lines = new ArrayList<>();
        lines.add("Num");
        lines.add(text1);


        String filename = "test2" + dateStr + ".txt";
        String dir = "d://ITS//";
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        path = Paths.get(dir+filename);
        Files.write(path, lines, StandardCharsets.UTF_8);

    }

    public void countResult() {
        String znach = num.getValue();
        int num = Integer.parseInt(znach);
        List<Integer> resultList = new ArrayList<>();
        int n = 0;
        while (num > 0){
            resultList.add(num % 10);
            num = num / 10;
            n++;
        }


        if (n > 1){
            for (int i = n-1; i > 0; i--) {
                int dek = (int) Math.pow(10.0 , i);
                int ds = resultList.get(i) * dek;
                result += String.valueOf(ds) + " + ";
            }
        }

        result += String.valueOf(resultList.get(0));

        label.setText(result);
        label.setWidth(null);
        VerticalLayout verticalLayout = new VerticalLayout(label);

        add(verticalLayout);

    }

    public String setTest2(String param) {
        this.num.setValue(param);
        countResult();

        return result;
    }
}
