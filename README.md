# To-Do List Frontend

## Beskrivning

Detta projekt är en enkel **To-Do List Frontend-applikation** byggd i JavaFX. Applikationen fungerar som ett grafiskt gränssnitt för att interagera med en backend-applikation genom att använda RESTful API:er. Användaren kan utföra CRUD-operationer (Create, Read, Update, Delete) för att hantera sina uppgifter.

### Funktionalitet

- **Lägg till en uppgift**: Användaren kan skapa en ny uppgift genom att fylla i uppgiftsnamn, beskrivning och datum.
- **Hämta alla uppgifter**: Visar en lista över alla uppgifter i ett formaterat textområde.
- **Uppdatera en uppgift**: Uppdaterar en befintlig uppgift baserat på dess ID.
- **Ta bort en uppgift**: Tar bort en specifik uppgift baserat på dess ID.

---

## Projektstruktur

```
ToDoListFE/
|-- src/
|   |-- main/
|       |-- java/org/example/todolistfe/
|           |-- HelloController.java      # Hanterar logiken för användargränssnittet
|           |-- HelloApplication.java     # Startar JavaFX-applikationen
|           |-- Task.java                 # Modellen för uppgifter
|       |-- resources/org/example/todolistfe/
|           |-- hello-view.fxml           # Gränssnittsdesign i FXML-format
```

---

## Installation och körning

### Krav

- **Java 17** eller högre.
- **JavaFX 17** eller högre.
- En IDE som stöder JavaFX (t.ex., IntelliJ IDEA eller Eclipse).

### Steg för att köra applikationen

1. Klona repot:
   ```bash
   git clone <repository-url>
   ```
2. Öppna projektet i din IDE.
3. Konfigurera din IDE för att använda JavaFX-biblioteket:
   - Lägg till JavaFX-biblioteket till projektets modulinställningar.
4. Kör applikationen genom att starta `HelloApplication.java`.

---

## Användargränssnitt

### Komponenter

- **Input-fält**:
  - Task ID
  - Task Name
  - Description
  - Date/Time
- **Knappfunktioner**:
  - **Add Task**: Lägger till en ny uppgift.
  - **Update Task**: Uppdaterar en befintlig uppgift.
  - **Delete Task**: Tar bort en uppgift baserat på ID.
  - **Get All Tasks**: Hämtar och visar alla uppgifter.
- **TextArea**: Visar resultatet av CRUD-operationerna.

### Design

Gränssnittet är byggt med FXML och JavaFX, där:
- **`hello-view.fxml`** beskriver layouten för gränssnittet.
- **HelloController.java** hanterar händelselogiken för varje knapp.

---

## Kommunikation med Backend

Frontend kommunicerar med en backend-applikation via HTTP-begäran.

### Exempel på REST-endpoints som används:

1. **GET `/api/tasks`**: Hämtar alla uppgifter.
2. **POST `/api/tasks`**: Skapar en ny uppgift.
3. **PUT `/api/tasks/{id}`**: Uppdaterar en befintlig uppgift.
4. **DELETE `/api/tasks/{id}`**: Tar bort en uppgift.

---