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
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    conn = new conectaDAO().connectDB();
    try {
        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);
        prep.executeUpdate();
    } catch (Exception e) {
        System.out.println("Erro ao vender produto: " + e.getMessage());
    }
}

public ArrayList<ProdutosDTO> listarProdutos() {
    String sql = "SELECT * FROM produtos"; 
    conn = new conectaDAO().connectDB();
    listagem.clear(); // Limpa a lista para não duplicar itens ao atualizar a tela

    try {
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
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
    }
    return listagem;
}

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'"; // Filtro solicitado
    conn = new conectaDAO().connectDB();
    listagem.clear(); // Limpa a lista para evitar duplicidade de dados

    try {
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
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    }
    return listagem;
}
}