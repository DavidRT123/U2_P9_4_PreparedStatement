/*
 * Crea un programa, llámalo U2_P9_4_PreparedStatement, para visualizar los datos de los profesores
 * de un determinado departamento.
 * Al final de la lista aparecerá una línea del estilo:
 * El departamento INFORMATICA tiene XX profesores con un sueldo máximo de xxxxxx.
 * Si el departamento no existe deberá aparecer la línea:
 * El departamento no existe.
 * Si el departamento no tiene profesores deberá aparecer la línea:
 * El departamento XXXXXX no tiene profesores.
 */
package u2_p9_4_preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mdfda
 */
public class U2_P9_4_PreparedStatement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dept_no = args[0], sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\mdfda\\Desktop\\DAM\\Acceso a Datos (AD)\\Tema 2\\Ejercicios\\clase\\bases\\sqlite\\ejemplo.db");

            sql = "SELECT DEPARTAMENTOS.DNOMBRE AS 'NOMBRE_DEPARTAMENTO', MAX(SALARIO) AS 'MAX_SALARIO', COUNT(NRM) AS 'NUM_PROFESORES' FROM DEPARTAMENTOS LEFT JOIN PROFESORES ON PROFESORES.DEPT_NO = DEPARTAMENTOS.DEPT_NO WHERE DEPARTAMENTOS.DEPT_NO = ?";

            PreparedStatement pS = con.prepareStatement(sql);

            pS.setInt(1, Integer.parseInt(dept_no));

            ResultSet result = pS.executeQuery();

            if (result.getString("NOMBRE_DEPARTAMENTO") == null) {
                System.out.println("El departamento no existe");
            } else {
                if (result.getInt("NUM_PROFESORES") == 0) {
                    System.out.println("El departamento " + result.getString("NOMBRE_DEPARTAMENTO") + " no tiene profesores");
                }else{
                    System.out.println("El departamento de " + result.getString("NOMBRE_DEPARTAMENTO") + " tiene " + result.getInt("NUM_PROFESORES") + " profesores con un sueldo máximo de: " + result.getString("MAX_SALARIO"));
                }
            }
            
            result.close();
            pS.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U2_P9_4_PreparedStatement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Código de error: " + ex.getErrorCode());
            System.out.println("Mensaje de error: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
        }
    }

}
