package entities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.Date;

import static billDetails.BillDetailsController.bill;
import static helper.RootStage.rootStage;

public class Bill {
    private Integer id;
    private Date datetime;
    private float total;
    public Button view;

    public Button getView() {
        return view;
    }

    public void setView(Button edit) {
        this.view = view;
    }

    public Bill() {
    }

    public Bill(Integer id, Date datetime, float total) {
        this.id = id;
        this.datetime = datetime;
        this.total = total;
        this.view = new Button("View Detail");
        this.view.setOnAction(event -> {
            try{
                bill=this;
                Parent listBook = FXMLLoader.load(getClass().getResource("../billDetails/billDetails.fxml"));
                rootStage.setTitle("History");
                rootStage.setScene(new Scene(listBook, 1280, 800));
            }catch (Exception e){}
        });

    }

    public Bill(Date datetime, float total) {
        this.datetime = datetime;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
