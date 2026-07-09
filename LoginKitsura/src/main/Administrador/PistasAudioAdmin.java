package main.Administrador;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import main.conexion.Conexion;

import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PistasAudioAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JTextField txtIdPista;
    private JTable tablaPistas;
    private DefaultTableModel modelo;

    private String rutaAudioSeleccionado = "";

   // CONEXIÓN A LA BASE DE DATOS
    private Conexion conexion = new Conexion();
    
    private DatosConfiguracion datos;

    public PistasAudioAdmin(DatosConfiguracion datos) {
        
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
        setTitle("Pistas: Audio");
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

        JLabel lblTituloSeccion = new JLabel("PISTAS: AUDIO", JLabel.CENTER);

        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);

        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO ----------------
        // Se aumenta el alto de 420 a 630 para albergar el nuevo panel interno
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));

        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 200, 500, 630);

        fondo.add(panelIzquierdo);

        JLabel lblSubirAudio = new JLabel("Suba el Audio de la pista");

        lblSubirAudio.setFont(fuente2.deriveFont(28f));
        lblSubirAudio.setForeground(Color.WHITE);
        lblSubirAudio.setBounds(30, 40, 440, 40);

        panelIzquierdo.add(lblSubirAudio);

        JPanel panelOndaAudio = new JPanel();

        panelOndaAudio.setBackground(Color.DARK_GRAY);
        panelOndaAudio.setBounds(30, 100, 440, 90);
        panelOndaAudio.setLayout(new BorderLayout());

        JLabel lblIconoOnda = new JLabel(" ||||||ıııııı||||||ıııııı||||||", JLabel.CENTER);

        lblIconoOnda.setFont(new Font("Arial", Font.BOLD, 22));
        lblIconoOnda.setForeground(Color.WHITE);

        panelOndaAudio.add(lblIconoOnda, BorderLayout.CENTER);

        panelIzquierdo.add(panelOndaAudio);

        //---------------- BOTÓN CARGAR AUDIO ----------------
        JButton btnCargar = new DecoracionBotones(
                "CARGAR",
                DecoracionBotones.ROSA,
                DecoracionBotones.ROJO,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.ROJO,
                DecoracionBotones.ROSA,
                DecoracionBotones.ROSA);

        btnCargar.setFont(fuente2.deriveFont(26f));
        btnCargar.setBounds(130, 230, 240, 65);

        panelIzquierdo.add(btnCargar);

        //---------------- PANEL DE OPERACIONES (BAJO CARGAR) ----------------
        FondoPanelSemi panelOperaciones = new FondoPanelSemi(new Color(0, 0, 0, 100));
        panelOperaciones.setLayout(null);
        panelOperaciones.setBounds(30, 320, 440, 270);
        panelIzquierdo.add(panelOperaciones);

        // Botón Buscar
        JButton btnBuscar = new DecoracionBotones(
                "BUSCAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnBuscar.setFont(fuente2.deriveFont(24f));
        btnBuscar.setBounds(50, 25, 340, 55);
        panelOperaciones.add(btnBuscar);

        // Botón Editar
        JButton btnEditar = new DecoracionBotones(
                "EDITAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnEditar.setFont(fuente2.deriveFont(24f));
        btnEditar.setBounds(50, 105, 340, 55);
        panelOperaciones.add(btnEditar);

        // Botón Eliminar
        JButton btnEliminar = new DecoracionBotones(
                "ELIMINAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnEliminar.setFont(fuente2.deriveFont(24f));
        btnEliminar.setBounds(50, 185, 340, 55);
        panelOperaciones.add(btnEliminar);

        //---------------- PANEL DERECHO ----------------
        FondoPanelSemi panelDerecho = new FondoPanelSemi(new Color(0, 0, 0, 130));

        panelDerecho.setLayout(null);
        panelDerecho.setBounds(680, 170, 880, 560);

        fondo.add(panelDerecho);

        JLabel lblInstruccionId = new JLabel("Ingrese el ID de la pregunta");

        lblInstruccionId.setFont(fuente2.deriveFont(22f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);

        panelDerecho.add(lblInstruccionId);

        txtIdPista = new JTextField();

        txtIdPista.setFont(fuente1.deriveFont(26f));
        txtIdPista.setBounds(30, 75, 820, 55);

        panelDerecho.add(txtIdPista);

        //---------------- TABLA ----------------
        modelo = new DefaultTableModel(
                new String[]{"ID Pregunta", "Audio"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPistas = new JTable(modelo);

        tablaPistas.setFont(fuente1.deriveFont(18f));
        tablaPistas.setRowHeight(42);

        tablaPistas.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaPistas.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);

        scrollPaneTabla.setBounds(30, 155, 820, 370);

        panelDerecho.add(scrollPaneTabla);

        //---------------- BOTÓN GUARDAR ----------------
        JButton btnGuardar = new DecoracionBotones(
                "GUARDAR",
                DecoracionBotones.VERDE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.AZUL,
                DecoracionBotones.VERDE,
                DecoracionBotones.VERDE);

        btnGuardar.setFont(fuente2.deriveFont(26f));
        btnGuardar.setBounds(980, 760, 250, 70);

        fondo.add(btnGuardar);

        //---------------- BOTÓN MOSTRAR (BAJO GUARDAR) ----------------
        JButton btnMostrar = new DecoracionBotones(
                "MOSTRAR",
                DecoracionBotones.AZUL,
                DecoracionBotones.GRIS,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AZUL);

        btnMostrar.setFont(fuente2.deriveFont(26f));
        btnMostrar.setBounds(980, 850, 250, 70);

        fondo.add(btnMostrar);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones(
                "VOLVER",
                DecoracionBotones.AZUL,
                DecoracionBotones.GRIS,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AZUL);

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1550, 860, 300, 65);

        btnVolver.addActionListener(e -> {
            new PistasMenu(datos);
            dispose();
        });

        fondo.add(btnVolver);

        //---------------- EVENTOS ----------------
        btnCargar.addActionListener(e -> seleccionarAudio());
        btnGuardar.addActionListener(e -> guardarAudioBD());
        btnBuscar.addActionListener(e -> buscarAudio());
        btnEditar.addActionListener(e -> editarAudio());
        btnEliminar.addActionListener(e -> eliminarAudio());
        btnMostrar.addActionListener(e -> mostrarAudios());

        //---------------- MASCOTA ----------------
        JLabel staticMascotaLibro = new JLabel();

        try {
            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));

            Image imgEscalada = iconMascota.getImage()
                    .getScaledInstance(550, 550, Image.SCALE_SMOOTH);

            staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {
            staticMascotaLibro.setText("~");
        }
        staticMascotaLibro.setBounds(1490, 220, 550, 550);

        fondo.add(staticMascotaLibro);
    }

    //---------------- SELECCIONAR AUDIO ----------------
    private void seleccionarAudio() {

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Archivos de Audio",
                "mp3", "wav", "ogg");

        chooser.setFileFilter(filtro);

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            rutaAudioSeleccionado = archivo.getAbsolutePath();

            JOptionPane.showMessageDialog(
                    this,
                    "Audio seleccionado:\n" + archivo.getName());
        }
    }
    
    //--------------- MOSTRAR AUDIOS ----------------
    private void mostrarAudios() {
        modelo.setRowCount(0);
        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT id_pregunta, audio FROM Ayuda WHERE tipo='audio'";
            PreparedStatement ps = con.prepareStatement(sql);
            var rs = ps.executeQuery();
            while (rs.next()) {
                String nombreArchivo = new File(rs.getString("audio")).getName();
                modelo.addRow(new Object[]{
                    rs.getInt("id_pregunta"),
                    nombreArchivo
                });
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar los audios");
        }
    }

    //---------------- GUARDAR EN MYSQL ----------------
    private void guardarAudioBD() {
        String idTexto = txtIdPista.getText().trim();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID de la pregunta");
            return;
        }
        if (rutaAudioSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un audio");
            return;
        }   
        try {
            int idPregunta = Integer.parseInt(idTexto);
            Connection con = conexion.getConnection();

            String sql = "INSERT INTO Ayuda (id_pregunta, tipo, audio) VALUES (?, 'audio', ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPregunta);
            ps.setString(2, rutaAudioSeleccionado);
            ps.executeUpdate();
            
            String nombreArchivo = new File(rutaAudioSeleccionado).getName();
            modelo.addRow(new Object[]{
                idPregunta,
                nombreArchivo
            });
            
            JOptionPane.showMessageDialog(this, "Audio guardado correctamente");
            mostrarAudios();
            txtIdPista.setText("");
            rutaAudioSeleccionado = "";
            ps.close();
            con.close();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar:\n" + e.getMessage());
        }
    }
    
    // ---------------- BUSCAR EN MYSQL ----------------
    private void buscarAudio(){
        if(txtIdPista.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Ingrese el ID");
            return;
        }
        modelo.setRowCount(0);
        try{
            Connection con = conexion.getConnection();
            String sql="SELECT id_pregunta,audio FROM Ayuda WHERE id_pregunta=? AND tipo='audio'";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,Integer.parseInt(txtIdPista.getText()));
            var rs=ps.executeQuery();
            if(rs.next()){
                modelo.addRow(new Object[]{
                        rs.getInt("id_pregunta"),
                        new File(rs.getString("audio")).getName()
                });
                txtIdPista.setText("");
            }else{
                JOptionPane.showMessageDialog(this,
                        "No existe un audio para ese ID");
            }
            rs.close();
            ps.close();
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,
                    "Error al buscar");
        }
    }
    // --------------- ELIMINAR EN MYSQL ----------------
    private void eliminarAudio(){
        if(txtIdPista.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Ingrese el ID");
            return;
        }
        try{
            Connection con = conexion.getConnection();
            String sql="DELETE FROM Ayuda WHERE id_pregunta=? AND tipo='audio'";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,Integer.parseInt(txtIdPista.getText()));
            int filas=ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(this,
                        "Audio eliminado");
                        txtIdPista.setText("");
                mostrarAudios();
            }else{
                JOptionPane.showMessageDialog(this,
                        "No existe ese audio");
            }
            ps.close();
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar");
            }
        }
    // --------------- EDITAR EN MYSQL ----------------
    private void editarAudio(){
        if(txtIdPista.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Ingrese el ID");
            return;
        }
        seleccionarAudio();
        if(rutaAudioSeleccionado.isEmpty()){
            return;
        }
        try{
            Connection con = conexion.getConnection();
            String sql="UPDATE Ayuda SET audio=? WHERE id_pregunta=? AND tipo='audio'";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,rutaAudioSeleccionado);
            ps.setInt(2,Integer.parseInt(txtIdPista.getText()));
            int filas=ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(this,
                        "Audio actualizado");
                mostrarAudios();
                txtIdPista.setText("");
            }else{
                JOptionPane.showMessageDialog(this,
                        "No existe ese ID");
            }
            ps.close();
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,
                    "Error al editar");
        }
    }
}