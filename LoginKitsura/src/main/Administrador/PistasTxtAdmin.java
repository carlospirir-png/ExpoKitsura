//-------------------------- PISTAS DE TEXTO --------------------------
package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import java.sql.*;

public class PistasTxtAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JTextArea txtAreaPista;
    private JTextField txtIdPista;

    private DefaultTableModel modelo;
    private JTable tablaPistas;

    PistasDAO dao = new PistasDAO();

    public PistasTxtAdmin() {
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
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTituloSeccion = new JLabel("PISTAS: TEXTO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO (TEXTO PISTA) ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));
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

        JLabel lblInstruccionId = new JLabel("Ingrese el ID.");
        lblInstruccionId.setFont(fuente2.deriveFont(26f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);
        panelDerecho.add(lblInstruccionId);

        JLabel lblid = new JLabel("(EDITAR/ELIMINAR: id_ayuda)            (AGREGAR: id_pregunta)");
        lblid.setFont(fuente2.deriveFont(15f));
        lblid.setForeground(Color.WHITE);
        lblid.setBounds(230, 33, 820, 35);
        panelDerecho.add(lblid);

        txtIdPista = new JTextField();
        txtIdPista.setFont(fuente1.deriveFont(36f));
        txtIdPista.setBounds(30, 75, 820, 55);
        panelDerecho.add(txtIdPista);

        //---------------- TABLA (ID 1/3, CONTENIDO 2/3) ----------------
        modelo = new DefaultTableModel(
                new String[]{"ID", "CONTENIDO"}, 0) {
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
        tablaPistas.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaPistas.getColumnModel().getColumn(1).setPreferredWidth(560);

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
                
                
                if (fila != -1) {
                    //getValueAt(fila, columna) saca los datos de la tabla
                    //se parsean de una vez a String y se colocan de una vez en los txt
                    txtIdPista.setText(modelo.getValueAt(fila, 0).toString());
                    txtAreaPista.setText(modelo.getValueAt(fila, 1).toString());
                }
            }
        });

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
            new PistasMenu();
            dispose();
        });
        fondo.add(btnVolver);
    }

    public void Editar() {
        //se obtiene el texto del id pista
        String idString = txtIdPista.getText().trim();

        //se obtiene el texto del area pista
        String contenido = txtAreaPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID.");
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
        if (dao.actualizarPistaTexto(id, contenido)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue actualizada correctamente.");
            //Si devolvió false
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID.");
        }

        cargarTabla(); //carga la tabla
    }

    public void Agregar() {
        //se obtiene el texto del id pregunta
        String idString = txtIdPista.getText().trim();

        //se obtiene el texto del area pista
        String contenido = txtAreaPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID.");
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
        if (dao.agregarPistaTexto(id, contenido)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue agregada correctamente.");
            //Si devolvió false
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID.");
        }

        cargarTabla(); //Carga la tabla
    }

    public void Eliminar() {
        //se obtiene el texto del id pregunta
        String idString = txtIdPista.getText().trim();

        //Si el id que se obtuvo está vacío
        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID.");
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
        if (dao.eliminarPistaTexto(id)) {
            JOptionPane.showMessageDialog(
                    this,
                    "La pista fue eliminada correctamente.");
            //Si devolvió false
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No existe una pista de texto con ese ID.");
        }

        cargarTabla(); //Carga la tabla
    }

    public void cargarTabla() {

        //Para limpiar la tabla
        modelo.setRowCount(0);

        ResultSet rs = dao.obtenerPistasTexto();

        //si está vacío
        if (rs == null) {
            return;
        }

        try {
            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getInt("id_ayuda"),
                    rs.getString("contenido")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PistasTxtAdmin();
    }

}
