package br.com.rcouto.cadastrocaelum;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private ListView listaAlunos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.listagem_alunos);
		
		final String[] alunos = {"Anderson", "Filipe", "Guilherme"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);
		
		listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		listaAlunos.setAdapter(adapter);
		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, alunos[position], Toast.LENGTH_LONG).show();
				
			}
			
		});
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, "Posição selecionada com toque longo: " + position, Toast.LENGTH_LONG).show();
				return false;
			}
			
		});
		
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
//			Toast.makeText(MainActivity.this, "Você clicou no novoALuno", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
			startActivity(intent);
			return false;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
