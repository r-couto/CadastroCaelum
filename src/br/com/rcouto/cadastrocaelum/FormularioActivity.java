package br.com.rcouto.cadastrocaelum;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.rcouto.cadastro.modelo.Aluno;
import br.com.rcouto.cadastro.modelo.Extras;
import br.com.rcouto.cadastro.modelo.FormularioHelper;

public class FormularioActivity extends ActionBarActivity {
	
	private FormularioHelper helper;
	private AlunoDAO dao;
	private Aluno alunoParaSerAlterado;
	private String localArquivoFoto;
	private static final int TIRA_FOTO = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		helper = new FormularioHelper(this);
		dao = new AlunoDAO(this);
		
		Button botao = (Button) findViewById(R.id.botao_inserir);
		
		alunoParaSerAlterado = (Aluno) getIntent().getSerializableExtra(Extras.ALUNO_SELECIONADO);
		
		if(alunoParaSerAlterado != null) {
			botao.setText("Alterar");
			this.helper.colocarNoFormulario(alunoParaSerAlterado);
		}
		
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Aluno aluno;
				if (alunoParaSerAlterado != null) {
					aluno = helper.pegaAlunoDoFormulario();
					aluno.setId(alunoParaSerAlterado.getId());
					dao.alterar(aluno);
				} else {
					aluno = helper.pegaAlunoDoFormulario();
					dao.insere(aluno);
				}
				finish();
				
			}
		});
		
		ImageView foto = helper.getFoto();
		foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				localArquivoFoto = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
				
				File arquivo = new File(localArquivoFoto);
				Uri localFoto = Uri.fromFile(arquivo);
				
				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				startActivityForResult(irParaCamera, TIRA_FOTO);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		dao.closeSql();
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == TIRA_FOTO) {
			if (resultCode == Activity.RESULT_OK) {
				helper.carregarImagem(this.localArquivoFoto);
			} else {
				this.localArquivoFoto = null;
			}
		}
		
	}
}
