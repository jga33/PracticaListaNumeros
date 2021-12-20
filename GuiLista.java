import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class GuiLista extends Application {
    private static final int TAM_LISTA = 15;
    private static final int MIN_INTERVALO_NUM = -99;
    private static final int MAX_INTERVALO_NUM = 99;

    private Stage stage;

    private Button btnAdd;
    private Button btnSegundoMaximo;
    private Button btnSegundosPrincipio;
    private Button btnBuscarBinaria;
    private Button btnEstrellas;
    private Button btnMostrarLista;
    private Button btnVaciarLista;
    private Button btnClear;
    private Button btnSalir;
    private TextField txtNumero;
    private TextArea areaTexto;

    private ListaNumeros lista; // el modelo

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        crearLista();
        BorderPane root = crearGui();

        Scene scene = new Scene(root, 950, 600);
        stage.setScene(scene);
        stage.setTitle("- Entrega 1 UT5 - Lista de números -");
        scene.getStylesheets().add(getClass().getResource("/application.css")
            .toExternalForm());

        stage.show();
        mostrarLista();
    }

    private void crearLista() {

        lista = new ListaNumeros(TAM_LISTA);
        int n = (int) (Math.random() * TAM_LISTA) + 1;
        for (int i = 0; i < n; i++) {
            lista.addElemento((int) (Math.random()
                    * (MAX_INTERVALO_NUM - MIN_INTERVALO_NUM + 1))
                + MIN_INTERVALO_NUM);
        }

    }

    private BorderPane crearGui() {
        BorderPane panel = new BorderPane();
        panel.setRight(crearPanelBotones());
        panel.setCenter(crearPanelCentral());
        return panel;
    }

    private BorderPane crearPanelCentral() {
        BorderPane panel = new BorderPane();
        panel.setTop(crearPanelEntrada());

        areaTexto = new TextArea();
        areaTexto.setId("areatexto");
        panel.setCenter(areaTexto);
        return panel;
    }

    private HBox crearPanelEntrada() {
        HBox panel = new HBox();
        panel.setStyle("-fx-background-color: #ECEAE0");
        panel.setSpacing(10);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);

        Label lblNumero = new Label("Teclee nº");
        txtNumero = new TextField();
        txtNumero.setPrefColumnCount(20);
        txtNumero.setOnAction(e -> addNumero());

        btnAdd = new Button("Add número");
        btnAdd.setId("botonadd");
        btnAdd.setOnAction(e -> addNumero());

        panel.getChildren().addAll(lblNumero, txtNumero, btnAdd);

        return panel;
    }

    private VBox crearPanelBotones() {
        VBox panel = new VBox();
        panel.setStyle("-fx-background-color: #ECEAE0");
        panel.setSpacing(10);
        panel.setPadding(new Insets(10));

        btnSegundoMaximo = new Button("Segundo máximo");
        btnSegundoMaximo.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnSegundoMaximo, Priority.ALWAYS);
        btnSegundoMaximo.setOnAction(e -> segundoMaximo());

        btnSegundosPrincipio = new Button("Segundos máximos \n     al principio");
        btnSegundosPrincipio.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnSegundosPrincipio, Priority.ALWAYS);
        btnSegundosPrincipio.setOnAction(e -> segundosAlPrincipio());

        btnBuscarBinaria = new Button("Búsqueda binaria");
        btnBuscarBinaria.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnBuscarBinaria, Priority.ALWAYS);
        btnBuscarBinaria.setOnAction(e -> buscarBinaria());

        btnEstrellas = new Button("Estrellas en espacio");
        btnEstrellas.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnEstrellas, Priority.ALWAYS);
        btnEstrellas.setId("botonestrellas");
        btnEstrellas.setOnAction(e -> detectarEstrellas());

        btnMostrarLista = new Button("Mostrar lista");
        btnMostrarLista.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnMostrarLista, Priority.ALWAYS);
        btnMostrarLista.setOnAction(e -> mostrarLista());

        btnVaciarLista = new Button("Vaciar lista");
        btnVaciarLista.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnVaciarLista, Priority.ALWAYS);
        btnVaciarLista.setOnAction(e -> vaciarLista());

        btnClear = new Button("Limpiar área de texto");
        btnClear.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        VBox.setVgrow(btnClear, Priority.ALWAYS);
        btnClear.setOnAction(e -> clear());

        btnSalir = new Button("_Salir");
        btnSalir.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        btnSalir.setId("botonsalir");
        VBox.setVgrow(btnSalir, Priority.ALWAYS);
        btnSalir.setOnAction(e -> salir());

        panel.getChildren().addAll(btnSegundoMaximo, btnSegundosPrincipio,
            btnBuscarBinaria, btnEstrellas);
        panel.getChildren().addAll(btnMostrarLista, btnVaciarLista, btnClear,
            btnSalir);

        return panel;

    }

    /**
     * añade un nuevo número a la lista mostrando el estado actual de la lista en el
     * área de texto
     * 
     */
    private void addNumero() {
        mostrarLista();
        String strNumero = txtNumero.getText();
        if (strNumero.isEmpty()) {
            areaTexto.appendText("\nTeclee nº");
        }
        else {
            try {
                int numero = Integer.parseInt(strNumero);
                if (numero < MIN_INTERVALO_NUM || numero > MAX_INTERVALO_NUM) {
                    throw new IllegalArgumentException();
                }
                boolean exito = lista.addElemento(numero);
                if (!exito) {
                    areaTexto.appendText(
                        "\nLista completa, no se ha podido añadir\n");
                }
                else {
                    mostrarLista();
                }
            }
            catch (NumberFormatException e) {
                areaTexto.appendText("\nTeclee solo dígitos numéricos");
            }
            catch (IllegalArgumentException e) {
                areaTexto.appendText(
                    "\nTeclee valores en el intervalo ["
                    + MIN_INTERVALO_NUM
                    + ","
                    + MAX_INTERVALO_NUM
                    + "]");
            }
        }

        cogerFoco();

    }

    /**
     * Muestra en el área de texto el array2D de estrellas
     * obtenido a partir de la lista
     */
    private void detectarEstrellas() {
        clear();

        int[][] brillos = lista.crearBrillos();
        boolean[][] estrellas = lista.detectarEstrellas(brillos);

        GridPane panel2D = crearPanel2D(brillos, estrellas);
        Scene escena2D = new Scene(panel2D);

        Stage escenario2D = new Stage();

        escenario2D.setScene(escena2D);
        escenario2D.initModality(Modality.WINDOW_MODAL);
        escenario2D.initOwner(this.stage);
        escenario2D.setX(this.stage.getX() + 200);
        escenario2D.setY(this.stage.getY());
        // escenario2D.setX(this.stage.getX() + this.stage.getWidth() / 2 );
        // escenario2D.setY(this.stage.getY() + this.stage.getHeight() / 2);

        escenario2D.setTitle("- Estrellas en el espacio -");
        escena2D.getStylesheets()
        .add(getClass().getResource("/application.css")
            .toExternalForm());
        // escenario2D.sizeToScene();
        escenario2D.show();

    }
    private GridPane crearPanel2D(int[][] brillos, boolean[][] estrellas) {
        GridPane panel = new GridPane();
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);
        panel.setHgap(5);
        panel.setVgap(5);

        for (int f = 0; f < brillos.length; f++) {
            for (int c = 0; c < brillos[f].length; c++) {
                Button btn = new Button(brillos[f][c] + "");
                btn.setPrefSize(60, 60);
                btn.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                GridPane.setHgrow(btn, Priority.ALWAYS);
                GridPane.setVgrow(btn, Priority.ALWAYS);
                btn.setStyle(null);
                btn.getStyleClass().add("button2D");
                if (estrellas[f][c]) {
                    btn.getStyleClass().add("button2Dsombreado");
                }

                panel.add(btn, c, f);

            }
        }
        return panel;
    }

    /**
     * Muestra en el área de texto el segundo máximo
     * 
     */
    private void segundoMaximo() {
        clear();
        if (lista.estaVacia()) {
            areaTexto.appendText("Lista vacía\n");
        }
        else {
            escribirLista();
            int segundo = lista.segundoMaximo();
            if (segundo == Integer.MIN_VALUE) {
                areaTexto.appendText("No hay segundo máximo");
            }
            else {
                areaTexto.appendText("Segundo máximo: " + segundo);
            }

        }
        cogerFoco();
    }

    /**
     * Colocar los segundos máximos al principio de la lista
     */
    private void segundosAlPrincipio() {
        clear();
        mostrarLista();
        boolean exito = lista.segundosMaximosAlPrincipio();
        if (!exito) {
            areaTexto.appendText("No hay segundo máximo");
        }
        else {
            areaTexto.appendText("Colocados segundos máximos al principio\n\n");
            escribirLista();
        }

    }

    /**
     * pedir un valor e indicar si existe o no  en 
     * la lista  
     */
    private void buscarBinaria() {
        clear();
        escribirLista();
        if (!lista.estaVacia()) {
            try {
                int numero = pedirNumero("Búsqueda binaria");
                int p = lista.buscarBinario(numero);
                if (p < 0) {
                    areaTexto.appendText("No existe el valor " + numero
                        + " en la lista");

                }
                else {
                    areaTexto.appendText(
                        "El nº " + numero + " está en la lista ");
                }
            }
            catch (IllegalArgumentException e) {
                areaTexto.appendText(e.getMessage());
            }
        }

    }

    private int pedirNumero(String mensaje) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Entrada de datos - " + mensaje);
        dialog.setHeaderText(null);
        dialog.setContentText("Teclee un valor:");
        Optional<String> result = dialog.showAndWait();

        int numero = 0;
        if (result.isPresent()) {
            String strNumero = result.get();
            try {
                numero = Integer.parseInt(strNumero);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Teclee valor numérico");
            }
        }
        else {
            throw new IllegalArgumentException("Ha pulsado Cancel");
        }

        return numero;
    }

    /**
     * mostrar la lista y su nº de elementos en el área de texto
     */
    private void mostrarLista() {
        clear();
        if (lista.estaVacia()) {
            areaTexto.appendText("Lista vacía\n");
        }
        else {
            escribirLista();
        }

        cogerFoco();

    }

    private void escribirLista() {
        areaTexto.appendText("Lista\n");
        areaTexto.appendText(lista.toString() + "\n");
        areaTexto.appendText("\nNº de elementos en la lista "
            + lista.getTotalNumeros() + "\n\n");

    }

    /**
     * Vacía la lista
     */
    private void vaciarLista() {
        clear();
        lista.vaciarLista();
        areaTexto.appendText("Borrados todos los valores de la lista");
        cogerFoco();

    }

    /**
     * limpiar área de texto
     */
    private void clear() {
        areaTexto.setText("");
        cogerFoco();
    }

    /**
     * finalizar aplicación
     */
    private void salir() {
        Platform.exit();

    }

    /**
     * llevar el foco al campo de texto y seleccionar todo
     */
    private void cogerFoco() {
        txtNumero.requestFocus();
        txtNumero.selectAll();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
