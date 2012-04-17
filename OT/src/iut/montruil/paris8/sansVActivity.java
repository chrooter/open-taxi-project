package iut.montruil.paris8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class sansVActivity extends Activity{

	
	 Button valider, retour = null;
	 LinearLayout layout = null;
	 EditText nbP, des;
	 String snbP, sdes, id;
	 Spinner spinner;
	ArrayAdapter<CharSequence> adapter;
	Intent thisIntent;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sansv);
		
		
		valider = (Button)findViewById(R.id.buttonValiderSans);
		retour = (Button)findViewById(R.id.buttonRetourSans);
		
		valider.setOnClickListener(ButtonVALIDER);
		retour.setOnClickListener(ButtonRETOUR);
		
		thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
	}
	
	
	OnClickListener ButtonVALIDER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == valider) {
			 
			nbP = (EditText) findViewById(R.id.nbplacesSV);
			
			snbP = nbP.getText().toString();
			
			des = (EditText) findViewById(R.id.destinaSV);
			
			sdes = nbP.getText().toString();
			
			if (snbP.equals("") || sdes.equals("")){
				
				 Toast.makeText(getBaseContext(),"Veuillez remplir le(s) champs",Toast.LENGTH_SHORT).show();
				 return;
			}
			
			return;
/*
			Intent changementDePage = new Intent(InscriptionActivity.this,OTActivity.class);
			 
			startActivity(changementDePage);
	*/	
		}
	}
 	 
 	};
	
	OnClickListener ButtonRETOUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			 
		
		Intent monIntent = new Intent(sansVActivity.this,choiceActivity.class);
		monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
}
