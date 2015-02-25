package br.com.rcouto.cadastro.modelo;

import br.com.rcouto.cadastrocaelum.FormularioActivity;
import br.com.rcouto.cadastrocaelum.R;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class FormularioHelper {

	private EditText nome;
	private EditText telefone;
	private EditText site;
	private SeekBar nota;
	private EditText endereco;
	private ImageView botaoImagem;
	private Aluno aluno;
	
	public FormularioHelper(FormularioActivity activity) {
		
		this.nome = (EditText) activity.findViewById(R.id.nome);
		this.telefone = (EditText) activity.findViewById(R.id.telefone);
		this.site = (EditText) activity.findViewById(R.id.site);
		this.nota = (SeekBar) activity.findViewById(R.id.nota);
		this.endereco = (EditText) activity.findViewById(R.id.endereco);
		this.botaoImagem = (ImageView) activity.findViewById(R.id.foto);
		this.aluno = new Aluno();
	}
	
	public Aluno pegaAlunoDoFormulario() {
		aluno.setNome(nome.getEditableText().toString());
		aluno.setEndereco(endereco.getEditableText().toString());
		aluno.setSite(site.getEditableText().toString());
		aluno.setTelefone(telefone.getEditableText().toString());
		aluno.setNota(Double.valueOf(nota.getProgress()));
		return aluno;
	}
	
	public void colocarNoFormulario(Aluno aluno) {
		this.nome.setText(aluno.getNome());
		this.telefone.setText(aluno.getTelefone());
		this.site.setText(aluno.getSite());
		this.nota.setProgress(Double.valueOf(aluno.getNota()).intValue());
		this.endereco.setText(aluno.getEndereco());
		this.aluno = aluno;
	}
	
	
}
