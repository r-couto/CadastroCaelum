package br.com.rcouto.cadastro.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.rcouto.cadastro.modelo.Aluno;
import br.com.rcouto.cadastrocaelum.R;

public class ListaAlunosAdapter extends BaseAdapter {
	
	private final List<Aluno> alunos;
	private final Activity activity;

	public ListaAlunosAdapter(List<Aluno> alunos, Activity activity) {
		this.alunos = alunos;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alunos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
		
		Aluno aluno = alunos.get(position);
		
		LinearLayout fundo = (LinearLayout) view.findViewById(R.id.fundo);
		
		if (position % 2 == 0) {
			fundo.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		} else {
			fundo.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));
		}
		
		TextView nome = (TextView) view.findViewById(R.id.nome2);
		nome.setText(aluno.toString());
		
		Bitmap bm;
		
		if (aluno.getFoto() != null) {
			bm = BitmapFactory.decodeFile(aluno.getFoto());
		} else {
			bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
		}
		
		bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
		
		ImageView foto = (ImageView) view.findViewById(R.id.foto2);
		foto.setImageBitmap(bm);
		
		return view;
	}

}
