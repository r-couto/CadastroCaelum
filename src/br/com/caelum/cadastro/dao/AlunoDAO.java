package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.rcouto.cadastro.modelo.Aluno;

public class AlunoDAO {

	// versão da tabela para marcar que alteramos algum detalhe do modelo
	private static final int VERSAO = 1;
	private static final String TABELA = "CadastroCaelum";
	private static final String DATABASE = "FJ57";
	
	private SQLiteOpenHelper sql;
	
	private static final String[] COLUNAS = { "id", "nome", "telefone", "endereco", "site", "nota", "foto" };
	

	public AlunoDAO(Context context) {
		
		sql = new SQLiteOpenHelper(context, DATABASE, null, VERSAO) {
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				String ddl = "CREATE TABLE " + TABELA
						 + " (id INTEGER PRIMARY KEY, nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT, site TEXT, nota REAL, foto TEXT);";
			
				db.execSQL(ddl);
				
			}
		};
	}

	public void insere(Aluno aluno) {
		ContentValues values = toValues(aluno);
		sql.getWritableDatabase().insert(TABELA, null, values);
	}
	
	private ContentValues toValues(Aluno aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		
		return values;
	}
	
	public List<Aluno> getLista() {
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		Cursor c = null;
		try {
			c = sql.getWritableDatabase().query(TABELA, COLUNAS, null, null, null, null, null);
			
			while(c.moveToNext()) {
				Aluno aluno = new Aluno();
				
				aluno.setId(c.getLong(0));
				aluno.setNome(c.getString(1));
				aluno.setTelefone(c.getString(2));
				aluno.setEndereco(c.getString(3));
				aluno.setSite(c.getString(4));
				aluno.setNota(c.getInt(5));
				aluno.setFoto(c.getString(6));
				
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			Log.e("SQLException", "Erro ao criar lista de alunos no método getLista.");
		} finally {
			if(c != null)
				c.close();
		}
		
		
		return alunos;
	}
	
	public void deletar(Aluno aluno) {
		String[] args = {aluno.getId().toString()};
		sql.getWritableDatabase().delete(TABELA, "id=?", args);
	}
	
	public void alterar(Aluno aluno) {
		ContentValues values = toValues(aluno);
		
		String[] args = {aluno.getId().toString()};
		sql.getWritableDatabase().update(TABELA, values, "id=?", args);
	}
	
	public Aluno buscaAlunoPeloID(Long id) {
		
		String[] args = {id.toString()};
		
		Aluno aluno = null;
		Cursor c = null;
		try {
			c = sql.getWritableDatabase().query(TABELA, COLUNAS, "id=?", args, null, null, null);
			
			c.moveToFirst();
			
			aluno = new Aluno();
			
			aluno.setId(c.getLong(0));
			aluno.setNome(c.getString(1));
			aluno.setTelefone(c.getString(2));
			aluno.setEndereco(c.getString(3));
			aluno.setSite(c.getString(4));
			aluno.setNota(c.getInt(5));
			aluno.setFoto(c.getString(6));
			
		} catch (SQLException e) {
			Log.e("SQLException", "Erro buscar aluno pelo ID no método buscaAlunoPeloID.");
		} finally {
			if(c != null)
				c.close();
		}
		
		return aluno;
	}
	
	public void closeSql() {
		this.sql.close();
	}
	
}
