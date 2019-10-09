package dad.javafx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraComplejaApp extends Application {
	
	private Complejo complejo1 = new Complejo();
	private Complejo complejo2 = new Complejo();
	private Complejo resultado = new Complejo();
	private StringProperty operador = new SimpleStringProperty();

	private TextField operando11Text;
	private TextField operando12Text;
	private TextField operando21Text;
	private TextField operando22Text;
	private TextField resultado1Text;
	private TextField resultado2Text;
	private ComboBox<String> operadorCombo;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		operando11Text = new TextField();
		operando11Text.setPrefColumnCount(4);
		operando12Text = new TextField();
		operando12Text.setPrefColumnCount(4);
		operando21Text = new TextField();
		operando21Text.setPrefColumnCount(4);
		operando22Text = new TextField();
		operando22Text.setPrefColumnCount(4);
		resultado1Text = new TextField();
		resultado1Text.setPrefColumnCount(4);
		resultado1Text.setDisable(true);
		resultado2Text = new TextField();
		resultado2Text.setPrefColumnCount(4);
		resultado2Text.setDisable(true);
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");

		HBox hbox1 = new HBox(5, operando11Text, new Label(" + "), operando12Text, new Label("i"));
		hbox1.setAlignment(Pos.CENTER);
		HBox hbox2 = new HBox(5, operando21Text, new Label(" + "), operando22Text, new Label("i"));
		hbox2.setAlignment(Pos.CENTER);
		HBox hbox3 = new HBox(5, resultado1Text, new Label(" + "), resultado2Text, new Label("i"));
		hbox3.setAlignment(Pos.CENTER);
		VBox vbox = new VBox(5, hbox1, hbox2, new Separator(), hbox3);
		vbox.setAlignment(Pos.CENTER);
		HBox root = new HBox(15, operadorCombo, vbox);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Bindings.bindBidirectional(operando11Text.textProperty(), complejo1.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando12Text.textProperty(), complejo1.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando21Text.textProperty(), complejo2.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando22Text.textProperty(), complejo2.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultado1Text.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultado2Text.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		operador.addListener((o, ov, nv) -> onOperadorComplejoChanged(nv));
		operadorCombo.getSelectionModel().selectFirst();
	}

	public static void main(String[] args) {
		launch();
	}
	
	private void onOperadorComplejoChanged(String nv) {
		switch(nv) {
		case "+":
			resultado.realProperty().bind(complejo1.realProperty().add(complejo2.realProperty()));
			resultado.imaginarioProperty().bind(complejo1.imaginarioProperty().add(complejo2.imaginarioProperty()));
			break;
		case "-":
			resultado.realProperty().bind(complejo1.realProperty().subtract(complejo2.realProperty()));
			resultado.imaginarioProperty().bind(complejo1.imaginarioProperty().subtract(complejo2.imaginarioProperty()));
			break;
		case "*":
			resultado.realProperty().bind(complejo1.realProperty().multiply(complejo2.realProperty()).subtract(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty())));
			resultado.imaginarioProperty().bind(complejo1.realProperty().multiply(complejo2.imaginarioProperty()).add(complejo1.imaginarioProperty().multiply(complejo2.realProperty())));
			break;
		case "/":

			resultado.realProperty().bind(complejo1.realProperty().multiply(complejo2.realProperty()).add(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()))
					.divide(complejo2.realProperty().multiply(complejo2.realProperty().add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())))));
			resultado.imaginarioProperty().bind(complejo1.imaginarioProperty().multiply(complejo2.realProperty().subtract(complejo1.realProperty().multiply(complejo2.imaginarioProperty())))
					.divide(complejo2.realProperty().multiply(complejo2.realProperty().add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty())))));
			break;
		}
	}
	
	/*private void onOperadorChanged(String nv) {
		switch(nv) {
		case "+":
			resultado.bind(operando1.add(operando2));
			break;
		case "-":
			resultado.bind(operando1.subtract(operando2));
			break;
		case "*":
			resultado.bind(operando1.multiply(operando2));
			break;
		case "/":
			resultado.bind(operando1.divide(operando2));
			break;
		}
	}*/

}
