package iut.montruil.paris8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class choiceActivity extends Activity{

	
	Button conducteur, sansVoiture, profil = null;
	Intent monIntent;
	String id;
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.choixmode);

		Intent thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
			
		conducteur = (Button)findViewById(R.id.buttonConducteur);
		sansVoiture = (Button)findViewById(R.id.buttonSansVoiture);
		profil = (Button)findViewById(R.id.buttonProfil);		
        conducteur.setOnClickListener(ButtonCONDUCTEUR);
        sansVoiture.setOnClickListener(ButtonSV);
        profil.setOnClickListener(ButtonPROFIL);
	}
	
	OnClickListener ButtonCONDUCTEUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == conducteur) {		
			monIntent = new Intent(choiceActivity.this,ConducteurActivity.class); 
			monIntent.putExtra("Identifiant", id);	 
			startActivity(monIntent);	
		}
	} 	 
 	};
 	
 	OnClickListener ButtonSV = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == sansVoiture) {	
			monIntent = new Intent(choiceActivity.this,sansVActivity.class);
			monIntent.putExtra("Identifiant", id);	 
			startActivity(monIntent);		
		}
	} 
 	};
 	
 	OnClickListener ButtonPROFIL = new OnClickListener(){
 	public void onClick(View v) {
		
		if(v == profil) {
			monIntent = new Intent(choiceActivity.this,ProfilActivity.class);
			monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		}
	}
 	};
}