package br.edu.poo.m0013.ui;

import br.edu.poo.m0013.api.EnderecoViaCep;
import br.edu.poo.m0013.api.ViaCepClient;
import br.edu.poo.m0013.dao.LeitorDAO;
import br.edu.poo.m0013.model.Leitor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuLeitor {

    private static final LeitorDAO leitorDAO = new LeitorDAO();

    public static void cadastrar() throws SQLException {
        System.out.println("\n--- Novo leitor ---");
        Leitor leitor = new Leitor();
        leitor.setNome(Entrada.lerTexto("Nome: "));
        leitor.setEmail(Entrada.lerEmail("Email: "));
        String cep = Entrada.lerCepOpcional("CEP (8 números, Enter para pular): ");
        if (cep != null) {
            preencherEndereco(leitor, cep);
        } else {
            System.out.println("Sem problemas! Você pode informar o CEP depois, em \"Atualizar leitor\" (opção 3).");
        }
        leitorDAO.inserir(leitor);
        System.out.println("Leitor registrado na guilda!");
    }

    public static void listar() throws SQLException {
        System.out.println("\n--- Leitores da guilda ---");
        List<Leitor> leitores = leitorDAO.listarTodos();
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor registrado ainda.");
        }
        for (Leitor leitor : leitores) {
            Exibicao.mostrar(leitor);
        }
    }

    public static void atualizar() throws SQLException {
        int id = Entrada.lerInteiro("ID do leitor a atualizar: ");
        Leitor leitor = leitorDAO.buscarPorId(id);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        System.out.println("Aperte Enter para manter o valor atual.");

        String nome = Entrada.lerTextoOpcional("Nome [" + leitor.getNome() + "]: ");
        if (nome != null) {
            leitor.setNome(nome);
        }

        String email = Entrada.lerEmailOuManter("Email [" + leitor.getEmail() + "]: ");
        if (email != null) {
            leitor.setEmail(email);
        }

        String cep = Entrada.lerCepOpcional("Novo CEP (8 números, Enter mantém): ");
        if (cep != null) {
            preencherEndereco(leitor, cep);
        }

        leitorDAO.atualizar(leitor);
        System.out.println("Leitor atualizado!");
    }

    public static void remover() throws SQLException {
        int id = Entrada.lerInteiro("ID do leitor a remover: ");
        Leitor leitor = leitorDAO.buscarPorId(id);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        if (Entrada.confirmar("Remover " + leitor.getNome() + " e todos os seus favoritos?")) {
            leitorDAO.deletar(id);
            System.out.println("Leitor removido da guilda.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void preencherEndereco(Leitor leitor, String cep) {
        try {
            EnderecoViaCep endereco = ViaCepClient.buscar(cep);
            if (endereco == null) {
                System.out.println("[!] CEP não encontrado no ViaCEP. Endereço não preenchido.");
                System.out.println("    Você pode informar um CEP válido depois, em \"Atualizar leitor\" (opção 3).");
                return;
            }
            leitor.setCep(cep);
            leitor.setLogradouro(endereco.getLogradouro());
            leitor.setBairro(endereco.getBairro());
            leitor.setCidade(endereco.getLocalidade());
            leitor.setUf(endereco.getUf());
            System.out.println("Endereço encontrado: " + endereco.getLogradouro() + ", "
                    + endereco.getBairro() + " - " + endereco.getLocalidade()
                    + "/" + endereco.getUf());
        } catch (IOException e) {
            System.out.println("[!] Não foi possível consultar o ViaCEP: " + e.getMessage());
            System.out.println("    Você pode informar o CEP depois, em \"Atualizar leitor\" (opção 3).");
        }
    }


}