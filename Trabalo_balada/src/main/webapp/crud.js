var valores = [];
var idevento = 0;

function novo(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    idevento = 0;
    var tema = document.getElementById("tema");
    var valor_entrada = document.getElementById("valor_entrada");
    var data_evento = document.getElementById("data_evento");
    var local_evento = document.getElementById("local_evento");
    var horario = document.getElementById("horario");
    tema.value = "";
    valor_entrada.value = "";
    data_evento.value = "";
    local_evento.value = "";
    horario.value = "";
    
    //joga o foco no 1º campo:
    tema.focus();
}

function alterar(i){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    idevento = valores[i].idevento;
    var tema = document.getElementById("tema");
     var valor_entrada = document.getElementById("valor_entrada");
    var data_evento = document.getElementById("data_evento");
    var local_evento = document.getElementById("local_evento");
    var horario = document.getElementById("horario");
    tema.value = valores[i].tema;
    valor_entrada.value = valores[i].valor_entrada;
    data_evento.value = valores[i].data_evento;
    local_evento.value = valores[i].local_evento;
    horario.value = valores[i].horario;
    
    //joga o foco no 1º campo:
    tema.focus();
}

function salvar(){
	//valida campos obrigarotios
	if(document.getElementById("tema").value  == ""){
		alert("Campo Tema é Obrigaratório!");
		return;
	}
	
    //pega os dados digitados pelo usuário e monta um objeto
    var en = {
		idevento: idevento,
		tema:  document.getElementById("tema").value,
		valor_entrada: document.getElementById("valor_entrada").value,
		data_evento: document.getElementById("data_evento").value,
		local_evento: document.getElementById("local_evento").value,
		horario: document.getElementById("horario").value
	};
   
   	//define se o método sera para inserir ou alterar
   	if (idevento == 0) {
		   metodo = "POST";
	   } else {
		   metodo = "PUT";
	   }
   
	//envia os dados para o servidor
	mostraLoading("aguarde, salvando dados...")
	fetch("http://localhost:8080/Trabalo_balada/Evento/*",
		{method: metodo,
	    body: JSON.stringify(en)
		}
	).then(resp => resp.json())
	.then(function (Retorno){
		escondeLoading();
		alert(Retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}
function excluir(i){
 	idevento = valores[i].idevento; 
 
	//envia os dados para o servidor
	mostraLoading("aguarde, excluindo...");
	fetch("http://localhost:8080/Trabalo_balada/Evento/" + idevento,
		{method: "DELETE",
		}
	).then(resp => resp.json())
	.then(function (retorno){
		escondeLoading();
		alert(retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}

function cancelar(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //escondeo o formulário 
    form.style.display = "none";
    //mostra a lista 
    lista.style.display = "block";
}

function listar(){
	var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML = "<tr><td colspan>aguarde, carregando...</td></tr>";
	
    fetch ("http://localhost:8080/Trabalo_balada/Evento/*")
    .then(resp => resp.json())
    .then(dados => mostrar(dados));
}

function mostrar(dados){
	valores = dados;
    var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML ="";
    //percoorre a lista 
    for (var i in dados){
        lista.innerHTML += "<tr>"
                        + "<td>" + dados[i].idevento + "</td>"
                        + "<td>" + dados[i].tema + "</td>"
                        + "<td>" +("R$ ")+ dados[i].valor_entrada + "</td>"
                        + "<td>" + dados[i].data_evento + "</td>"
                        + "<td>" + dados[i].local_evento + "</td>"
                        + "<td>" + dados[i].horario + "</td>"
                        + "<td>"
                        + "<input type='button' value='Alterar' onclick='alterar("+i+")'/>"
                        + "<input type='button' value='Excluir' onclick='excluir("+i+")'/>"
                        +"</td>"
                        + "</tr>";
                        
    }
}

function mostraLoading(msg){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "block";
	con.style.display = "none";
	loa.innerHTML = msg;
}

function escondeLoading(){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "none";
	con.style.display = "block";
	
}
listar();