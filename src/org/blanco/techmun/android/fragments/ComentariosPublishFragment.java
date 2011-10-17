package org.blanco.techmun.android.fragments;

import org.blanco.techmun.android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

public class ComentariosPublishFragment extends Fragment {

	TextView header = null;
	boolean expanded = false;
	int prevHeight = 0;
	Button btnEnviar = null;
	Button btnCancelar = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.comentarios_publish_layout, null); 
		
		header = (TextView) v.findViewById(R.id.comentarios_publish_layout_header);
		header.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				animate();
			}
		});
		btnEnviar = (Button) v.findViewById(R.id.comentarios_publish_layout_btn_accept);
		btnEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animate();
			}
		});
		btnCancelar = (Button) v.findViewById(R.id.comentarios_publish_layout_btn_cancel);
		btnCancelar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				animate();
			}
		});
		return v;
	}

	private void animate(){
		int endHeight = (expanded)? header.getHeight() : 400;
		expanded = !expanded;
		ExpandAnimation anim = new ExpandAnimation(getView(), getView().getHeight(), endHeight);
		getView().startAnimation(anim);
	}
	
	class ExpandAnimation extends Animation {
        private final View _view;
        private final int _startHeight;
        private final int _finishHeight;

        public ExpandAnimation( View view, int startHeight, int finishHeight ) {
            _view = view;
            _startHeight = startHeight;
        _finishHeight = finishHeight;
        setDuration(220);
    }

    @Override
    protected void applyTransformation( float interpolatedTime, Transformation t ) {
        final int newHeight = (int)((_finishHeight - _startHeight) * interpolatedTime + _startHeight);
        _view.getLayoutParams().height = newHeight;
        _view.requestLayout();
    }

    @Override
    public void initialize( int width, int height, int parentWidth, int parentHeight ) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds( ) {
        return true;
    }
	}
	
}
