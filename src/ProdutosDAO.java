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
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    // Alterado de void para boolean para retornar o status da operação
    public boolean cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB(); // Abre a conexão
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate(); // Executa a inserção
            return true; // Sucesso
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false; // Falha
        } finally {
            try { conn.close(); } catch (Exception e) {} // Fecha conexão
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        // Lógica de listagem para a próxima etapa
        return listagem;
    }
}