package org.example.todolistfe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HelloController {

    @FXML
    private TextField Input_TaskID;

    @FXML
    private TextField Input_Date;


    @FXML
    private TextField Input_Description;

    @FXML
    private TextField Input_TaskName;

    @FXML
    private TextArea Textarea_Allbox;

    @FXML
    void AddTask(ActionEvent event) {
        try {
            // Läs input från textfälten
            String date = Input_Date.getText();           // Textfält för datum
            String taskName = Input_TaskName.getText();   // Textfält för TaskName
            String description = Input_Description.getText(); // Textfält för Description

            // Skapa en Task-objekt utan ID
            Task myTask = new Task(0, taskName, description, date); // ID sätts till 0 eller tas bort i modellen

            // Konvertera Task till JSON med ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(myTask);


            URL url = new URL("http://localhost:8080/api/tasks");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Skriv JSON-data till servern
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            String response = readResponse(connection);
            Textarea_Allbox.setText(response);
        } catch (Exception e) {
            Textarea_Allbox.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void DeleteTask(ActionEvent event) {

        try {
            // Läs Task ID från popupen
            int id = Integer.parseInt(Input_TaskID.getText());

            // Skicka DELETE-begäran till backend
            URL url = new URL("http://localhost:8080/api/tasks/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection);
                Textarea_Allbox.setText("Task deleted successfully: " + response);
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                Textarea_Allbox.setText("Task with ID " + id + " not found.");
            } else {
                Textarea_Allbox.setText("Failed to delete task. HTTP response code: " + responseCode);
            }

            // rensa inputfältet
            Input_TaskID.clear();

        } catch (NumberFormatException e) {
            Input_TaskID.setPromptText("Invalid ID"); // Felmeddelande för ogiltigt ID
        } catch (Exception e) {
            Textarea_Allbox.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    void GetAllTasks(ActionEvent event) {
        try {
            URL url = new URL("http://localhost:8080/api/tasks");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            String jsonResponse = readResponse(connection);
            // Omvandla JSON-svaret till en lista av objekt
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> tasks = mapper.readValue(jsonResponse, new TypeReference<List<Map<String, Object>>>() {});

            // Bygg upp en snygg sträng
            StringBuilder formattedResponse = new StringBuilder("Tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                Map<String, Object> task = tasks.get(i);
                formattedResponse.append(i + 1).append(". Task:\n");
                formattedResponse.append("   - ID: ").append(task.get("id")).append("\n");
                formattedResponse.append("   - Name: ").append(task.get("name")).append("\n");
                formattedResponse.append("   - Description: ").append(task.get("description")).append("\n");
                formattedResponse.append("   - Date: ").append(task.get("date")).append("\n\n");
            }

            // Visa det snyggt formaterade resultatet i Textarea_Allbox
            Textarea_Allbox.setText(formattedResponse.toString());

        } catch (Exception e) {
            Textarea_Allbox.setText("Error" + e.getMessage());
        }
    }
    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader;
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
    @FXML
    void UpdateTask(ActionEvent event) {
        try {
            // Hämta data från inputfält
            int id = Integer.parseInt(Input_TaskID.getText());
            String name = Input_TaskName.getText();
            String description = Input_Description.getText();
            String date = Input_Date.getText();

            // Skapa en Task-objekt
            Task updatedTask = new Task(id, name, description, date);

            // Konvertera Task-objektet till JSON med ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(updatedTask);

            // Skicka PUT-begäran till backend
            URL url = new URL("http://localhost:8080/api/tasks/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Skriv JSON-data till begäran
            try (var os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Läs svar från servern
            String response = readResponse(connection);
            Textarea_Allbox.setText("Task updated successfully:\n" + response);

        } catch (NumberFormatException e) {
            Textarea_Allbox.setText("Error: Invalid ID format.");
        } catch (Exception e) {
            Textarea_Allbox.setText("Error: " + e.getMessage());
        }
    }

}