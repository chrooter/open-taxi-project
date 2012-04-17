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

public class ConducteurActivity extends Activity{

	 Button valider, retour = null;
	 LinearLayout layout = null;
	 EditText nbP,prix, des;
	 String snbP, sprix, sdes, id;
	 Spinner spinner;
	ArrayAdapter<CharSequence> adapter;
	Intent thisIntent;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.conducteur);
		
		valider = (Button)findViewById(R.id.buttonValiderCond);
		retour = (Button)findViewById(R.id.buttonRetourCond);
		
		valider.setOnClickListener(ButtonVALIDER);
        retour.setOnClickListener(ButtonRETOUR);
		
        thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");	
		
	}
		
	
	
	OnClickListener ButtonVALIDER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == valider) {
			 
			
			des  = (EditText) findViewById(R.id.destina);
			sdes = des.getText().toString();
			
			nbP = (EditText) findViewById(R.id.nbplaces);
			
			snbP = nbP.getText().toString();
			
			prix = (EditText) findViewById(R.id.prix);
			
			sprix = prix.getText().toString();
			
			if (snbP.equals("") || snbP.equals("")){
				
				 Toast.makeText(getBaseContext(),"Veuillez remplir le(s) champs",Toast.LENGTH_SHORT).show();
				 return;
			}
			
			if (sdes.equals("")){
				
				 Toast.makeText(getBaseContext(),"Veuillez remplir l'adresse ou selectionner un favoris",Toast.LENGTH_SHORT).show();
				 return;
			}
			
			Intent monIntent = new Intent(ConducteurActivity.this,ItineraireActivity.class);
			 monIntent.putExtra("adresse", sdes);
			startActivity(monIntent);
		
		}
	} 	 
 	};
 		
	OnClickListener ButtonRETOUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			 
		
		Intent monIntent = new Intent(ConducteurActivity.this,choiceActivity.class);
		monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
		
}
