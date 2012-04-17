package iut.montruil.paris8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InscriptionActivity extends Activity{
	
	Button ok, returnB;
	EditText nom, prenom, login, mdp, mail, reponse;
	String snom, sprenom, slogin, smdp, smail, sreponse;
	Intent IntentOK;
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		ok = (Button)findViewById(R.id.buttonOk);
		returnB = (Button)findViewById(R.id.buttonRetour);
        ok.setOnClickListener(ButtonOK);
        returnB.setOnClickListener(ButtonRETURN);
	}

	OnClickListener ButtonOK = new OnClickListener(){
 		
		public void onClick(View v) {
		
			nom = (EditText) findViewById(R.id.nom);
			prenom = (EditText) findViewById(R.id.prenom);
			login = (EditText) findViewById(R.id.login);
			mdp = (EditText) findViewById(R.id.mdp);
			mail = (EditText) findViewById(R.id.mail);
			reponse = (EditText) findViewById(R.id.reponse);
			snom = nom.getText().toString().trim();
			sprenom = prenom.getText().toString().trim();
			slogin = login.getText().toString().trim();
			smdp = mdp.getText().toString().trim();
			smail = mail.getText().toString().trim();
			sreponse = reponse.getText().toString().trim();
		
			if (snom.equals("") || sprenom.equals("") || slogin.equals("") || smdp.equals("") || smail.equals("") || sreponse.equals("")){
				 Toast.makeText(getBaseContext(),"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
				 return;
			}	
			Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
			Matcher m = p.matcher(smail);
			
			if (m.matches() == false){
				Toast.makeText(getBaseContext(),"Le champs email ne correspond pas au format d'une adresse mail",Toast.LENGTH_SHORT).show();
				return;
			}

			if(smdp.length() < 6){
				Toast.makeText(getBaseContext(),"Le mot de passe est trop court (au moins 6 caractères)",Toast.LENGTH_SHORT).show();
				return;
			}
			// Create a new HttpClient and Post Header    
			HttpClient httpclient = new DefaultHttpClient();    
			HttpPost httppost = new HttpPost("http://webperso.iut.univ-paris8.fr/~cfarot/opentaxi/BDConnect.php?choix=inscription");
			try {        
				// Add your data        
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();        
				nameValuePairs.add(new BasicNameValuePair("nom", snom));        
				nameValuePairs.add(new BasicNameValuePair("prenom", sprenom));
				nameValuePairs.add(new BasicNameValuePair("login", slogin));
				nameValuePairs.add(new BasicNameValuePair("mdp", smdp));
				nameValuePairs.add(new BasicNameValuePair("courriel", smail));
				nameValuePairs.add(new BasicNameValuePair("reponse", sreponse));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
				// Execute HTTP Post Request        
				HttpResponse response = httpclient.execute(httppost);  
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = ""; 
				String text = "";
				while ((line = reader.readLine()) != null){
					text += line;
				}
				 Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

				 if(text.trim().equals("Inscription effectuee !")){
					 Intent monIntent = new Intent(InscriptionActivity.this, OTActivity.class);	 
					 startActivity(monIntent);
				 }				 
			} catch (ClientProtocolException e) {        
				Toast.makeText(getBaseContext(), "client protocol exception", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {   
				Toast.makeText(getBaseContext(), "IOException", Toast.LENGTH_SHORT).show();  
			}		
 		} 	 
 	};
 	
	OnClickListener ButtonRETURN = new OnClickListener(){
		
		public void onClick(View v) {
			
			Intent monIntent = new Intent(InscriptionActivity.this,OTActivity.class);
			startActivity(monIntent);	
		}
 	};	
}