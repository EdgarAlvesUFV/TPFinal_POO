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
//     @Override
//     public String getTipoConta(){ 
//         return conta.getTipoConta();
//     }
//     @Override
//     public void verExtrato(){
//         System.out.println("Extrato do cliente : "+ getNome() + ": Saldo = " + conta.getSaldo());
//     }
//     @Override
//     public void simularEmprestimo(int quantia){//quantia para emprestimo desejado
//         //simular novo saldo
//         //consultar juros de cada agiota??

//     }
//     @Override
//     public void realizarEmprestimo(){
//         //consultar lista de agiotas disponiveis
//         //mostrar informacoes gerais (saldo, juros, tempo etc)
//         //atualizar extrato
//         //atualizar fatura
//         //inicializar temporizador de pagamento
//         //notificar agiota ao longo do tempo e quando for realizado o emprestimo
//     }
//     @Override
//     public String historicoEmprestimos(){
//         //exibir lista de emprestimos
//         //disponibilidade de ver dados dos emprestimos(cobrancas, datas, atrasos, valores e avaliacoes)
//     }
//     @Override
//     public String pagarParcela(int idParcela){
//         //diminuir saldo do valor
//         //caso sem limite notificar agiota do saldo
//         //notificar possivel penalidade caso sem limite
//         //diminuir o valor do pagamento da parcela na fatura
//         //diminuir da fatura
//     }
//     @Override
//     public void avaliarAgiota(int idConta){
//         //quantidade de estrelas
//         //adicionar comentario
//         //pegar o texto do comentario araves do botao
//     }
//     @Override
//     public String verHistoricoCobranca(){
//         //exibir lista de cobrancas realizadas pelo agiota
//         //exibir consequencias das conbrancas se houver
//     }



// }
