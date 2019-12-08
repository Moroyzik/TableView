package sample;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private javafx.scene.control.TableColumn<ModelTable, String> col_id;
    @FXML
    private javafx.scene.control.TableColumn<ModelTable, String> col_name;
    @FXML
    private javafx.scene.control.TableColumn<ModelTable, String> col_email;
    @FXML
    private TableColumn<ModelTable, String> col_speciality;
    @FXML
    private void deleteRowFromTable(ActionEvent event){
        //удаление из самой таблицы
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        try {
            //удаление из базы данных
            Connection con1 = ConnectionToDataBase.getConnection();
            //берём индекс строки
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            int result1 = con1.createStatement().executeUpdate("delete from people where id="+selectedIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    ObservableList<ModelTable> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //получение данных из базы данных
        try {
            Connection con1 = ConnectionToDataBase.getConnection();
            ResultSet result = con1.createStatement().executeQuery("select * from people");
            while(result.next()){
                list.add(new ModelTable(result.getString("id"), result.getString("name"), result.getString("email"), result.getString("speciality")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_speciality.setCellValueFactory(new PropertyValueFactory<>("spec"));

        table.setItems(list);
    }
}
