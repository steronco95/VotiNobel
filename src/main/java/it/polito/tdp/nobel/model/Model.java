package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	
	private double bestMedia = 0.0;
	private Set<Esame> bestSoluzione;
	
	
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
	//devo impostare la chiamata della ricorsione
		
		//creo set parziale;
		Set<Esame> parziale = new HashSet<>();
		cerca(parziale,0,numeroCrediti);
				
		return bestSoluzione;
	}

	
	private void cerca(Set<Esame>parziale, int l, int m) {
		
		//caso terminale
		
		int crediti = sommaCrediti(parziale);
		
		if(crediti>m) 
			return;
		
		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
			
//				bestSoluzione = parziale; ERRORACCIO!
				
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
				return;
			}
			
		}
		
		//sicuramente crediti < m;
		//sono arrivato al fondo dell'albero di ricorsione ma non ho trovato abbastanza crediti esco dalla ricorsione
		
		if(l == esami.size()) {
			return;
		
		}
		
		//genero i sotto-problemi
		//esami[L] è da aggiungere o no? provo entrambe le cose;
		//provo ad aggiungerlo;
		
		parziale.add(esami.get(l));
		cerca(parziale, l+1, m);
		parziale.remove(esami.get(l));
		
		//provo anche a non aggiungerlo
		cerca(parziale,l+1,m);
		
		
	}

	public double calcolaMedia(Set<Esame> parziale) {
		int crediti =0;
		int somma =0;
		
		for(Esame e : parziale) {
			crediti += e.getCrediti();
			somma += (e.getVoto()*e.getCrediti());
		}
		
	
		
		return somma/crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma =0;
		for(Esame e : parziale) {
			somma+= e.getCrediti();
		}
		return somma;
	}

//	public void svuotaSoluzioni() {
//		bestSoluzione.clear();
//		
//	}
	
	
}
