package br.com.rcouto.cadastrocaelum;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.rcouto.cadastro.adapter.ListaAlunosAdapter;
import br.com.rcouto.cadastro.modelo.Aluno;
import br.com.rcouto.cadastro.modelo.Extras;

public class MainActivity extends ActionBarActivity {
	
	private ListView listaAlunos;
	private AlunoDAO dao;
	private List<Aluno> alunos;
//	private ArrayAdapter<Aluno> adapter;
	private ListaAlunosAdapter adapter;
	private Aluno alunoSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);
		
		listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		dao = new AlunoDAO(this);
		carregarLista();
		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent edicao = new Intent(MainActivity.this, FormularioActivity.class);
				Aluno alunoSelecionado = (Aluno) listaAlunos.getItemAtPosition(position);
				edicao.putExtra(Extras.ALUNO_SELECIONADO, alunoSelecionado);
				startActivity(edicao);
				
			}
			
		});
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				alunoSelecionado = (Aluno) adapter.getItem(position);
				return false;
			}
			
		});
		
		registerForContextMenu(listaAlunos);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dao.closeSql();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		carregarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.novo:
			Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
			startActivity(intent);
			return false;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuItem ligar = menu.add(0, 0, 0, "Ligar");
		Intent intentLigar = new Intent(Intent.ACTION_CALL);
		intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
		ligar.setIntent(intentLigar);
		
		MenuItem sms = menu.add(0, 1, 0, "Enviar SMS");
		Intent intentSms = new Intent(Intent.ACTION_VIEW);
		intentSms.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
		intentSms.putExtra("sms_body", "Mensagem");
		sms.setIntent(intentSms);
		
		MenuItem acharNoMapa = menu.add(0, 2, 0, "Achar no Mapa");
		Intent intentMapa = new Intent(Intent.ACTION_VIEW);
		intentMapa.setData(
				Uri.parse("geo:0,0?q="+alunoSelecionado.getEndereco()));
		acharNoMapa.setIntent(intentMapa);
		
		MenuItem site = menu.add(0, 3, 0, "Navegar no site");
		Intent intentSite = new Intent(Intent.ACTION_VIEW);
		intentSite.setData(Uri.parse("http://"+alunoSelecionado.getSite()));
		site.setIntent(intentSite);
		
		MenuItem email = menu.add(0, 4, 0, "Enviar E-mail");
		Intent intentEmail = new Intent(Intent.ACTION_SEND);
		intentEmail.setType("message/rfc822");
		intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[] { "caelum@caelum.com.br" } );
		intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Elogios do curso de android");
		intentEmail.putExtra(Intent.EXTRA_TEXT, "Este curso é ótimo!!!");
		email.setIntent(intentEmail);
		
		
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				new AlertDialog.Builder(MainActivity.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Deletar")
					.setMessage("Deseja mesmo deletar?")
					.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AlunoDAO dao = new AlunoDAO(MainActivity.this);
							dao.deletar(alunoSelecionado);
							dao.closeSql();
							carregarLista();
						}
					})
					.setNegativeButton("Não", null).show();
				
				return false;
			}
		});
		
	}
	
	private void carregarLista() {
		alunos = dao.getLista();
		
//		adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
		adapter = new ListaAlunosAdapter(alunos, this);
		
		
		listaAlunos.setAdapter(adapter);
	}
}
