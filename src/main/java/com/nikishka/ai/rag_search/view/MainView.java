package com.nikishka.ai.rag_search.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("")
public class MainView extends Div {

    private final TextField input = new TextField("Ваш запрос");
    private final Button sendButton = new Button("Отправить");

    public MainView() {
        HorizontalLayout layout = new HorizontalLayout(input, sendButton);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);

        // обработчик нажатия кнопки Отправить
        sendButton.addClickListener(event -> {
            String query = input.getValue().trim();
            if (!query.isEmpty()) {
                String response = sendRequest(query);
                Notification.show(response, 3000, Notification.Position.MIDDLE);
            }
        });

        add(layout);
    }

    private String sendRequest(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject("http://localhost:8080/api/chat/" + query, null, String.class);
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
