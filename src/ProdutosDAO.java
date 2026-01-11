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
    String sql = "SELECT * FROM produtos"; // Seleciona todos os produtos do banco
    conn = new conectaDAO().connectDB(); // Abre a conexão

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery(); // Executa a consulta e guarda no resultset

        // Enquanto houver linhas no banco de dados, ele cria um objeto e adiciona na lista
        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            
            produto.setId(resultset.getInt("id"));         // Pega o id da coluna do banco
            produto.setNome(resultset.getString("nome"));   // Pega o nome
            produto.setValor(resultset.getInt("valor"));    // Pega o valor
            produto.setStatus(resultset.getString("status")); // Pega o status
            
            listagem.add(produto); // Adiciona o objeto na lista
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
    } finally {
        try { 
            conn.close(); // Sempre feche a conexão após o uso
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
    
    return listagem; // Retorna a lista preenchida para a listagemVIEW
    }
}