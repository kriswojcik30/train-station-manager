package com.example.zadanie3;

import com.example.zadanie3.model.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML
    private TableView<Train> trainTable;

    @FXML
    private ListView<String> platformList;

    @FXML
    private TableColumn<Train, String> numberCol;

    @FXML
    private TableColumn<Train, String> carrierCol;

    @FXML
    private TableColumn<Train, String> directionCol;

    @FXML
    private TableColumn<Train, Integer> platformCol;

    @FXML
    private TableColumn<Train, String> timeCol;

    @FXML
    private TableColumn<Train, String> statusCol;

    @FXML
    private TextField searchField;

    private ObservableList<Train> trains;

    @FXML
    public void initialize() {
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        carrierCol.setCellValueFactory(new PropertyValueFactory<>("carrier"));
        directionCol.setCellValueFactory(new PropertyValueFactory<>("direction"));
        platformCol.setCellValueFactory(new PropertyValueFactory<>("platform"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        trains = FXCollections.observableArrayList(
                new Train("IC 1234", "PKP Intercity", "Warszawa", 1, "12:30", "ON_TIME"),
                new Train("REG 456", "Polregio", "Kraków", 2, "13:10", "DELAYED"),
                new Train("TLK 7890", "PKP Intercity", "Gdańsk", 3, "14:00", "BOARDING")
        );

        trainTable.setItems(trains);

        platformList.setItems(FXCollections.observableArrayList(
                "Peron 1",
                "Peron 2",
                "Peron 3"
        ));

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterTrains(newVal);
        });

        platformList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filterByPlatform(newVal);
        });

        statusCol.setCellFactory(column -> new TableCell<Train, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);

                    switch (item) {
                        case "ON_TIME":
                            setStyle("-fx-background-color: #c8e6c9;");
                            break;
                        case "DELAYED":
                            setStyle("-fx-background-color: #ffcdd2;");
                            break;
                        case "BOARDING":
                            setStyle("-fx-background-color: #fff9c4;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });
    }

    private void filterTrains(String text) {
        if (text == null || text.isEmpty()) {
            String selectedPlatform = platformList.getSelectionModel().getSelectedItem();
            if (selectedPlatform != null) {
                filterByPlatform(selectedPlatform);
            } else {
                trainTable.setItems(trains);
            }
            return;
        }

        ObservableList<Train> filtered = FXCollections.observableArrayList();

        for (Train t : trains) {
            boolean matchesText = t.getNumber().toLowerCase().contains(text.toLowerCase())
                    || t.getCarrier().toLowerCase().contains(text.toLowerCase());

            String selectedPlatform = platformList.getSelectionModel().getSelectedItem();

            if (selectedPlatform != null) {
                String platformNumberText = selectedPlatform.replace("Peron ", "").trim();
                int platformNumber;

                try {
                    platformNumber = Integer.parseInt(platformNumberText);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (matchesText && t.getPlatform() == platformNumber) {
                    filtered.add(t);
                }
            } else {
                if (matchesText) {
                    filtered.add(t);
                }
            }
        }

        trainTable.setItems(filtered);
    }

    private void filterByPlatform(String selectedPlatform) {
        String currentSearch = searchField.getText();

        if (selectedPlatform == null || selectedPlatform.isEmpty()) {
            if (currentSearch == null || currentSearch.isEmpty()) {
                trainTable.setItems(trains);
            } else {
                filterTrains(currentSearch);
            }
            return;
        }

        String platformNumberText = selectedPlatform.replace("Peron ", "").trim();

        int platformNumber;
        try {
            platformNumber = Integer.parseInt(platformNumberText);
        } catch (NumberFormatException e) {
            trainTable.setItems(trains);
            return;
        }

        ObservableList<Train> filtered = FXCollections.observableArrayList();

        for (Train t : trains) {
            boolean matchesPlatform = t.getPlatform() == platformNumber;

            if (currentSearch == null || currentSearch.isEmpty()) {
                if (matchesPlatform) {
                    filtered.add(t);
                }
            } else {
                boolean matchesText = t.getNumber().toLowerCase().contains(currentSearch.toLowerCase())
                        || t.getCarrier().toLowerCase().contains(currentSearch.toLowerCase());

                if (matchesPlatform && matchesText) {
                    filtered.add(t);
                }
            }
        }

        trainTable.setItems(filtered);
    }

    @FXML
    private void handleDelete() {
        Train selected = trainTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            trains.remove(selected);

            String selectedPlatform = platformList.getSelectionModel().getSelectedItem();
            if (selectedPlatform != null) {
                filterByPlatform(selectedPlatform);
            } else {
                trainTable.setItems(trains);
            }
        }
    }

    @FXML
    private void handleAdd() {
        TextInputDialog numberDialog = new TextInputDialog();
        numberDialog.setTitle("Dodawanie pociągu");
        numberDialog.setHeaderText("Podaj numer pociągu");
        numberDialog.setContentText("Numer:");

        String number = numberDialog.showAndWait().orElse("").trim();
        if (number.isEmpty()) {
            return;
        }

        TextInputDialog carrierDialog = new TextInputDialog();
        carrierDialog.setTitle("Dodawanie pociągu");
        carrierDialog.setHeaderText("Podaj przewoźnika");
        carrierDialog.setContentText("Przewoźnik:");

        String carrier = carrierDialog.showAndWait().orElse("").trim();
        if (carrier.isEmpty()) {
            return;
        }

        TextInputDialog directionDialog = new TextInputDialog();
        directionDialog.setTitle("Dodawanie pociągu");
        directionDialog.setHeaderText("Podaj kierunek");
        directionDialog.setContentText("Kierunek:");

        String direction = directionDialog.showAndWait().orElse("").trim();
        if (direction.isEmpty()) {
            return;
        }

        TextInputDialog platformDialog = new TextInputDialog();
        platformDialog.setTitle("Dodawanie pociągu");
        platformDialog.setHeaderText("Podaj numer peronu");
        platformDialog.setContentText("Peron:");

        String platformText = platformDialog.showAndWait().orElse("").trim();
        if (platformText.isEmpty()) {
            return;
        }

        int platform;
        try {
            platform = Integer.parseInt(platformText);
        } catch (NumberFormatException e) {
            showError("Peron musi być liczbą.");
            return;
        }

        TextInputDialog timeDialog = new TextInputDialog();
        timeDialog.setTitle("Dodawanie pociągu");
        timeDialog.setHeaderText("Podaj godzinę odjazdu");
        timeDialog.setContentText("Godzina:");

        String time = timeDialog.showAndWait().orElse("").trim();
        if (time.isEmpty()) {
            return;
        }

        ChoiceDialog<String> statusDialog = new ChoiceDialog<>("ON_TIME", "ON_TIME", "DELAYED", "BOARDING");
        statusDialog.setTitle("Dodawanie pociągu");
        statusDialog.setHeaderText("Wybierz status");
        statusDialog.setContentText("Status:");

        String status = statusDialog.showAndWait().orElse("");
        if (status.isEmpty()) {
            return;
        }

        Train newTrain = new Train(number, carrier, direction, platform, time, status);
        trains.add(newTrain);

        if (!platformList.getItems().contains("Peron " + platform)) {
            platformList.getItems().add("Peron " + platform);
        }

        String selectedPlatform = platformList.getSelectionModel().getSelectedItem();
        if (selectedPlatform != null) {
            filterByPlatform(selectedPlatform);
        } else {
            trainTable.setItems(trains);
        }
    }

    @FXML
    private void handleModify() {
        Train selected = trainTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Najpierw wybierz pociąg do modyfikacji.");
            return;
        }

        TextInputDialog numberDialog = new TextInputDialog(selected.getNumber());
        numberDialog.setTitle("Modyfikacja pociągu");
        numberDialog.setHeaderText("Zmień numer pociągu");
        numberDialog.setContentText("Numer:");

        String number = numberDialog.showAndWait().orElse("").trim();
        if (number.isEmpty()) {
            return;
        }

        TextInputDialog carrierDialog = new TextInputDialog(selected.getCarrier());
        carrierDialog.setTitle("Modyfikacja pociągu");
        carrierDialog.setHeaderText("Zmień przewoźnika");
        carrierDialog.setContentText("Przewoźnik:");

        String carrier = carrierDialog.showAndWait().orElse("").trim();
        if (carrier.isEmpty()) {
            return;
        }

        TextInputDialog directionDialog = new TextInputDialog(selected.getDirection());
        directionDialog.setTitle("Modyfikacja pociągu");
        directionDialog.setHeaderText("Zmień kierunek");
        directionDialog.setContentText("Kierunek:");

        String direction = directionDialog.showAndWait().orElse("").trim();
        if (direction.isEmpty()) {
            return;
        }

        TextInputDialog platformDialog = new TextInputDialog(String.valueOf(selected.getPlatform()));
        platformDialog.setTitle("Modyfikacja pociągu");
        platformDialog.setHeaderText("Zmień numer peronu");
        platformDialog.setContentText("Peron:");

        String platformText = platformDialog.showAndWait().orElse("").trim();
        if (platformText.isEmpty()) {
            return;
        }

        int platform;
        try {
            platform = Integer.parseInt(platformText);
        } catch (NumberFormatException e) {
            showError("Peron musi być liczbą.");
            return;
        }

        TextInputDialog timeDialog = new TextInputDialog(selected.getTime());
        timeDialog.setTitle("Modyfikacja pociągu");
        timeDialog.setHeaderText("Zmień godzinę odjazdu");
        timeDialog.setContentText("Godzina:");

        String time = timeDialog.showAndWait().orElse("").trim();
        if (time.isEmpty()) {
            return;
        }

        ChoiceDialog<String> statusDialog = new ChoiceDialog<>(
                selected.getStatus(),
                "ON_TIME", "DELAYED", "BOARDING"
        );
        statusDialog.setTitle("Modyfikacja pociągu");
        statusDialog.setHeaderText("Zmień status");
        statusDialog.setContentText("Status:");

        String status = statusDialog.showAndWait().orElse("");
        if (status.isEmpty()) {
            return;
        }

        selected.setNumber(number);
        selected.setCarrier(carrier);
        selected.setDirection(direction);
        selected.setPlatform(platform);
        selected.setTime(time);
        selected.setStatus(status);

        if (!platformList.getItems().contains("Peron " + platform)) {
            platformList.getItems().add("Peron " + platform);
        }

        trainTable.refresh();

        String selectedPlatform = platformList.getSelectionModel().getSelectedItem();
        if (selectedPlatform != null) {
            filterByPlatform(selectedPlatform);
        } else {
            trainTable.setItems(trains);
        }
    }

    @FXML
    private void handleSort() {
        FXCollections.sort(trains, (t1, t2) -> t1.getTime().compareTo(t2.getTime()));

        String selectedPlatform = platformList.getSelectionModel().getSelectedItem();
        if (selectedPlatform != null) {
            filterByPlatform(selectedPlatform);
        } else {
            trainTable.setItems(trains);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Nieprawidłowe dane");
        alert.setContentText(message);
        alert.showAndWait();
    }
}