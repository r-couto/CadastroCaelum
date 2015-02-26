package br.com.rcouto.cadastro.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.com.rcouto.cadastrocaelum.FormularioActivity;
import br.com.rcouto.cadastrocaelum.R;

public class FormularioHelper {

	private EditText nome;
	private EditText telefone;
	private EditText site;
	private SeekBar nota;
	private EditText endereco;
	private ImageView foto;
	private Aluno aluno;
	
	public FormularioHelper(FormularioActivity activity) {
		
		this.nome = (EditText) activity.findViewById(R.id.nome);
		this.telefone = (EditText) activity.findViewById(R.id.telefone);
		this.site = (EditText) activity.findViewById(R.id.site);
		this.nota = (SeekBar) activity.findViewById(R.id.nota);
		this.endereco = (EditText) activity.findViewById(R.id.endereco);
		this.foto = (ImageView) activity.findViewById(R.id.foto);
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
		
		if (aluno.getFoto() != null) {
			this.carregarImagem(aluno.getFoto());
		}
		
	}

	public ImageView getFoto() {
		return foto;
	}

	public void carregarImagem(String localArquivoFoto) {
		Bitmap imageFoto = BitmapFactory.decodeFile(localArquivoFoto);
		Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imageFoto, 100, 100, true);
		
		aluno.setFoto(localArquivoFoto);
		foto.setImageBitmap(imagemFotoReduzida);
	}
	
	
}
