package org.libertas.bd;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class EventoDAO {
	public void inserir(Evento en) {
		Conexao con = new Conexao();
		try {
			String sql = " INSERT INTO evento "
					+ " (tema, valor_entrada, data_evento, local_evento, horario) VALUES (?, ?, ?, ?, ?) ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, en.getTema());
			prep.setDouble(2, en.getValor_entrada());
			prep.setString(3, en.getData_evento());
			prep.setString(4, en.getLocal_evento());
			prep.setString(5, en.getHorario());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
	}
	public void alterar(Evento en) {
		Conexao con = new Conexao();
		try {
			String sql = " UPDATE evento "
					+ " SET tema = ?, valor_entrada = ?, data_evento = ?, local_evento = ?, horario = ? "
					+ " WHERE idevento = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			
			prep.setString(1, en.getTema());
			prep.setDouble(2, en.getValor_entrada());
			prep.setString(3, en.getData_evento());
			prep.setString(4, en.getLocal_evento());
			prep.setString(5, en.getHorario());
			prep.setInt(6, en.getIdevento());
			
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}
	public void excluir(Evento en) {
		Conexao con = new Conexao();
		try {
			String sql = " DELETE FROM evento "
					+ " WHERE idevento = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, en.getIdevento());
			
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}

	public Evento consultar(int id) {

		Evento en = new Evento();
		Conexao con = new Conexao();
		try {
			String sql = " SELECT * FROM evento WHERE idevento = " + id;
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				en.setIdevento
				
				(res.getInt("idevento"));
				en.setTema(res.getString("tema"));
				en.setValor_entrada(res.getDouble("valor_entraada"));
				en.setData_evento(res.getString("data_evento"));
				en.setLocal_evento(res.getString("local_evento"));
				en.setHorario(res.getString("horario"));
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return en;
	}
		
	public List<Evento> listar(){
		List<Evento> lista = new LinkedList<Evento>();
			Conexao con = new Conexao();
		
		try {
			String sql = " SELECT * FROM evento ORDER BY data_evento";
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				Evento en = new Evento();
				en.setIdevento(res.getInt("idevento"));
				en.setTema(res.getString("tema"));
				en.setValor_entrada(res.getDouble("valor_entrada"));
				en.setData_evento(res.getString("data_evento"));
				en.setLocal_evento(res.getString("local_evento"));
				en.setHorario(res.getString("horario"));
				lista.add(en);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return lista;
	}
}

