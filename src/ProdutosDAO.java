/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ProdutosDAO {

    conectaDAO conectaDAO = new conectaDAO();
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    ProdutosDTO produto = new ProdutosDTO();

    private final String[] tableColumns = {"Id", "Nome", "Valor", "Status"};

    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {
        conn = new conectaDAO().connectDB();

        String sql = "insert into produtos (nome, valor, status) values (?,?,?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            int status = prep.executeUpdate();

            if (status > 0) {
                JOptionPane.showMessageDialog(null, "Produto "
                        + "cadastrado com sucesso.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto.");
            System.out.println("Erro: " + e);
        } finally {
            conectaDAO.fecharConexao(conn);
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        String sql = "select id, nome, valor, status from produtos";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);

            }
            resultset.close();
            return listagem;
        } catch (SQLException e) {
            return null;
        }
    }
}
