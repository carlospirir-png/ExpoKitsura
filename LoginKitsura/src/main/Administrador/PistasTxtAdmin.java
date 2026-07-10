//-------------------------- PISTAS DE TEXTO --------------------------
package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import java.sql.*;
import javax.swing.event.*;

public class PistasTxtAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JTextArea txtAreaPista;
    private JTextField txtIdPista;
    private JTextField txtIdPregunta;

    private DefaultTableModel modelo;
    private JTable tablaPistas;

    private DatosConfiguracion datos;
    private PistasDAO dao = new PistasDAO();

    public PistasTxtAdmin(DatosConfiguracion datos) {

        this.datos = datos;

        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        setTitle("Pistas: TXT");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(252, 118, 125, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTituloSeccion = new JLabel("PISTAS: TEXTO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO (TEXTO PISTA) ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 100));
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 170, 500, 820);
        fondo.add(panelIzquierdo);

        JLabel lblTextoPista = new JLabel("Ingrese el texto de la pista");
        lblTextoPista.setFont(fuente2.deriveFont(28f));
        lblTextoPista.setForeground(Color.WHITE);
        lblTextoPista.setBounds(30, 30, 440, 40);
        panelIzquierdo.add(lblTextoPista);

        txtAreaPista = new JTextArea();
        txtAreaPista.setFont(fuente1.deriveFont(36f));
        txtAreaPista.setLineWrap(true);
        txtAreaPista.setWrapStyleWord(true);
        txtAreaPista.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JScrollPane scrollPaneTexto = new JScrollPane(txtAreaPista);
        scrollPaneTexto.setBounds(30, 85, 440, 440);
        panelIzquierdo.add(scrollPaneTexto);

        //---------------- BOTÓN EDITAR ----------------
        JButton btnEditar = new DecoracionBotones("EDITAR", //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(130, 550, 240, 55);

        btnEditar.addActionListener(e -> {
            Editar();
        });
        panelIzquierdo.add(btnEditar);

        //---------------- BOTÓN AGREGAR ----------------
        JButton btnAgregar = new DecoracionBotones("AGREGAR", //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnAgregar.setFont(fuente2.deriveFont(26f));
        btnAgregar.setBounds(130, 650, 240, 55);

        btnAgregar.addActionListener(e -> {
            Agregar();
        });

        panelIzquierdo.add(btnAgregar);

        //---------------- BOTÓN AGREGAR ----------------
        JButton btnLimpiar = new DecoracionBotones("LIMPIAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AZUL, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.CELESTE); //MOUSE DENTRO

        btnLimpiar.setFont(fuente2.deriveFont(26f));
        btnLimpiar.setBounds(38, 9, 240, 55);

        btnLimpiar.addActionListener(e -> {
            Limpiar();
        });

        panelTitulo.add(btnLimpiar);

        //---------------- BOTÓN BORRAR ----------------
        JButton btnEliminar = new DecoracionBotones("ELIMINAR", //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnEliminar.setFont(fuente2.deriveFont(26f));
        btnEliminar.setBounds(130, 750, 240, 55);

        btnEliminar.addActionListener(e -> {
            Eliminar();
        });

        panelIzquierdo.add(btnEliminar);

        //---------------- PANEL DERECHO (ID + TABLA) ----------------
        FondoPanelSemi panelDerecho = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelDerecho.setLayout(null);
        panelDerecho.setBounds(680, 170, 880, 620);
        fondo.add(panelDerecho);

        JLabel lblInstruccionId = new JLabel("ID_Ayuda.");
        lblInstruccionId.setFont(fuente2.deriveFont(26f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);
        panelDerecho.add(lblInstruccionId);

        JLabel lblid = new JLabel("ID_Pregunta.");
        lblid.setFont(fuente2.deriveFont(26f));
        lblid.setForeground(Color.WHITE);
        lblid.setBounds(450, 30, 820, 35);
        panelDerecho.add(lblid);

        txtIdPista = new JTextField();
        txtIdPista.setFont(fuente1.deriveFont(36f));
        txtIdPista.setBounds(30, 75, 400, 55);
        panelDerecho.add(txtIdPista);

        txtIdPregunta = new JTextField();
        txtIdPregunta.setFont(fuente1.deriveFont(36f));
        txtIdPregunta.setBounds(450, 75, 400, 55);
        panelDerecho.add(txtIdPregunta);

        //---------------- TABLA (ID 1/3, CONTENIDO 2/3) ----------------
        modelo = new DefaultTableModel(
                new String[]{"ID_AYUDA", "ID_PREGUNTA", "CONTENIDO"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPistas = new JTable(modelo);
        tablaPistas.setFont(fuente1.deriveFont(18f));
        tablaPistas.setRowHeight(50);
        tablaPistas.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaPistas.getTableHeader().setReorderingAllowed(false);

        // ID = 1/3, CONTENIDO = 2/3
        tablaPistas.getColumnModel().getColumn(0).setPreferredWidth(120);
        tablaPistas.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaPistas.getColumnModel().getColumn(2).setPreferredWidth(550);

        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);
        scrollPaneTabla.setBounds(30, 155, 820, 440);
        panelDerecho.add(scrollPaneTabla);
        cargarTabla(); //Carga la tabla

        //Esto es para que cuando se presione una fila se carguen en los txt
        //getSelectionModel(): escucha que fila se selecciona
        tablaPistas.getSelectionModel().addListSelectionListener(e -> {

            //Esto es por si se usa el teclado o se selecciona rápido, evite que el método se ejecute a medias
            if (!e.getValueIsAdjusting()) {

                //getSelectedRow(): te dice la fila que fue clickeada
                int fila = tablaPistas.getSelectedRow();

                if (fila >= 0 && fila < modelo.getRowCount()) {
                    //getValueAt(fila, columna) saca los datos de la tabla
                    //se parsean de una vez a String y se colocan de una vez en los txt
                    txtIdPista.setText(modelo.getValueAt(fila, 0).toString());
                    txtIdPregunta.setText(modelo.getValueAt(fila, 1).toString());
                    txtAreaPista.setText(modelo.getValueAt(fila, 2).toString());
                }
            }
        });

        //----------------- INFORMACIÓN ----------------
        FondoPanelSemi panelInfo = new FondoPanelSemi(new Color(252, 118, 125, 200));
        panelInfo.setLayout(null);
        panelInfo.setBounds(680, 800, 400, 150);
        fondo.add(panelInfo);

        //---------------- MINIJUEGO -------------------
        //Se inicializa el label
        JLabel lblMinijuego = new JLabel("Minijuego: ");
        //Se le coloca la fuente y su respectivo tamaño
        lblMinijuego.setFont(fuente1.deriveFont(40f));
        //Color de fuente
        lblMinijuego.setForeground(Color.WHITE);
        //Posición y tamaño del label
        lblMinijuego.setBounds(10, 10, 600, 35);
        //Se añade al panel
        panelInfo.add(lblMinijuego);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblMinijuego.setText("Minijuego: " + datos.getMinijuego());

        //---------------- CATEGORÍA -------------------
        //Se inicializa el label
        JLabel lblCategoria = new JLabel("Categoría: ");
        //Se le coloca la fuente y su respectivo tamaño
        lblCategoria.setFont(fuente1.deriveFont(40f));
        //Se le coloca color a la fuente
        lblCategoria.setForeground(Color.WHITE);
        //Posición y tamaño al label
        lblCategoria.setBounds(10, 50, 600, 35);
        //Se agrega al panel
        panelInfo.add(lblCategoria);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblCategoria.setText("Categoría: " + datos.getCategoria());

        //---------------- NIVEL -----------------------
        //Se inicializa el label
        JLabel lblNivel = new JLabel("Nivel: ");
        //Se le coloca la fuente y el tamaño
        lblNivel.setFont(fuente1.deriveFont(40f));
        //Se le coloca color a la fuente
        lblNivel.setForeground(Color.WHITE);
        //se le coloca posición y tamaño al label
        lblNivel.setBounds(10, 90, 600, 35);
        //Se agrega al panel
        panelInfo.add(lblNivel);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblNivel.setText("Nivel: " + datos.getNivel());

        //---------------- MASCOTA ----------------
        JLabel staticMascotaLibro = new JLabel();

        try {
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));
            Image imgEscalada = iconMascota.getImage().getScaledInstance(550, 550, Image.SCALE_SMOOTH);
            staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception e) {
            staticMascotaLibro.setText("~");
        }
        staticMascotaLibro.setBounds(1480, 250, 550, 550);
        fondo.add(staticMascotaLibro);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);
        btnVolver.addActionListener(e
                -> {
            new PistasMenu(datos);
            dispose();
        });
        fondo.add(btnVolver);

        //----------------- BUSCAR ------------
        //DocumentListener escucha prácticamente lo que hay en el componente
        DocumentListener buscador = new DocumentListener() {

            //Cuando se escriba algo
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscar(); //Busca automáticamente
            }

            //Cuando elimine algo
            @Override
            public void removeUpdate(DocumentEvent e) {
                buscar(); //Busca automáticamente
            }

            //Esta cosa porque obliga a que el update esté sí o sí
            @Override
            public void changedUpdate(DocumentEvent e) {
                buscar(); //Por si, que busque automáticamente
            }
        };

        txtIdPista.getDocument().addDocumentListener(buscador);
        txtIdPregunta.getDocument().addDocumentListener(buscador);
    }

    public void Editar() {
        //se obtiene el texto del id pista
        String idString = txtIdPista.getText().trim();

        //se obtiene el texto del area pista
        String contenido = txtAreaPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID_Ayuda.");
            return;
        }

        //Si el contenido de la pista está vacío
        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el contenido de la pista.");
            return;
        }

        //Si todo ha ido bien, se parsea el id para psarlo a int
        int id = Integer.parseInt(idString);

        //Recordemos que actualizarPistaTexto devuelve un boolean
        //Así que si es true, significa que se logró actualizar correctamente
        if (dao.actualizarPistaTexto(contenido, datos.getMinijuego(), datos.getCategoria(), datos.getNivel(), id)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue actualizada correctamente.");
            //Si devolvió false
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID dentro del: \n"
                    + "Minijuego: " + datos.getMinijuego() + "\n"
                    + "Categoría: " + datos.getCategoria() + "\n"
                    + "Nivel: " + datos.getNivel() + "\n"
            );
        }

        cargarTabla(); //carga la tabla
    }

    public void Agregar() {
        //se obtiene el texto del id pregunta
        String idString = txtIdPregunta.getText().trim();

        //se obtiene el texto del area pista
        String contenido = txtAreaPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID_Pregunta.");
            return;
        }

        //Si el contenido de la pista está vacío
        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el contenido de la pista.");
            return;
        }

        //Si todo ha ido bien, se parsea el id para pasarlo a int
        int id = Integer.parseInt(idString);

        //Recordemos que agregarPistaTexto también devuelve un boolean
        //Así que si es true, significa que se logró actualizar correctamente
        if (dao.agregarPistaTexto(
                id,
                contenido,
                datos.getMinijuego(),
                datos.getCategoria(),
                datos.getNivel()
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue agregada correctamente.");
            //Si devolvió false
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID dentro del: \n"
                    + "Minijuego: " + datos.getMinijuego() + "\n"
                    + "Categoría: " + datos.getCategoria() + "\n"
                    + "Nivel: " + datos.getNivel() + "\n"
            );
        }
        cargarTabla(); //Carga la tabla
    }

    //el método para buscar
    private void buscar() {
        //Obtiene el texto de la pista
        String textoPista = txtIdPista.getText().trim();
        String textoPregunta = txtIdPregunta.getText().trim();

        //si están ambas vacías
        if (textoPista.isEmpty() && textoPregunta.isEmpty()) {
            cargarTabla();//carga la tabla normal con todas las pistas
            return;
        }

        try {

            buscarTabla(textoPista, textoPregunta);

        } catch (NumberFormatException ex) {
            modelo.setRowCount(0);
        }
    }

    public void Eliminar() {
        //se obtiene el texto del id pregunta
        String idString = txtIdPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID_Ayuda.");
            return;
        }

        //se declara una opción de mensaje
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta pista?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        //si el usuario no quiere eliminarlo
        if (opcion != JOptionPane.YES_OPTION) {
            return; //regresa
        }

        //Si todo ha ido bien, se parsea el id para pasarlo a int
        int id = Integer.parseInt(idString);

        //Recordemos que eliminarPistaTexto también devuelve un boolean
        //Así que si es true, significa que se logró actualizar correctamente
        if (dao.eliminarPistaTexto(
                id,
                datos.getMinijuego(),
                datos.getCategoria(),
                datos.getNivel()
        )) {
            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue eliminada correctamente.");
            //Si devolvió false
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID dentro del: \n"
                    + "Minijuego: " + datos.getMinijuego() + "\n"
                    + "Categoría: " + datos.getCategoria() + "\n"
                    + "Nivel: " + datos.getNivel() + "\n"
            );
        }

        cargarTabla(); //Carga la tabla
    }

    public void cargarTabla() {

        //Para limpiar la tabla
        modelo.setRowCount(0);

        ResultSet rs = dao.obtenerPistasTexto(
                datos.getMinijuego(),
                datos.getCategoria(),
                datos.getNivel()
        );

        //si está vacío
        if (rs == null) {
            return;
        }

        try {
            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getInt("id_ayuda"),
                    rs.getInt("id_pregunta"),
                    rs.getString("contenido")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Utilizamos Integer en los parámetros porque Integer permite null
    public void buscarTabla(String id_ayuda, String id_pregunta) {

        modelo.setRowCount(0);

        ResultSet rs = dao.buscarPistaTexto(
                id_ayuda, id_pregunta,
                datos.getMinijuego(),
                datos.getCategoria(),
                datos.getNivel());

        if (rs == null) {
            return;
        }

        try {

            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getInt("id_ayuda"),
                    rs.getInt("id_pregunta"),
                    rs.getString("contenido")
                });

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Limpiar() {
        txtAreaPista.setText("");
        txtIdPista.setText("");
        txtIdPregunta.setText("");
    }
}
