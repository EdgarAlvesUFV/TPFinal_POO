// import java.util.ArrayList;

// public class Cliente extends Pessoa implements Conta{
//     String cpf;
//     String senha;
//     ArrayList<Parente> parentes;//lista parentes
//     ArrayList<Avaliacao> avaliacoes;//lista de avaliações
//     ArrayList<Fatura> faturas;//lista de faturas
//     double notaTotal;
//     ArrayList<String> listaCobranca;


//     public String getCpf() {
//         return cpf;
//     }
//     public void setCpf(String cpf) {
//         this.cpf = cpf;
//     }
//     public String getSenha() {
//         return senha;
//     }
//     public void setSenha(String senha) {
//         this.senha = senha;
//     }
//     public ArrayList<Parente> getParentes() {
//         return parentes;
//     }
//     public void setParentes(ArrayList<Parente> parentes) {
//         this.parentes = parentes;
//     }
//     public ArrayList<Avaliacao> getAvaliacoes() {
//         return avaliacoes;
//     }
//     public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
//         this.avaliacoes = avaliacoes;
//     }
//     public ArrayList<Fatura> getFaturas() {
//         return faturas;
//     }
//     public void setFaturas(ArrayList<Fatura> faturas) {
//         this.faturas = faturas;
//     }
//     public double getNotaTotal() {
//         return notaTotal;
//     }
//     public void setNotaTotal(double notaTotal) {
//         this.notaTotal = notaTotal;
//     }
//     public ArrayList<String> getListaCobranca() {
//         return listaCobranca;
//     }
//     public void setListaCobranca(ArrayList<String> listaCobranca) {
//         this.listaCobranca = listaCobranca;
//     }
//     public Cliente(String nome, String endereco, String telefone, String cpf, String senha, ArrayList<Parente> parentes,
//             ArrayList<Avaliacao> avaliacoes, ArrayList<Fatura> faturas, double notaTotal,
//             ArrayList<String> listaCobranca) {
//         super(nome, endereco, telefone);
//         this.cpf = cpf;
//         this.senha = senha;
//         this.parentes = parentes;
//         this.avaliacoes = avaliacoes;
//         this.faturas = faturas;
//         this.notaTotal = notaTotal;
//         this.listaCobranca = listaCobranca;
//     }
    

//     public void adicionarParente(Parente p){
//         this.parentes.add(p);
//     }
//     public void removerParente(String nome){
//         this.parentes.removeIf(parente -> parente.getNome().equals(nome))
//     }
   
//     public String getTipoConta(){ 
//         return conta.getTipoConta();
//     }
    
//     public void verExtrato(){
//         System.out.println("Extrato do cliente : "+ getNome() + ": Saldo = " + conta.getSaldo());
//     }

//     public void simularEmprestimo(int quantia){//quantia para emprestimo desejado
//         //consultar juros de cada agiota??
//         double taxaJurosAnual = 10.0;//taxa de juros//depois adicionar um para cada agiota
//         int prazoMes = 12;//mesma coisa, pegar de cada agiota um prazo
//         double jurosMensais = taxaJurosAnual/12;
//         double parcela = ((quantia*taxaJurosAnual)/12);
//         double valorTotal = parcela*prazoMes;

//         System.out.println("Simulação de empréstimo:");
//         System.out.println("Quantia: R$ " + quantia);
//         System.out.println("Parcelas: " + prazoMes);
//         System.out.println("Valor mensal: R$ " + parcela);
//         System.out.println("Total a pagar: R$ " + valorTotal);

//     }
    
//     public void realizarEmprestimo(int quantia, double taxaJuros){
//         //consultar lista de agiotas disponiveis
//         ArrayList<Agiota> listaAgiotas;
//         System.out.println("Lista de agiotas disponiveis para o emprestimo: ");
//         for (Agiota agiota : listaAgiotas) {
//             double jurosMensais = agiota.getTaxaJurosMensal();
//             int prazoMes = agiota.getPrazoMes();
//             double parcela = (quantia * (jurosMensais / 100));
//             double valorTotal = parcela * prazoMes;

//             System.out.println("Agiota: " + agiota.getNome());
//             System.out.println("Taxa de Juros Mensal: " + jurosMensais + "%");
//             System.out.println("Prazo: " + prazoMes + " meses");
//             System.out.println("Valor Mensal: R$ " + parcela);
//             System.out.println("Total a pagar: R$ " + valorTotal);
//             System.out.println("-------------------------------");
//         }
//         //atualizar extrato
//         //atualizar fatura
//         //inicializar temporizador de pagamento
//         //notificar agiota ao longo do tempo e quando for realizado o emprestimo
//     }
//     public String historicoEmprestimos(){
//         //exibir lista de emprestimos
//         StringBuilder historico = new StringBuilder();
//         for(String cobranca : listaCobranca){
//             historico.append(cobranca).append("\n");
//         }
//         return historico.toString();
//         //disponibilidade de ver dados dos emprestimos(cobrancas, datas, atrasos, valores e avaliacoes)
//     }
  
//     public String pagarParcela(int idParcela){
//         //diminuir saldo do valor
//         Fatura fatura = faturas.get(idParcela); // Busca a fatura pelo ID
//         double valorParcela = fatura.getValorParcela();
//         if (conta.getSaldo() >= valorParcela) {//metodos conta e fatura, olhar
//             conta.sacar(valorParcela);
//             fatura.diminuirSaldo(valorParcela);
//             return "Parcela paga com sucesso. Valor: R$ " + valorParcela;
//         } else {
//             return "Saldo insuficiente para pagar a parcela. Notificando agiota.";
//         }
//         //caso sem limite notificar agiota do saldo
//         //notificar possivel penalidade caso sem limite
//         //diminuir o valor do pagamento da parcela na fatura
//         //diminuir da fatura
//     }

//     public void avaliarAgiota(int idConta){
//         //quantidade de estrelas
//         //adicionar comentario
//         //salvar no txt a avaliacao
//         Avaliacao avaliacao = new Avaliacao(idConta, estrelas, comentario);
//         avaliacoes.add(avaliacao);
//         System.out.println("Agiota avaliado com " + estrelas + " estrelas. Comentário: " + comentario);
//     }

//     public String verHistoricoCobranca(){
//         //exibir lista de cobrancas realizadas pelo agiota
//         //exibir consequencias das conbrancas se houver
//     }



// }
