package org.libertas;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.libertas.bd.EventoDAO;
import com.google.gson.Gson;


public class Evento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Evento() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//out.println("Executando método GET");
		EventoDAO endao = new EventoDAO();
		List<org.libertas.bd.Evento> lista = endao.listar();
		Gson gson = new Gson();
		out.print(gson.toJson(lista));
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
		//PEGA O BOBY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) !=null) {
			sb.append(line);
		}
			String boby = sb.toString();
			
			//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
			Gson gson = new Gson();
			org.libertas.bd.Evento en = gson.fromJson(boby, org.libertas.bd.Evento.class);
			
			//INSERE OBJETO NO BANCO DE DADOS
			EventoDAO endao = new EventoDAO();
			endao.inserir(en);
			
			Retorno r = new Retorno(true, "Registro Inserido com Sucesso");
			out.print(gson.toJson(r));
			
	 } catch (Exception e) {
		 e.printStackTrace();
		 Gson gson = new Gson();
		 Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
}
		
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//PEGA O BOBY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) !=null) {
				sb.append(line);
			}
				String boby = sb.toString();
				
				//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
				Gson gson = new Gson();
				org.libertas.bd.Evento en = gson.fromJson(boby, org.libertas.bd.Evento.class);
				
				//INSERE OBJETO NO BANCO DE DADOS
				EventoDAO endao = new EventoDAO();
				endao.alterar(en);
				
				Retorno r = new Retorno(true, "Registro Alterado com Sucesso");
				out.print(gson.toJson(r));
				
		 } catch (Exception e) {
			 e.printStackTrace();
			 Gson gson = new Gson();
			 Retorno r = new Retorno(false, e.getMessage());
			 out.print(gson.toJson(r));out.print(e.getMessage());
			}
	}
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//pega o id passando por parâmetro 
			String id = request.getRequestURI();
			id = id.substring(id.lastIndexOf("/")+1);
			
			EventoDAO endao = new EventoDAO();
			org.libertas.bd.Evento en = new org.libertas.bd.Evento();
			en.setIdevento(Integer.parseInt(id));
			endao.excluir(en);
			
			Retorno r = new Retorno(true, "Registro Excluido com Sucesso");
			Gson gson = new Gson();
			out.print(gson.toJson(r));
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
		
		
		
		
	}

}
