package br.edu.poo.m0013;

import br.edu.poo.m0013.database.InicializadorBanco;
import br.edu.poo.m0013.api.EnderecoViaCep;
import br.edu.poo.m0013.api.ViaCepClient;
import br.edu.poo.m0013.dao.FavoritoDAO;
import br.edu.poo.m0013.dao.LeitorDAO;
import br.edu.poo.m0013.dao.ManhwaDAO;
import br.edu.poo.m0013.model.Leitor;
import br.edu.poo.m0013.model.Manhwa;
import br.edu.poo.m0013.model.StatusManhwa;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final LeitorDAO leitorDAO = new LeitorDAO();
    private static final ManhwaDAO manhwaDAO = new ManhwaDAO();
    private static final FavoritoDAO favoritoDAO = new FavoritoDAO();

    public static void main(String[] args) {
        try {
            InicializadorBanco.criarTabelas();
        } catch (SQLException e) {
            System.out.println("[!] Não foi possível conectar ao MySQL: " + e.getMessage());
            System.out.println("Verifique se o MySQL está ligado e se o usuário e a senha do .env estão corretos.");
            return;
        }
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha seu caminho: ");
            try {
                executar(opcao);
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("[!] Dado repetido ou vínculo inexistente "
                        + "(ex.: email já cadastrado, favorito já existente).");
            } catch (SQLException e) {
                System.out.println("[!] Erro no banco de dados: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[!] " + e.getMessage());
            }
        } while (opcao != 0);
        System.out.println("Até a próxima aventura!");
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("      VELLUM - Biblioteca de Manhwas");
        System.out.println("==========================================");
        System.out.println("-- Guilda dos Leitores --");
        System.out.println(" 1. Registrar leitor");
        System.out.println(" 2. Listar leitores");
        System.out.println(" 3. Atualizar leitor");
        System.out.println(" 4. Remover leitor");
        System.out.println("-- Acervo de Manhwas --");
        System.out.println(" 5. Registrar manhwa");
        System.out.println(" 6. Listar manhwas");
        System.out.println(" 7. Atualizar manhwa");
        System.out.println(" 8. Remover manhwa");
        System.out.println("-- Grimório de Favoritos --");
        System.out.println(" 9. Favoritar manhwa");
        System.out.println("10. Ver favoritos de um leitor");
        System.out.println("11. Remover favorito");
        System.out.println(" 0. Sair");
    }

    private static void executar(int opcao) throws SQLException {
        switch (opcao) {
            case 1:
                cadastrarLeitor();
                break;
            case 2:
                listarLeitores();
                break;
            case 3:
                atualizarLeitor();
                break;
            case 4:
                removerLeitor();
                break;
            case 5:
                cadastrarManhwa();
                break;
            case 6:
                listarManhwas();
                break;
            case 7:
                atualizarManhwa();
                break;
            case 8:
                removerManhwa();
                break;
            case 9:
                favoritar();
                break;
            case 10:
                verFavoritos();
                break;
            case 11:
                removerFavorito();
                break;
            case 0:
                break;
            default:
                System.out.println("[!] Opção inválida, aventureiro.");
        }
    }

    // ---------- Leitores ----------

    private static void cadastrarLeitor() throws SQLException {
        System.out.println("\n--- Novo leitor ---");
        Leitor leitor = new Leitor();
        leitor.setNome(lerTexto("Nome: "));
        leitor.setEmail(lerEmail("Email: "));
        String cep = lerCepOpcional("CEP (8 números, Enter para pular): ");
        if (cep != null) {
            preencherEndereco(leitor, cep);
        }
        leitorDAO.inserir(leitor);
        System.out.println("Leitor registrado na guilda!");
    }

    private static void listarLeitores() throws SQLException {
        System.out.println("\n--- Leitores da guilda ---");
        List<Leitor> leitores = leitorDAO.listarTodos();
        if (leitores.isEmpty()) {
            System.out.println("Nenhum leitor registrado ainda.");
        }
        for (Leitor leitor : leitores) {
            exibirLeitor(leitor);
        }
    }

    private static void atualizarLeitor() throws SQLException {
        listarLeitores();
        int id = lerInteiro("ID do leitor a atualizar: ");
        Leitor leitor = leitorDAO.buscarPorId(id);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        System.out.println("Aperte Enter para manter o valor atual.");

        String nome = lerTextoOpcional("Nome [" + leitor.getNome() + "]: ");
        if (nome != null) {
            leitor.setNome(nome);
        }

        while (true) {
            String email = lerTextoOpcional("Email [" + leitor.getEmail() + "]: ");
            if (email == null) {
                break;
            }
            if (emailValido(email)) {
                leitor.setEmail(email);
                break;
            }
            System.out.println("[!] Email inválido (precisa ter @ e não ter espaços).");
        }

        String cep = lerCepOpcional("Novo CEP (8 números, Enter mantém): ");
        if (cep != null) {
            preencherEndereco(leitor, cep);
        }

        leitorDAO.atualizar(leitor);
        System.out.println("Leitor atualizado!");
    }

    private static void removerLeitor() throws SQLException {
        listarLeitores();
        int id = lerInteiro("ID do leitor a remover: ");
        Leitor leitor = leitorDAO.buscarPorId(id);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        if (confirmar("Remover " + leitor.getNome() + " e todos os seus favoritos?")) {
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
        }
    }

    private static void exibirLeitor(Leitor leitor) {
        System.out.println(leitor);
        if (leitor.getCep() != null) {
            System.out.println("    CEP " + leitor.getCep() + " - " + leitor.getLogradouro()
                    + ", " + leitor.getBairro() + " - " + leitor.getCidade()
                    + "/" + leitor.getUf());
        }
    }

    // ---------- Manhwas ----------

    private static void cadastrarManhwa() throws SQLException {
        System.out.println("\n--- Novo manhwa no acervo ---");
        Manhwa manhwa = new Manhwa();
        manhwa.setTitulo(lerTexto("Título: "));
        manhwa.setAutor(lerTexto("Autor: "));
        manhwa.setGenero(lerTextoOpcional("Gênero (Enter para pular): "));
        manhwa.setSinopse(lerTextoOpcional("Sinopse (Enter para pular): "));
        manhwa.setQuantidadeCapitulos(lerInteiroNaoNegativo("Quantidade de capítulos: "));
        manhwa.setStatus(lerStatus());
        manhwaDAO.inserir(manhwa);
        System.out.println("Manhwa registrado no acervo!");
    }

    private static void listarManhwas() throws SQLException {
        System.out.println("\n--- Acervo de manhwas ---");
        List<Manhwa> manhwas = manhwaDAO.listarTodos();
        if (manhwas.isEmpty()) {
            System.out.println("O acervo está vazio.");
        }
        for (Manhwa manhwa : manhwas) {
            exibirManhwa(manhwa);
        }
    }

    private static void atualizarManhwa() throws SQLException {
        listarManhwas();
        int id = lerInteiro("ID do manhwa a atualizar: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(id);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        System.out.println("Aperte Enter para manter o valor atual.");

        String titulo = lerTextoOpcional("Título [" + manhwa.getTitulo() + "]: ");
        if (titulo != null) {
            manhwa.setTitulo(titulo);
        }
        String autor = lerTextoOpcional("Autor [" + manhwa.getAutor() + "]: ");
        if (autor != null) {
            manhwa.setAutor(autor);
        }
        String genero = lerTextoOpcional("Gênero [" + manhwa.getGenero() + "]: ");
        if (genero != null) {
            manhwa.setGenero(genero);
        }
        String sinopse = lerTextoOpcional("Sinopse (Enter mantém): ");
        if (sinopse != null) {
            manhwa.setSinopse(sinopse);
        }
        manhwa.setQuantidadeCapitulos(lerInteiroOuManter(
                "Capítulos [" + manhwa.getQuantidadeCapitulos() + "]: ",
                manhwa.getQuantidadeCapitulos()));

        while (true) {
            String op = lerTextoOpcional("Status [" + manhwa.getStatus()
                    + "] (1 - Em publicação, 2 - Finalizada, Enter mantém): ");
            if (op == null) {
                break;
            }
            if (op.equals("1")) {
                manhwa.setStatus(StatusManhwa.EM_PUBLICACAO);
                break;
            }
            if (op.equals("2")) {
                manhwa.setStatus(StatusManhwa.FINALIZADA);
                break;
            }
            System.out.println("[!] Digite 1, 2 ou apenas Enter.");
        }

        manhwaDAO.atualizar(manhwa);
        System.out.println("Manhwa atualizado!");
    }

    private static void removerManhwa() throws SQLException {
        listarManhwas();
        int id = lerInteiro("ID do manhwa a remover: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(id);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        if (confirmar("Remover \"" + manhwa.getTitulo() + "\" do acervo e dos favoritos?")) {
            manhwaDAO.deletar(id);
            System.out.println("Manhwa removido do acervo.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void exibirManhwa(Manhwa manhwa) {
        System.out.println(manhwa);
        if (manhwa.getGenero() != null) {
            System.out.println("    Gênero: " + manhwa.getGenero());
        }
        if (manhwa.getSinopse() != null) {
            System.out.println("    Sinopse: " + manhwa.getSinopse());
        }
    }

    // ---------- Favoritos ----------

    private static void favoritar() throws SQLException {
        listarLeitores();
        int leitorId = lerInteiro("ID do leitor: ");
        Leitor leitor = leitorDAO.buscarPorId(leitorId);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        listarManhwas();
        int manhwaId = lerInteiro("ID do manhwa: ");
        Manhwa manhwa = manhwaDAO.buscarPorId(manhwaId);
        if (manhwa == null) {
            System.out.println("[!] Manhwa não encontrado.");
            return;
        }
        favoritoDAO.adicionar(leitorId, manhwaId);
        System.out.println(leitor.getNome() + " guardou \"" + manhwa.getTitulo()
                + "\" no grimório de favoritos.");
    }

    private static void verFavoritos() throws SQLException {
        listarLeitores();
        int leitorId = lerInteiro("ID do leitor: ");
        Leitor leitor = leitorDAO.buscarPorId(leitorId);
        if (leitor == null) {
            System.out.println("[!] Leitor não encontrado.");
            return;
        }
        System.out.println("\n--- Grimório de " + leitor.getNome() + " ---");
        List<Manhwa> favoritos = favoritoDAO.listarPorLeitor(leitorId);
        if (favoritos.isEmpty()) {
            System.out.println("Nenhum favorito ainda.");
        }
        for (Manhwa manhwa : favoritos) {
            System.out.println(manhwa);
        }
    }

    private static void removerFavorito() throws SQLException {
        listarLeitores();
        int leitorId = lerInteiro("ID do leitor: ");
        List<Manhwa> favoritos = favoritoDAO.listarPorLeitor(leitorId);
        if (favoritos.isEmpty()) {
            System.out.println("Esse leitor não tem favoritos.");
            return;
        }
        for (Manhwa manhwa : favoritos) {
            System.out.println(manhwa);
        }
        int manhwaId = lerInteiro("ID do manhwa a remover dos favoritos: ");
        favoritoDAO.remover(leitorId, manhwaId);
        System.out.println("Favorito removido.");
    }

    // ---------- Entrada de dados validada ----------

    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("[!] Este campo não pode ficar vazio.");
        }
    }

    private static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem);
        String texto = entrada.nextLine().trim();
        return texto.isEmpty() ? null : texto;
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Digite um número inteiro válido.");
            }
        }
    }

    private static int lerInteiroNaoNegativo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("[!] O valor não pode ser negativo.");
        }
    }

    private static int lerInteiroOuManter(String mensagem, int atual) {
        while (true) {
            String texto = lerTextoOpcional(mensagem);
            if (texto == null) {
                return atual;
            }
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= 0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // cai na mensagem de erro abaixo
            }
            System.out.println("[!] Digite um número inteiro igual ou maior que 0.");
        }
    }

    private static String lerEmail(String mensagem) {
        while (true) {
            String email = lerTexto(mensagem);
            if (emailValido(email)) {
                return email;
            }
            System.out.println("[!] Email inválido (precisa ter @ e não ter espaços).");
        }
    }

    private static boolean emailValido(String email) {
        return email.contains("@") && !email.contains(" ");
    }

    private static String lerCepOpcional(String mensagem) {
        while (true) {
            String cep = lerTextoOpcional(mensagem);
            if (cep == null) {
                return null;
            }
            cep = cep.replace("-", "");
            if (cepValido(cep)) {
                return cep;
            }
            System.out.println("[!] O CEP deve ter exatamente 8 números.");
        }
    }

    private static boolean cepValido(String cep) {
        if (cep.length() != 8) {
            return false;
        }
        for (int i = 0; i < cep.length(); i++) {
            if (!Character.isDigit(cep.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static StatusManhwa lerStatus() {
        while (true) {
            System.out.println("Status: 1 - Em publicação | 2 - Finalizada");
            int opcao = lerInteiro("Escolha: ");
            if (opcao == 1) {
                return StatusManhwa.EM_PUBLICACAO;
            }
            if (opcao == 2) {
                return StatusManhwa.FINALIZADA;
            }
            System.out.println("[!] Escolha 1 ou 2.");
        }
    }

    private static boolean confirmar(String mensagem) {
        System.out.print(mensagem + " (s/n): ");
        return entrada.nextLine().trim().equalsIgnoreCase("s");
    }
}