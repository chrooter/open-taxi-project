package iut.montruil.paris8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

public class MdpForgetActivity extends Activity{

	
		Button ok, returnB;
		EditText reponse, login;
		String sreponse, slogin;
		TextView viewMdp;
		
		public void onCreate(Bundle savedInstanceState) {
	        
			super.onCreate(savedInstanceState);		
			setContentView(R.layout.mdpoublier);
			ok = (Button)findViewById(R.id.buttonOk);
			returnB = (Button)findViewById(R.id.buttonRetour);	
	        ok.setOnClickListener(ButtonOK);
	        returnB.setOnClickListener(ButtonRETURN);
		}
			
	 	OnClickListener ButtonOK = new OnClickListener(){
	 	public void onClick(View v) { 	
		
				reponse = (EditText) findViewById(R.id.reponse);
				sreponse = reponse.getText().toString().trim();
				login = (EditText) findViewById(R.id.log);
				slogin = login.getText().toString().trim();
					
				if (sreponse.equals("") && slogin.equals("")){
					 Toast.makeText(getBaseContext(),"Veuillez répondre a la question.",Toast.LENGTH_SHORT).show();
					 return;
				}
				
				HttpClient httpclient = new DefaultHttpClient();    
				HttpPost httppost = new HttpPost("http://webperso.iut.univ-paris8.fr/~cfarot/opentaxi/BDConnect.php?choix=recupMdp");
				
				try {
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
					nameValuePairs.add(new BasicNameValuePair("login",slogin));
					nameValuePairs.add(new BasicNameValuePair("reponse",sreponse));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 
					// Execute HTTP Post Request        
					HttpResponse response;
					response = httpclient.execute(httppost);
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = ""; 
					String text = "";
					while ((line = reader.readLine()) != null){
						text += line;
					}
					viewMdp = (TextView) findViewById(R.id.Mdp);
					if(text.trim().equals("Pas de resultat")){		
						viewMdp.setText("Désolé ce couple: login/reponse n'existe pas");
					}
					else{
						viewMdp.setText("Votre mot de passe: "+text.trim());	
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
					
				//Intent IntentOK = new Intent(MdpForgetActivity.this,InscriptionBASEActivity.class);
				//IntentOK.putExtra("reponse", sreponse);
				//startActivity(IntentOK);
	 		}	 
	 	};
	 	
		OnClickListener ButtonRETURN = new OnClickListener(){
			
		 	public void onClick(View v) {
				
				Intent monIntent = new Intent(MdpForgetActivity.this,OTActivity.class);	 
				startActivity(monIntent);
			}	 
	 };
}